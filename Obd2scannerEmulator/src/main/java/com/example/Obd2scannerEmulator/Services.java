package com.example.Obd2scannerEmulator;

public class Services {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
