package com.university.byteme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private JFrame frame;
    private JPanel homePanel;
    private JPanel menuPanel;
    private JPanel pendingPanel;

    private CardLayout cardLayout;

    private List<String> menuList;
    private List<String> pendingOrders;

    public GUI() {
        menuList = new ArrayList<>();
        pendingOrders = new ArrayList<>();
        readFile("menuFile.txt", menuList);
        readFile("pendingOrders.txt", pendingOrders);
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Byte Me! GUI");
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        createHomePanel();
        createMenuPanel();
        createPendingOrdersPanel();

        frame.add(homePanel, "Home");
        frame.add(menuPanel, "Menu");
        frame.add(pendingPanel, "Pending");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    private void createHomePanel() {
        homePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel titleLabel = new JLabel("Welcome to Byte Me!", SwingConstants.CENTER);
        JButton menuButton = new JButton("Menu");
        JButton pendingButton = new JButton("Pending Orders");

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Menu");
            }
        });

        pendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Pending");
            }
        });

        homePanel.add(titleLabel);
        homePanel.add(menuButton);
        homePanel.add(pendingButton);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        JTextArea menuArea = new JTextArea();
        menuArea.setEditable(false);

        StringBuilder menuText = new StringBuilder(); // added this to create categories in menu gui

        menuText.append("Snacks:\n");
        for (String item : menuList) {
            if (item.toLowerCase().contains("snacks")) {
                menuText.append(formatItemDetails(item)).append("\n");
            }
        }

        menuText.append("\nBeverages:\n");
        for (String item : menuList) {
            if (item.toLowerCase().contains("beverages")) {
                menuText.append(formatItemDetails(item)).append("\n");
            }
        }

        menuArea.setText(menuText.toString());
        JScrollPane scrollPane = new JScrollPane(menuArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cardLayout.show(frame.getContentPane(), "Home");
            }
        });

        menuPanel.add(scrollPane, BorderLayout.CENTER);
        menuPanel.add(backButton, BorderLayout.SOUTH);
    }

    private String formatItemDetails(String item) {
        String[] parts = item.split(",");
        // parts is basically for no. of splits
        // 0th - itemname, 1st - price, 2nd - availability
        if (parts.length >= 3) {
            return String.format("- %s (Price: %s, %s)", parts[0].trim(), parts[1].trim(), parts[2].trim());
        }
        return item;
    }


    private void createPendingOrdersPanel() {
        pendingPanel = new JPanel(new BorderLayout());
        JTextArea pendingArea = new JTextArea();
        pendingArea.setEditable(false);
        pendingArea.setText(String.join("\n", pendingOrders));
        JScrollPane scrollPane = new JScrollPane(pendingArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(frame.getContentPane(), "Home");
            }
        });

        pendingPanel.add(scrollPane, BorderLayout.CENTER);
        pendingPanel.add(backButton, BorderLayout.SOUTH);
    }

    private void readFile(String fileName, List<String> list) {
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}
