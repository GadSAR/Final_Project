package com.example.Obd2scannerEmulator;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Random;

public class GUI_IFM_RMI_OBD2S extends JFrame {
    private final JTextField driverID;
    private final JTextField carID;

    public GUI_IFM_RMI_OBD2S() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Set up the frame
        setTitle("IFM's OBD2 Scanner Emulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel driverLabel = new JLabel("Driver ID:");
        JLabel carLabel = new JLabel("Car ID:");
        driverID = new JTextField(20);
        carID = new JTextField(20);
        JButton button1 = new JButton("Start Server");
        JButton button2 = new JButton("Start Client");
        JButton button3 = new JButton("Start ALl Clients");
        button1.setPreferredSize(new Dimension(150, 40));

        panel.add(driverLabel);
        panel.add(driverID);
        panel.add(carLabel);
        panel.add(carID);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        button1.addActionListener(e -> {
            try {
                RMIApplication.main(new String[]{});
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        button2.addActionListener(e -> {
            try {
                Client_Obd2Scanner.main(new String[]{}, driverID.getText(), carID.getText());
            } catch (MalformedURLException | NotBoundException | RemoteException | ServerNotActiveException ex) {
                throw new RuntimeException(ex);
            }
        });
        button3.addActionListener(e -> {
            for (int i = 0; i < 14; i++) {
                String randomDriverID = generateRandomData();
                String randomCarID = generateRandomData();
                try {
                    Client_Obd2Scanner.main(new String[]{}, randomDriverID, randomCarID);
                } catch (MalformedURLException | NotBoundException | RemoteException | ServerNotActiveException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.setComponentZOrder(button1, 0); // Set button1 to be above everything else in the panel
        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generateRandomData() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI_IFM_RMI_OBD2S::new);
    }
}
