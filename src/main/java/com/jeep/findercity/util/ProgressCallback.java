package com.jeep.findercity.util;

/**
 * Interface para mostrar progreso durante operaciones largas
 * 
 * @author Ak4n1 - Terra Team
 */
public interface ProgressCallback {
    void update(String message);
}
