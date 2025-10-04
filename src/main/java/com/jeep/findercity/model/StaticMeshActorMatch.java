package com.jeep.findercity.model;

/**
 * Información detallada de un StaticMeshActor encontrado
 * 
 * @author Ak4n1 - Terra Team
 */
public class StaticMeshActorMatch {
    private String mapName;
    private int actorIndex;
    private float actorX;
    private float actorY;
    private float actorZ;
    private float targetX;
    private float targetY;
    private float targetZ;
    private float distance;
    
    public StaticMeshActorMatch(String mapName, int actorIndex, 
                               float actorX, float actorY, float actorZ,
                               float targetX, float targetY, float targetZ) {
        this.mapName = mapName;
        this.actorIndex = actorIndex;
        this.actorX = actorX;
        this.actorY = actorY;
        this.actorZ = actorZ;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        
        // Calcular distancia
        float distanceX = Math.abs(actorX - targetX);
        float distanceY = Math.abs(actorY - targetY);
        float distanceZ = Math.abs(actorZ - targetZ);
        this.distance = (float) Math.sqrt(distanceX*distanceX + distanceY*distanceY + distanceZ*distanceZ);
    }
    
    // Getters
    public String getMapName() { return mapName; }
    public int getActorIndex() { return actorIndex; }
    public float getActorX() { return actorX; }
    public float getActorY() { return actorY; }
    public float getActorZ() { return actorZ; }
    public float getTargetX() { return targetX; }
    public float getTargetY() { return targetY; }
    public float getTargetZ() { return targetZ; }
    public float getDistance() { return distance; }
    
    @Override
    public String toString() {
        return String.format("StaticMeshActor #%d en %s:\n" +
                           "  Ubicacion: %.1f, %.1f, %.1f\n" +
                           "  Objetivo:  %.1f, %.1f, %.1f\n" +
                           "  Distancia: %.1f unidades",
                           actorIndex, mapName, actorX, actorY, actorZ, 
                           targetX, targetY, targetZ, distance);
    }
}
