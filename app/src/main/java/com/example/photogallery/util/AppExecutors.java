package com.example.photogallery.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors ins;

    public static AppExecutors getIns() {
        if (ins == null) {
            ins = new AppExecutors();
        }
        return ins;
    }

    private final ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return networkIO;
    }
}
