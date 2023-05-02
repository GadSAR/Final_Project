package com.example.Obd2scannerEmulator;

public class Services {

    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
