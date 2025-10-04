package com.jeep.findercity;

import com.jeep.findercity.finder.CoordinateFinder;

/**
 * Clase principal del programa Finder City
 * Punto de entrada para la aplicacion de busqueda de coordenadas
 * 
 * @author Ak4n1 - Terra Team
 */
public class Main {
    
    /**
     * Metodo principal
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        try {
            // Verificar argumentos de linea de comandos
            if (args.length > 0 && ("--help".equals(args[0]) || "-h".equals(args[0]))) {
                CoordinateFinder.showHelp();
                return;
            }
            
            // Si se pasan coordenadas como parámetros, usarlas directamente
            if (args.length >= 3) {
                try {
                    float x = Float.parseFloat(args[0]);
                    float y = Float.parseFloat(args[1]);
                    float z = Float.parseFloat(args[2]);
                    
                    System.out.println("=== FINDER CITY - BUSCADOR DE COORDENADAS ===");
                    System.out.println("Buscando coordenadas: " + x + ", " + y + ", " + z);
                    System.out.println("=============================================");
                    
                    CoordinateFinder.searchCoordinatesDirectly(x, y, z);
                    return;
                } catch (NumberFormatException e) {
                    System.err.println("Error: Los parámetros deben ser números válidos");
                    System.err.println("Uso: java -cp \"dist;lib\\L2smr.jar\" com.jeep.findercity.Main <x> <y> <z>");
                    return;
                }
            }
            
            // Iniciar el programa principal (modo interactivo)
            CoordinateFinder.main(args);
            
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicacion:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
