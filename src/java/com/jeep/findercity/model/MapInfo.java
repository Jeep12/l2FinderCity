package com.jeep.findercity.model;

/**
 * Informacion basica de un mapa extraida de sus metadatos
 */
public class MapInfo {
    private String fileName;
    private int mapX;
    private int mapY;
    private boolean isValid;
    
    public MapInfo() {
    }
    
    public MapInfo(String fileName, int mapX, int mapY, boolean isValid) {
        this.fileName = fileName;
        this.mapX = mapX;
        this.mapY = mapY;
        this.isValid = isValid;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public int getMapX() {
        return mapX;
    }
    
    public void setMapX(int mapX) {
        this.mapX = mapX;
    }
    
    public int getMapY() {
        return mapY;
    }
    
    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public void setValid(boolean valid) {
        isValid = valid;
    }
    
    @Override
    public String toString() {
        return String.format("%s (MapX: %d, MapY: %d)", fileName, mapX, mapY);
    }
}
