package com.university.byteme;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileHandling {
    public static void saveOrderHistory(String email, Orders order) {
        File directory = new File("orderHistory");
        File userFile = new File(directory, email + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write("Order details for " + order.getCustomerName() + ":\n");

            for (Map.Entry<Item, Integer> entry : order.getItemQuantity().entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                int itemTotal = quantity * item.getPrice();
                writer.write("Item: " + entry.getKey().getName() + ", Quantity: " + entry.getValue() + ", Total: " + itemTotal);
                writer.newLine();
            }
            writer.write("Special Request: " + (order.getSpecialRequest() != null ? order.getSpecialRequest() : "None"));
            writer.newLine();
            writer.write("------------");
            writer.newLine();

            System.out.println("Order history saved for " + email);
        } catch (IOException e) {
            System.out.println("ERROR! Cannot save the order history! " + e.getMessage());
        }
    }

    public static void saveCart(String email, Map<Item, Integer> cartFromUser) {
        File directory = new File("temporaryCart");
        File cartFile = new File(directory, email + "_cart.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cartFile))) {
            for (Map.Entry<Item, Integer> entry : cartFromUser.entrySet()) {
                writer.write(entry.getKey().getName() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving cart data: " + e.getMessage());
        }
    }

    public static void clearCartFile(String email) {
        File cartFile = new File("temporaryCart/" + email + "_cart.txt");
        if (cartFile.exists() && cartFile.delete()) {
            System.out.println("Temporary cart file deleted for: " + email);
        } else {
            System.out.println("No temporary cart file found to delete for " + email);
        }
    }

}
