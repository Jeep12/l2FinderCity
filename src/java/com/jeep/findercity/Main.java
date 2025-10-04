package com.jeep.findercity;

import com.jeep.findercity.finder.CoordinateFinder;

/**
 * Clase principal del programa Finder City
 * Punto de entrada para la aplicacion de busqueda de coordenadas
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
            
            // Iniciar el programa principal
            CoordinateFinder.main(args);
            
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicacion:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
