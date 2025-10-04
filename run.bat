@echo off
title Finder City - Buscador de Coordenadas
color 0A

echo.
echo ==========================================
echo    FINDER CITY - BUSCADOR DE COORDENADAS
echo ==========================================
echo.

:menu
echo Selecciona una opcion:
echo.
echo 1. Buscar coordenadas (modo rapido)
echo 2. Buscar coordenadas (modo interactivo)
echo 3. Ver ayuda
echo 4. Salir
echo.
set /p opcion="Ingresa tu opcion (1-4): "

if "%opcion%"=="1" goto modo_rapido
if "%opcion%"=="2" goto modo_interactivo
if "%opcion%"=="3" goto ayuda
if "%opcion%"=="4" goto salir

echo Opcion invalida. Intenta de nuevo.
echo.
goto menu

:modo_rapido
echo.
echo === MODO RAPIDO ===
echo Ingresa las coordenadas separadas por espacios:
echo Ejemplo: 82118 148674 -3379
echo.
set /p coordenadas="Coordenadas (X Y Z): "

if "%coordenadas%"=="" (
    echo No se ingresaron coordenadas.
    echo.
    goto menu
)

echo.
echo Ejecutando busqueda...
echo.

java -cp "dist;lib\L2smr.jar" com.jeep.findercity.Main %coordenadas%

echo.
echo Presiona cualquier tecla para volver al menu...
pause >nul
goto menu

:modo_interactivo
echo.
echo === MODO INTERACTIVO ===
echo.

java -cp "dist;lib\L2smr.jar" com.jeep.findercity.Main

echo.
echo Presiona cualquier tecla para volver al menu...
pause >nul
goto menu

:ayuda
echo.
echo === AYUDA ===
echo.
echo Finder City es un buscador de coordenadas en mapas .unr de Lineage II
echo.
echo Funcionamiento:
echo 1. El programa analiza todos los archivos .unr en la carpeta 'maps'
echo 2. Busca StaticMeshActors cerca de las coordenadas especificadas
echo 3. Muestra los resultados con ubicaciones exactas y distancias
echo.
echo Modos de uso:
echo - Modo rapido: Ingresa coordenadas directamente
echo - Modo interactivo: Usa el menu interno del programa
echo.
echo Ejemplos de coordenadas:
echo - 82118 148674 -3379
echo - 12111 16686 -4582
echo.
echo Presiona cualquier tecla para volver al menu...
pause >nul
goto menu

:salir
echo.
echo Gracias por usar Finder City!
echo.
pause
exit

:error
echo.
echo Error: No se pudo ejecutar el programa.
echo Asegurate de que:
echo 1. El directorio 'maps' existe y contiene archivos .unr
echo 2. El archivo L2smr.jar esta en la carpeta lib\
echo 3. Las clases estan compiladas en dist\
echo.
echo Presiona cualquier tecla para volver al menu...
pause >nul
goto menu
