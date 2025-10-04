package com.jeep.findercity.analyzer;

import com.jeep.findercity.model.MapInfo;
import com.jeep.findercity.model.SearchResult;
import com.jeep.findercity.model.StaticMeshActorMatch;
import com.jeep.findercity.util.MapMetadataExtractor;
import com.jeep.findercity.util.ProgressCallback;
import acmi.l2.clientmod.io.UnrealPackage;
import acmi.l2.clientmod.l2smr.StaticMeshActorUtil;
import acmi.l2.clientmod.l2smr.model.Actor;
import java.io.File;
import java.util.List;

/**
 * Analiza archivos .unr para buscar coordenadas especificas
 * Implementa la estrategia hibrida: metadatos + analisis de objetos
 * 
 * @author Ak4n1 - Terra Team
 */
public class MapAnalyzer {
    
    private static final int DEFAULT_TILE_SIZE = 512; // Tamaño estandar de cada tile
    
    /**
     * Busca coordenadas en todos los mapas usando estrategia hibrida
     * @param mapsDir Directorio con los archivos .unr
     * @param targetX Coordenada X objetivo
     * @param targetY Coordenada Y objetivo
     * @param targetZ Coordenada Z objetivo
     * @param progressCallback Callback para mostrar progreso
     * @return Resultado de la busqueda
     */
    public static SearchResult searchCoordinates(File mapsDir, float targetX, float targetY, float targetZ, 
                                               ProgressCallback progressCallback) {
        
        long startTime = System.currentTimeMillis();
        SearchResult result = new SearchResult(String.format("%.2f %.2f %.2f", targetX, targetY, targetZ));
        
        try {
            // Obtener lista de archivos .unr directamente
            progressCallback.update("Iniciando análisis directo de archivos .unr...");
            File[] mapFiles = mapsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".unr"));
            
            if (mapFiles == null || mapFiles.length == 0) {
                progressCallback.update("No se encontraron archivos .unr en el directorio");
                return result;
            }
            
            progressCallback.update(String.format("Encontrados %d archivos .unr para analizar", mapFiles.length));
            result.setCandidateMaps(mapFiles.length);
            
            boolean found = analyzeObjectsInAllFiles(mapFiles, targetX, targetY, targetZ, progressCallback, result);
            
