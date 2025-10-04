@echo off
REM Compilacion de Finder City para Windows
REM Autor: Ak4n1 - Terra Team

echo === FINDER CITY - COMPILACION ===
echo.

REM Verificar que existe el directorio lib con las librerias
if not exist lib\L2smr.jar (
    echo ERROR: No se encontro lib\L2smr.jar
    echo Por favor asegurate de que L2smr.jar esta en la carpeta lib\
    pause
    exit /b 1
)

REM Limpiar directorio dist
echo Limpiando directorio dist...
if exist dist rmdir /s /q dist
mkdir dist

REM Compilar todas las clases de una vez
echo Compilando todas las clases Java...
echo.
javac -cp "lib\L2smr.jar" -d dist src\main\java\com\jeep\findercity\*.java src\main\java\com\jeep\findercity\analyzer\*.java src\main\java\com\jeep\findercity\finder\*.java src\main\java\com\jeep\findercity\model\*.java src\main\java\com\jeep\findercity\util\*.java
if %errorlevel% neq 0 goto :error

echo.
echo === COMPILACION EXITOSA ===
echo Clases compiladas en: dist\
echo.
goto :end

:error
echo.
echo ERROR: Fallo la compilacion
pause
exit /b 1

:end
pause
