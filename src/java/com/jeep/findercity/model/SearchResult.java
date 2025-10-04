package com.jeep.findercity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultado de la busqueda de coordenadas
 */
public class SearchResult {
    private boolean found;
    private List<String> foundInMaps;
    private String searchCoordinates;
    private long searchTime;
    private int totalMapsAnalyzed;
    private int candidateMaps;
    
    public SearchResult() {
        this.foundInMaps = new ArrayList<>();
        this.found = false;
    }
    
    public SearchResult(String searchCoordinates) {
        this();
        this.searchCoordinates = searchCoordinates;
    }
    
    public boolean isFound() {
        return found;
    }
    
    public void setFound(boolean found) {
        this.found = found;
    }
    
    public List<String> getFoundInMaps() {
        return foundInMaps;
    }
    
    public void setFoundInMaps(List<String> foundInMaps) {
        this.foundInMaps = foundInMaps;
    }
    
    public void addFoundMap(String mapName) {
        this.foundInMaps.add(mapName);
        this.found = true;
    }
    
    public String getSearchCoordinates() {
        return searchCoordinates;
    }
    
    public void setSearchCoordinates(String searchCoordinates) {
        this.searchCoordinates = searchCoordinates;
    }
    
    public long getSearchTime() {
        return searchTime;
    }
    
    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }
    
    public int getTotalMapsAnalyzed() {
        return totalMapsAnalyzed;
    }
    
    public void setTotalMapsAnalyzed(int totalMapsAnalyzed) {
        this.totalMapsAnalyzed = totalMapsAnalyzed;
    }
    
    public int getCandidateMaps() {
        return candidateMaps;
    }
    
    public void setCandidateMaps(int candidateMaps) {
        this.candidateMaps = candidateMaps;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE BUSQUEDA ===\n");
        sb.append("Coordenadas: ").append(searchCoordinates).append("\n");
        sb.append("Tiempo: ").append(searchTime).append(" ms\n");
        sb.append("Mapas analizados: ").append(totalMapsAnalyzed).append("\n");
        sb.append("Mapas candidatos: ").append(candidateMaps).append("\n");
        
        if (found) {
            sb.append("RESULTADO: ENCONTRADO en los siguientes mapas:\n");
            for (String map : foundInMaps) {
                sb.append("  - ").append(map).append("\n");
            }
        } else {
            sb.append("RESULTADO: NO ENCONTRADO en ningun mapa\n");
        }
        
        return sb.toString();
    }
}
