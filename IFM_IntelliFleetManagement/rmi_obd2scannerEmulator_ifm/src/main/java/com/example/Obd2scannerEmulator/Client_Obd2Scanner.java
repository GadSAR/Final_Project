package com.example.Obd2scannerEmulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Client_Obd2Scanner {

    private final UUID userID;
    private final String carID;

    Client_Obd2Scanner(String email, String password, String carID) throws ServerNotActiveException, NotBoundException, IOException {
        this.carID = carID;
        userID = connectUser(email, password);
        startClient();
    }

    public UUID connectUser(String email, String password) {
        try {
            // Create an HttpClient object
            HttpClient client = HttpClient.newHttpClient();

            // Create a JSON request body
            String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

            // Create a HttpRequest object with the endpoint URL and request body
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/ifm_api/auth/connect"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response body
            String responseBody = response.body();
            String uuidString = responseBody.replaceAll("\"", "");
            UUID userID = UUID.fromString(uuidString);
            System.out.println(userID);
            return userID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startClient() throws NotBoundException, ServerNotActiveException, IOException {

        String message = "Hello ";
        System.out.println("Sending to server " + message);
        ServiceInterface serviceInterface = (ServiceInterface)
                Naming.lookup("rmi://localhost:6002/hello");

        String nickname = "car" + carID;

        System.out.println(serviceInterface.connect(message, nickname) + " " + serviceInterface.getClass().getName());

        String csvFile = "src/main/resources/dataCSV/obd2data2.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine(); // read the header row
            String[] headerColumns = header.split(cvsSplitBy);

            int Index_VEHICLE_ID = -1;
            int Index_SPEED = -1;
            int Index_THROTTLE_POS = -1;
            int Index_ENGINE_RPM = -1;
            int Index_ENGINE_LOAD = -1;
            int Index_ENGINE_COOLANT_TEMP = -1;
            int Index_INTAKE_MANIFOLD_PRESSURE = -1;
            int Index_MAF = -1;
            int Index_FUEL_LEVEL = -1;
            int Index_FUEL_PRESSURE = -1;
            int Index_TIMING_ADVANCE = -1;
            int Index_TROUBLE_CODES = -1;

            for (int i = 0; i < headerColumns.length; i++) {
                switch (headerColumns[i]) {
                    case "VEHICLE_ID" -> Index_VEHICLE_ID = i;
                    case "SPEED" -> Index_SPEED = i;
                    case "THROTTLE_POS" -> Index_THROTTLE_POS = i;
                    case "ENGINE_RPM" -> Index_ENGINE_RPM = i;
                    case "ENGINE_LOAD" -> Index_ENGINE_LOAD = i;
                    case "ENGINE_COOLANT_TEMP" -> Index_ENGINE_COOLANT_TEMP = i;
                    case "INTAKE_MANIFOLD_PRESSURE" -> Index_INTAKE_MANIFOLD_PRESSURE = i;
                    case "MAF" -> Index_MAF = i;
                    case "FUEL_LEVEL" -> Index_FUEL_LEVEL = i;
                    case "FUEL_PRESSURE" -> Index_FUEL_PRESSURE = i;
                    case "TIMING_ADVANCE" -> Index_TIMING_ADVANCE = i;
                    case "TROUBLE_CODES" -> Index_TROUBLE_CODES = i;
                }
            }
            if (Index_VEHICLE_ID == -1 || Index_SPEED == -1 || Index_THROTTLE_POS == -1 || Index_ENGINE_RPM == -1
                    || Index_ENGINE_LOAD == -1 || Index_ENGINE_COOLANT_TEMP == -1 || Index_INTAKE_MANIFOLD_PRESSURE == -1
                    || Index_MAF == -1 || Index_FUEL_LEVEL == -1 || Index_FUEL_PRESSURE == -1 || Index_TIMING_ADVANCE == -1 || Index_TROUBLE_CODES == -1) {
                System.out.println("The specified columns were not found in the header.");
                return;
            }
            while ((line = br.readLine()) != null) {
                Random random = new Random();
                String[] row = line.split(cvsSplitBy);

                if (row[Index_VEHICLE_ID].equals(nickname)) {
                    String VEHICLE_ID = row[Index_VEHICLE_ID];
                    Integer SPEED = (row[Index_SPEED].isEmpty()) ? -1 : Integer.parseInt(row[Index_SPEED]);
                    Double THROTTLE_POS = (row[Index_THROTTLE_POS].isEmpty()) ? -1 : Double.parseDouble(row[Index_THROTTLE_POS]);
                    Integer ENGINE_RPM = (row[Index_ENGINE_RPM].isEmpty()) ? -1 : Integer.parseInt(row[Index_ENGINE_RPM]);
                    Double ENGINE_LOAD = (row[Index_ENGINE_LOAD].isEmpty()) ? -1 : Double.parseDouble(row[Index_ENGINE_LOAD]);
                    Integer ENGINE_COOLANT_TEMP = (row[Index_ENGINE_COOLANT_TEMP].isEmpty()) ? -1 : Integer.parseInt(row[Index_ENGINE_COOLANT_TEMP]);
                    Integer INTAKE_MANIFOLD_PRESSURE = (row[Index_INTAKE_MANIFOLD_PRESSURE].isEmpty()) ? -1 : Integer.parseInt(row[Index_INTAKE_MANIFOLD_PRESSURE]);
                    Double MAF = (row[Index_MAF].isEmpty()) ? -1 : Double.parseDouble(row[Index_MAF]);
                    Double FUEL_LEVEL = (row[Index_FUEL_LEVEL].isEmpty()) ? -1 : Double.parseDouble(row[Index_FUEL_LEVEL]);
                    Integer FUEL_PRESSURE = (row[Index_FUEL_PRESSURE].isEmpty()) ? -1 : Integer.parseInt(row[Index_FUEL_PRESSURE]);
                    Double TIMING_ADVANCE = (row[Index_TIMING_ADVANCE].isEmpty()) ? -1 : Double.parseDouble(row[Index_TIMING_ADVANCE]);
                    String TROUBLE_CODES = (row[Index_TROUBLE_CODES].isEmpty()) ? "None" : row[Index_TROUBLE_CODES];
                    Byte ISSUES = (byte) (TROUBLE_CODES.equals("None") ? 0 : 1);

                    System.out.println(serviceInterface.message(userID, VEHICLE_ID, SPEED, THROTTLE_POS, ENGINE_RPM,
                            ENGINE_LOAD, ENGINE_COOLANT_TEMP, INTAKE_MANIFOLD_PRESSURE, MAF,
                            FUEL_LEVEL, FUEL_PRESSURE, TIMING_ADVANCE, TROUBLE_CODES, ISSUES));
                    System.out.println(serviceInterface.SaveToDatabase(userID, VEHICLE_ID, SPEED, THROTTLE_POS, ENGINE_RPM,
                            ENGINE_LOAD, ENGINE_COOLANT_TEMP, INTAKE_MANIFOLD_PRESSURE, MAF,
                            FUEL_LEVEL, FUEL_PRESSURE, TIMING_ADVANCE, TROUBLE_CODES, ISSUES));
                    Services.sleep(random.nextInt(5000) + 10000);
                }
            }
        }

    }
}