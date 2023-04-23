package com.example.Obd2scannerEmulator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class ServiceInterfaceImpl extends UnicastRemoteObject implements ServiceInterface {

    ConfigurableApplicationContext context;
    DateTimeFormatter dtf;


    protected ServiceInterfaceImpl(ConfigurableApplicationContext context) throws RemoteException {
        super();
        this.context = context;
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    }

    @Override
    public String connect(String input, String VEHICLE_ID) throws RemoteException, ServerNotActiveException {
        System.out.println("Client " + RemoteServer.getClientHost() + " is connected" + "name: " + VEHICLE_ID);
        return "From server " + input;
    }

    @Override
    public String message(String VEHICLE_ID, Integer SPEED, Double THROTTLE_POS, Integer ENGINE_RPM,
                          Double ENGINE_LOAD, Integer ENGINE_COOLANT_TEMP, Integer INTAKE_MANIFOLD_PRESSURE, Double MAF,
                          Double FUEL_LEVEL, Integer FUEL_PRESSURE, Double TIMING_ADVANCE, String TROUBLE_CODES, Byte ISSUES)
            throws RemoteException, ServerNotActiveException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        System.out.print(now + "-> ");
        System.out.println("Client " + RemoteServer.getClientHost() + ", name: " + VEHICLE_ID + ":" + SPEED);

        return "From server: OK";

    }

    @Override
    public String SaveToDatabase(String VEHICLE_ID, Integer SPEED, Double THROTTLE_POS, Integer ENGINE_RPM,
                                 Double ENGINE_LOAD, Integer ENGINE_COOLANT_TEMP, Integer INTAKE_MANIFOLD_PRESSURE, Double MAF,
                                 Double FUEL_LEVEL, Integer FUEL_PRESSURE, Double TIMING_ADVANCE, String TROUBLE_CODES, Byte ISSUES)
            throws RemoteException, ServerNotActiveException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        System.out.print(now + "-> ");
        System.out.println("Client " + RemoteServer.getClientHost() + ", name: " + VEHICLE_ID + ":" + SPEED);

        Info info = context.getBean(Info.class);
        info.setIP(RemoteServer.getClientHost());
        info.setVEHICLE_ID(VEHICLE_ID);
        info.setSPEED(SPEED);
        info.setTHROTTLE_POS(THROTTLE_POS);
        info.setENGINE_RPM(ENGINE_RPM);
        info.setENGINE_LOAD(ENGINE_LOAD);
        info.setENGINE_COOLANT_TEMP(ENGINE_COOLANT_TEMP);
        info.setINTAKE_MANIFOLD_PRESSURE(INTAKE_MANIFOLD_PRESSURE);
        info.setMAF(MAF);
        info.setFUEL_LEVEL(FUEL_LEVEL);
        info.setFUEL_PRESSURE(FUEL_PRESSURE);
        info.setTIMING_ADVANCE(TIMING_ADVANCE);
        info.setTROUBLE_CODES(TROUBLE_CODES);
        info.setISSUES(ISSUES);
        info.setTIME(now);

        // goto the DemoApplication object ...
        RMIApplication rmiDbApplication = context.getBean(RMIApplication.class);
        //... and use customerRepository.save(customer); -> method
        // this will save also the product record, because customer has the product info
        rmiDbApplication.infoRepository.save(info);

        // only for singleton scope
        BeanFactory beanFactory = context.getBeanFactory();
        ((DefaultListableBeanFactory) beanFactory).destroySingleton("info");

        return "From server: Saved";

    }

}