            result.setTotalMapsAnalyzed(mapFiles.length);
            result.setSearchTime(System.currentTimeMillis() - startTime);
            
        } catch (Exception e) {
            progressCallback.update("Error durante el analisis: " + e.getMessage());
            result.setTotalMapsAnalyzed(0);
            result.setSearchTime(System.currentTimeMillis() - startTime);
        }
        
        return result;
    }
    
    /**
     * Analiza objetos en TODOS los archivos .unr para buscar coordenadas exactas
     */
    private static boolean analyzeObjectsInAllFiles(File[] mapFiles, 
                                                   float targetX, float targetY, float targetZ,
                                                   ProgressCallback progressCallback, 
                                                   SearchResult result) {
        
        boolean found = false;
        int analyzed = 0;
        
        for (File mapFile : mapFiles) {
            analyzed++;
            String progressMsg = String.format("Analizando %d/%d mapas: %s", 
                                             analyzed, mapFiles.length, mapFile.getName());
            progressCallback.update(progressMsg);
            
                   try {
                       // Implementar analisis real de objetos usando UnrealPackage
                       int staticMeshCount = analyzeObjectsInMap(mapFile.getName(), targetX, targetY, targetZ, progressCallback, result);

                       if (staticMeshCount > 0) {
                           result.addFoundMap(mapFile.getName());
                           found = true;
                       }
                
                // Pequeña pausa para mostrar progreso
                Thread.sleep(50);
                
            } catch (Exception e) {
                progressCallback.update("Error analizando " + mapFile.getName() + ": " + e.getMessage());
            }
        }
        
        return found;
    }
    
    /**
     * Analiza objetos reales en un mapa para buscar coordenadas exactas
     * @return Número de StaticMeshActor encontrados cerca de las coordenadas
     */
    private static int analyzeObjectsInMap(String fileName, float targetX, float targetY, float targetZ, ProgressCallback progressCallback, SearchResult result) {
        try {
            File mapFile = new File("maps", fileName);
            if (!mapFile.exists()) {
                return 0;
            }
            
            try (UnrealPackage up = new UnrealPackage(mapFile, true)) {
                // Buscar todos los StaticMeshActor en el mapa
                List<UnrealPackage.ExportEntry> actors = up.getExportTable().stream()
                    .filter(entry -> entry.getObjectClass() != null && 
                            "Engine.StaticMeshActor".equals(entry.getObjectClass().getObjectFullName()))
                    .collect(java.util.stream.Collectors.toList());
                
                // Mostrar información del mapa
                progressCallback.update(String.format("  > Mapa %s: %d StaticMeshActor encontrados", fileName, actors.size()));
                
                if (actors.size() > 0) {
                    progressCallback.update(String.format("  > Analizando StaticMeshActores del mapa %s...", fileName));
                }
                
                int foundCount = 0;
                
                // Analizar cada actor para buscar coordenadas exactas
                int actorIndex = 0;
                for (UnrealPackage.ExportEntry actorEntry : actors) {
                    actorIndex++;
                    
                    // Mostrar progreso cada 100 actores para no saturar la salida
                    if (actorIndex % 100 == 0 || actorIndex == actors.size()) {
                        progressCallback.update(String.format("  > Analizando StaticMeshActor %d/%d en %s", 
                                                             actorIndex, actors.size(), fileName));
                    }
                    
                    // Debug: mostrar algunos actores individuales para verificar que se están procesando
                    if (actorIndex <= 5) {
                        progressCallback.update(String.format("  > Debug: Procesando StaticMeshActor %d en %s", 
                                                             actorIndex, fileName));
                    }
                    
                    try {
                        Actor actor = new Actor(actorEntry.getIndex(), 
                                               actorEntry.getObjectInnerFullName(),
                                               actorEntry.getObjectRawDataExternally(), up);
                        
                        // Verificar si las coordenadas del actor coinciden
                        if (actor.getLocation() != null && actor.getLocation().length >= 3) {
                            float actorX = actor.getLocation()[0];
                            float actorY = actor.getLocation()[1];
                            float actorZ = actor.getLocation()[2];
                            
                            // Mostrar ubicacion de cada StaticMeshActor (solo algunos para no saturar)
                            if (actorIndex <= 10 || actorIndex % 500 == 0) {
                                progressCallback.update(String.format("  > StaticMeshActor %d: ubicacion %.1f, %.1f, %.1f", 
                                                                     actorIndex, actorX, actorY, actorZ));
                            }
                            
                            // Buscar coincidencia con tolerancia para encontrar StaticMesh cerca
                            float tolerance = 500.0f; // Tolerancia de 500 unidades (más amplia)
                            if (Math.abs(actorX - targetX) <= tolerance &&
                                Math.abs(actorY - targetY) <= tolerance &&
                                Math.abs(actorZ - targetZ) <= tolerance) {
                                foundCount++;
                                
                                // Calcular distancia exacta
                                float distanceX = Math.abs(actorX - targetX);
                                float distanceY = Math.abs(actorY - targetY);
                                float distanceZ = Math.abs(actorZ - targetZ);
                                float totalDistance = (float) Math.sqrt(distanceX*distanceX + distanceY*distanceY + distanceZ*distanceZ);
                                
                                progressCallback.update(String.format("  > ¡ENCONTRADO! StaticMeshActor #%d en %s", actorIndex, fileName));
                                progressCallback.update(String.format("  >   Ubicacion del StaticMesh: %.1f, %.1f, %.1f", actorX, actorY, actorZ));
                                progressCallback.update(String.format("  >   Coordenadas objetivo:   %.1f, %.1f, %.1f", targetX, targetY, targetZ));
                                progressCallback.update(String.format("  >   Distancia exacta: %.1f unidades", totalDistance));
                                progressCallback.update(String.format("  >   Diferencias: X=%.1f, Y=%.1f, Z=%.1f", distanceX, distanceY, distanceZ));
                                
                                // Guardar el StaticMeshActor encontrado para el resultado final
                                StaticMeshActorMatch actorMatch = new StaticMeshActorMatch(
                                    fileName, actorIndex, actorX, actorY, actorZ, targetX, targetY, targetZ);
                                result.addFoundActor(actorMatch);
                            }
                        }
                    } catch (Exception e) {
                        // Ignorar errores en actores individuales
                        continue;
                    }
                }
                
                return foundCount;
            }
            
        } catch (Exception e) {
            // Si hay error procesando el archivo, retornar 0
            return 0;
        }
    }
    
}
