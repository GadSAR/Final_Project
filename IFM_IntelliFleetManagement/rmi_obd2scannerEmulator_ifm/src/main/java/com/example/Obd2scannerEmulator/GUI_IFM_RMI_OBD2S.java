package com.example.Obd2scannerEmulator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Random;

public class GUI_IFM_RMI_OBD2S extends JFrame {

    public GUI_IFM_RMI_OBD2S() {
        final JTextField email;
        final JTextField password;
        final JTextField carId;
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

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel carIdLabel = new JLabel("Car ID:");
        email = new JTextField(20);
        password = new JTextField(20);
        carId = new JTextField(20);
        JButton button1 = new JButton("Start Server");
        JButton button2 = new JButton("Start Client");
        button1.setPreferredSize(new Dimension(150, 40));

        panel.add(emailLabel);
        panel.add(email);
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(carIdLabel);
        panel.add(carId);
        panel.add(button1);
        panel.add(button2);

        button1.addActionListener(e -> {
            Thread serverThread = new Thread(() -> {
                try {
                    RMIApplication.main(new String[]{});
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });
            serverThread.start();
        });
        button2.addActionListener(e -> {
            Thread clientThread = new Thread(() -> {
                try {
                    new Client_Obd2Scanner(email.getText(), password.getText(), carId.getText());
                } catch (NotBoundException | ServerNotActiveException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            clientThread.start();
        });

        panel.setComponentZOrder(button1, 0);
        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI_IFM_RMI_OBD2S::new);
    }
}
