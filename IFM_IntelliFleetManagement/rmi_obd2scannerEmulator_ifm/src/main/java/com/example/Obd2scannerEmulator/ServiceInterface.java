package com.example.Obd2scannerEmulator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface ServiceInterface extends Remote {
    String connect(String input, String clientName)
            throws RemoteException, MalformedURLException, NotBoundException, ServerNotActiveException;

    String message(String VEHICLE_ID, Integer SPEED, Double THROTTLE_POS, Integer ENGINE_RPM,
                   Double ENGINE_LOAD, Integer ENGINE_COOLANT_TEMP, Integer INTAKE_MANIFOLD_PRESSURE, Double MAF,
                   Double FUEL_LEVEL, Integer FUEL_PRESSURE, Double TIMING_ADVANCE, String TROUBLE_CODES, Byte ISSUES)
            throws RemoteException, ServerNotActiveException;

    String SaveToDatabase(String VEHICLE_ID, Integer SPEED, Double THROTTLE_POS, Integer ENGINE_RPM,
                          Double ENGINE_LOAD, Integer ENGINE_COOLANT_TEMP, Integer INTAKE_MANIFOLD_PRESSURE, Double MAF,
                          Double FUEL_LEVEL, Integer FUEL_PRESSURE, Double TIMING_ADVANCE, String TROUBLE_CODES, Byte ISSUES)
            throws RemoteException, ServerNotActiveException;
}
