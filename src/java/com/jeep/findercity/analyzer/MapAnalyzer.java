package com.jeep.findercity.analyzer;

import com.jeep.findercity.model.MapInfo;
import com.jeep.findercity.model.SearchResult;
import com.jeep.findercity.util.MapMetadataExtractor;
import com.jeep.findercity.util.ProgressCallback;
import java.io.File;
import java.util.List;

/**
 * Analiza archivos .unr para buscar coordenadas especificas
 * Implementa la estrategia hibrida: metadatos + analisis de objetos
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
            // Fase 1: Extraer metadatos de todos los mapas
            progressCallback.update("Fase 1: Extrayendo metadatos de mapas...");
            List<MapInfo> allMaps = MapMetadataExtractor.extractAllMapInfo(mapsDir, progressCallback);
            
            // Fase 2: Filtrar mapas candidatos por metadatos
            progressCallback.update("Fase 2: Filtrando mapas candidatos...");
            List<MapInfo> candidateMaps = MapMetadataExtractor.getCandidateMaps(allMaps, targetX, targetY, DEFAULT_TILE_SIZE);
            result.setCandidateMaps(candidateMaps.size());
            
            if (candidateMaps.isEmpty()) {
                progressCallback.update("No se encontraron mapas candidatos.");
                result.setTotalMapsAnalyzed(allMaps.size());
                result.setSearchTime(System.currentTimeMillis() - startTime);
                return result;
            }
            
            progressCallback.update(String.format("Mapas candidatos encontrados: %d", candidateMaps.size()));
            
            // Fase 3: Analizar objetos solo en mapas candidatos
            progressCallback.update("Fase 3: Analizando objetos en mapas candidatos...");
            boolean found = analyzeObjectsInCandidateMaps(candidateMaps, targetX, targetY, targetZ, progressCallback, result);
            
            result.setTotalMapsAnalyzed(allMaps.size());
            result.setSearchTime(System.currentTimeMillis() - startTime);
            
        } catch (Exception e) {
            progressCallback.update("Error durante el analisis: " + e.getMessage());
            result.setTotalMapsAnalyzed(0);
            result.setSearchTime(System.currentTimeMillis() - startTime);
        }
        
        return result;
    }
    
    /**
     * Analiza objetos en los mapas candidatos para buscar coordenadas exactas
     */
    private static boolean analyzeObjectsInCandidateMaps(List<MapInfo> candidateMaps, 
                                                        float targetX, float targetY, float targetZ,
                                                        ProgressCallback progressCallback, 
                                                        SearchResult result) {
        
        boolean found = false;
        int analyzed = 0;
        
        for (MapInfo mapInfo : candidateMaps) {
            analyzed++;
            String progressMsg = String.format("Analizando %d/%d mapas candidatos: %s", 
                                             analyzed, candidateMaps.size(), mapInfo.getFileName());
            progressCallback.update(progressMsg);
            
            try {
                // TODO: Implementar analisis real de objetos usando UnrealPackage
                // Por ahora simulamos el analisis
                boolean foundInThisMap = simulateObjectAnalysis(mapInfo, targetX, targetY, targetZ);
                
                if (foundInThisMap) {
                    result.addFoundMap(mapInfo.getFileName());
                    found = true;
                }
                
                // Pequeña pausa para mostrar progreso
                Thread.sleep(50);
                
            } catch (Exception e) {
                progressCallback.update("Error analizando " + mapInfo.getFileName() + ": " + e.getMessage());
            }
        }
        
        return found;
    }
    
    /**
     * Simula el analisis de objetos en un mapa
     * TODO: Reemplazar con analisis real usando UnrealPackage
     */
    private static boolean simulateObjectAnalysis(MapInfo mapInfo, float targetX, float targetY, float targetZ) {
        // Simulacion: buscar coordenadas cercanas a las del mapa
        float mapCenterX = (mapInfo.getMapX() + 0.5f) * DEFAULT_TILE_SIZE;
        float mapCenterY = (mapInfo.getMapY() + 0.5f) * DEFAULT_TILE_SIZE;
        
        // Simular que encontramos las coordenadas si estan cerca del centro del mapa
        float distance = (float) Math.sqrt(Math.pow(targetX - mapCenterX, 2) + Math.pow(targetY - mapCenterY, 2));
        return distance < DEFAULT_TILE_SIZE * 0.3f; // 30% del tamaño del tile
    }
    
}
