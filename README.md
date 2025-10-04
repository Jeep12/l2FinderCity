# Finder City - Buscador de Coordenadas

Programa para buscar un mapa .unr que contenga coordenadas específicas del juego Lineage 2.

## Instalación

1. Copia tus archivos de mapa `.unr` a la carpeta `maps\`
2. ¡Listo!

## Compilación

**Opción fácil - Usa los scripts:**

**Para Windows:**
```bash
compile-findercity.bat
```

**Para Linux/Mac/Git Bash:**
```bash
chmod +x compile-findercity.sh
./compile-findercity.sh
```

**O simplemente ejecuta `run.bat`** - compila automáticamente si es necesario.

## Uso

1. Ejecuta `run.bat`
2. Selecciona la opción "1" (Buscar coordenadas - modo rápido)
3. Ingresa las coordenadas separadas por espacios: `X Y Z`
   - Ejemplo: `82118 148674 -3379`
4. Espera a que termine el análisis
5. Revisa los resultados

## Consejo

Para mejores resultados, intenta usar coordenadas que estén cerca de un StaticMeshActor (objeto estático) en el juego. El programa busca StaticMeshActors dentro de un rango de 500 unidades de las coordenadas que ingreses.



## Requisitos

- Java 8 o superior



**Ak4n1 - Terra Team**
