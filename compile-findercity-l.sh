#!/bin/bash
# Compilacion de Finder City para Linux/Mac/Git Bash
# Autor: Ak4n1 - Terra Team

echo "=== FINDER CITY - COMPILACION ==="
echo

# Verificar que existe el directorio lib con las librerias
if [ ! -f "lib/L2smr.jar" ]; then
    echo "ERROR: No se encontro lib/L2smr.jar"
    echo "Por favor asegurate de que L2smr.jar esta en la carpeta lib/"
    exit 1
fi

# Limpiar directorio dist
echo "Limpiando directorio dist..."
rm -rf dist
mkdir dist

# Compilar todas las clases de una vez
echo "Compilando todas las clases Java..."
echo
javac -cp "lib/L2smr.jar" -d dist \
    src/main/java/com/jeep/findercity/*.java \
    src/main/java/com/jeep/findercity/analyzer/*.java \
    src/main/java/com/jeep/findercity/finder/*.java \
    src/main/java/com/jeep/findercity/model/*.java \
    src/main/java/com/jeep/findercity/util/*.java

if [ $? -ne 0 ]; then
    echo "ERROR: Fallo la compilacion"
    exit 1
fi

echo
echo "=== COMPILACION EXITOSA ==="
echo "Clases compiladas en: dist/"
echo
