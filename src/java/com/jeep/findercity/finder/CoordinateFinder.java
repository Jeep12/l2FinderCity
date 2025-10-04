package com.jeep.findercity.finder;

import com.jeep.findercity.analyzer.MapAnalyzer;
import com.jeep.findercity.model.SearchResult;
import com.jeep.findercity.util.ProgressCallback;
import java.io.File;
import java.util.Scanner;

/**
 * Clase principal para buscar coordenadas en mapas
 * Maneja la interaccion con el usuario y coordina la busqueda
 */
public class CoordinateFinder {
    
    private static final String MAPS_DIR = "maps";
    
    /**
     * Inicia el programa de busqueda de coordenadas
     */
    public static void main(String[] args) {
        System.out.println("=== FINDER CITY - BUSCADOR DE COORDENADAS ===");
        System.out.println("Buscador de coordenadas en mapas .unr");
        System.out.println("=============================================");
        
        File mapsDir = new File(MAPS_DIR);
        if (!mapsDir.exists() || !mapsDir.isDirectory()) {
            System.err.println("Error: No se encontro el directorio 'maps' con archivos .unr");
            System.err.println("Asegurate de que el directorio 'maps' existe y contiene archivos .unr");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("\nOpciones:");
                System.out.println("1. Buscar coordenadas");
                System.out.println("2. Salir");
                System.out.print("Selecciona una opcion (1-2): ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        performCoordinateSearch(mapsDir, scanner);
                        break;
                    case "2":
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opcion invalida. Por favor selecciona 1 o 2.");
                }
                
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }
    }
    
    /**
     * Realiza la busqueda de coordenadas
     */
    private static void performCoordinateSearch(File mapsDir, Scanner scanner) {
        System.out.println("\n--- BUSQUEDA DE COORDENADAS ---");
        System.out.println("Ingresa las coordenadas a buscar:");
        
        float x, y, z;
        
        try {
            System.out.print("Coordenada X: ");
            x = Float.parseFloat(scanner.nextLine().trim());
            
            System.out.print("Coordenada Y: ");
            y = Float.parseFloat(scanner.nextLine().trim());
            
            System.out.print("Coordenada Z: ");
            z = Float.parseFloat(scanner.nextLine().trim());
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Ingresa numeros validos para las coordenadas.");
            return;
        }
        
        System.out.println("\nIniciando busqueda...");
        System.out.println("Coordenadas objetivo: " + x + ", " + y + ", " + z);
        System.out.println("Directorio de mapas: " + mapsDir.getAbsolutePath());
        
        // Crear callback para mostrar progreso
        ProgressCallback progressCallback = new ProgressCallback() {
            @Override
            public void update(String message) {
                System.out.println("> " + message);
            }
        };
        
        // Realizar busqueda
        SearchResult result = MapAnalyzer.searchCoordinates(mapsDir, x, y, z, progressCallback);
        
        // Mostrar resultado
        System.out.println("\n" + result.toString());
        
        if (result.isFound()) {
            System.out.println("\nBusqueda completada exitosamente!");
        } else {
            System.out.println("\nNo se encontraron las coordenadas en ningun mapa.");
            System.out.println("Verifica que las coordenadas sean correctas o que los mapas esten en el directorio 'maps'.");
        }
    }
    
    /**
     * Muestra informacion sobre el programa
     */
    public static void showHelp() {
        System.out.println("\n=== AYUDA ===");
        System.out.println("Finder City es un buscador de coordenadas en mapas .unr");
        System.out.println("\nFuncionamiento:");
        System.out.println("1. El programa analiza todos los archivos .unr en la carpeta 'maps'");
        System.out.println("2. Primero filtra mapas candidatos basandose en metadatos");
        System.out.println("3. Luego analiza objetos solo en los mapas candidatos");
        System.out.println("4. Devuelve el mapa donde se encontraron las coordenadas");
        System.out.println("\nFormato de coordenadas:");
        System.out.println("- X, Y, Z: Coordenadas 3D del mundo del juego");
        System.out.println("- Ejemplo: 12345.67, 67890.12, 100.50");
        System.out.println("\nRequisitos:");
        System.out.println("- Directorio 'maps' con archivos .unr");
        System.out.println("- Archivos .unr validos del juego");
    }
}
