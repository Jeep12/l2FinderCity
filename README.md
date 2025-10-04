# Finder City - Buscador de mapas

El objetivo principal es **encontrar mapas  fácilmente** para poder editarlos mediante **Unreal Engine 3** o **L2Smr**. El programa devuelve **además del mapa**, los **StaticMeshActors más cercanos** a las coordenadas ingresadas dentro de un **rango de 500 unidades**.

## Disclaimer
Este software fue desarrollado por **Ak4n1/Jeep12** como herramienta personal para edición de mapas de Lineage 2. Se proporciona "tal como está" **sin garantías** de ningún tipo. El uso de este software es bajo tu propia responsabilidad.


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

<img width="420" height="566" alt="image" src="https://github.com/user-attachments/assets/3228507e-60e6-4cce-85dc-da090a34aa65" />


## Uso

1. Ejecuta `run.bat`
2. Selecciona la opción "1" (Buscar coordenadas - modo rápido)
3. Ingresa las coordenadas separadas por espacios: `X Y Z`
   - Ejemplo: `82118 148674 -3379`
4. Espera a que termine el análisis
5. Revisa los resultados

<img width="466" height="386" alt="image" src="https://github.com/user-attachments/assets/0e127bf7-269c-4a52-86a3-fdbd0b4a22b1" />


## Consejo

Para mejores resultados, intenta usar coordenadas que estén cerca de un StaticMeshActor (objeto estático) en el juego. El programa busca StaticMeshActors dentro de un rango de 500 unidades de las coordenadas que ingreses.



## Requisitos

- Java 8 o superior



**Ak4n1 - Terra Team**
