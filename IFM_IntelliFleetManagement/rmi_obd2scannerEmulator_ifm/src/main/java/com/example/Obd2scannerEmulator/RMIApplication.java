package com.example.Obd2scannerEmulator;

import com.example.Obd2scannerEmulator.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
public class RMIApplication {
    @Autowired
    InfoRepository infoRepository;


    public static void main(String[] args) throws RemoteException {
        ConfigurableApplicationContext context =
                SpringApplication.run(RMIApplication.class, args);

        System.out.println("\n\n\n\n");

        /*
        creating this welcome message ...
         */
        // https://devops.datenkollektiv.de/banner.txt/index.html

        System.out.println("  ____                                                _                 ____                       _         \n" +
                " / ___|    ___   _ __  __   __   ___   _ __          (_)  ___          |  _ \\    ___    __ _    __| |  _   _ \n" +
                " \\___ \\   / _ \\ | '__| \\ \\ / /  / _ \\ | '__|         | | / __|         | |_) |  / _ \\  / _` |  / _` | | | | |\n" +
                "  ___) | |  __/ | |     \\ V /  |  __/ | |            | | \\__ \\         |  _ <  |  __/ | (_| | | (_| | | |_| |\n" +
                " |____/   \\___| |_|      \\_/    \\___| |_|            |_| |___/         |_| \\_\\  \\___|  \\__,_|  \\__,_|  \\__, |\n" +
                "                                                                                                       |___/ ");

        // register the name
        /*
        The main() method creates an instance of the remote object and registers it in the
        JNDI tree, by binding it to a name
        (a URL that points to the implementation of the object).
        A client that needs to obtain a proxy to use the object remotely will be able
        to look up the object by name.
         */
        Registry registry = LocateRegistry.createRegistry(6002);
        registry.rebind("hello", new ServiceInterfaceImpl(context));
        System.out.println("Server ready...");
    }
}
