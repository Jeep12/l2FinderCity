package com.jeep.findercity.util;

/**
 * Interface para mostrar progreso durante operaciones largas
 */
public interface ProgressCallback {
    void update(String message);
}
