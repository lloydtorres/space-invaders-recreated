package client;

import javax.swing.*;
import java.awt.*;

// This is a connection frame where server address, server port and player name are entered
// Pressing the button calls a method in the client class
public class ConnectionFrame extends JFrame {
    public ConnectionFrame(String title, Client listenerClient){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JLabel ipLabel = new JLabel("IP Address:");
        JTextField ipField = new JTextField();
        JLabel portLabel = new JLabel("Port:");
        JTextField portField = new JTextField();
        JLabel nameLabel = new JLabel("Player Name:");
        JTextField nameField = new JTextField();

        addDefaultValues(ipField, portField, nameField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> {
            String ipAddress = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            String playerName = nameField.getText();
            listenerClient.handleConnectPress(ipAddress, port, playerName);
            dispose();
        });

        panel.add(ipLabel);
        panel.add(ipField);
        panel.add(portLabel);
        panel.add(portField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(connectButton);
        add(panel);
    }

    private void addDefaultValues(JTextField ipField, JTextField portField, JTextField nameField){
        ipField.setText("localhost");
        portField.setText("12345");
        nameField.setText("someone");
    }
}
