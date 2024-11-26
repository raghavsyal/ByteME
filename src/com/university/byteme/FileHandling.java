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

    public static void savePendingOrder (List<Orders> pendingOrdersFromMain , List<Customer> customersFromMain){
        File poFile = new File("pendingOrders.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(poFile))){
            for (Orders orders : pendingOrdersFromMain){
                String priority = null;
                for (Customer customer : customersFromMain) {
                    if (customer.getEmail().equals(orders.getCustomerName())) {
                        priority = customer.getPriority();
                        break;
                    }
                }

                bw.write(orders.getCustomerName() + ","+ priority);
                bw.newLine();

                for (Map.Entry<Item, Integer> entry : orders.getItemQuantity().entrySet()){
                    Item item = entry.getKey();
                    int quantity = entry.getValue();
                    int price = quantity * item.getPrice();
                    bw.write(entry.getKey().getName() + "," + entry.getValue() + "," + price);
                    bw.newLine();
                }
                bw.write(orders.getSpecialRequest() != null ? orders.getSpecialRequest() : "None");
                bw.newLine();
                bw.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error saving pending orders: " + e.getMessage());
        }
    }


    public static void addToMenuFile(List<Item> menuList){
        File mf = new File("menuFile.txt");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(mf))){
            for (Item item : menuList){
                bw.write(item.getName() + "," + item.getPrice() + "," + (item.isAvailable() ? "Available" : "OutOfStock") + "," + item.getCategory());
                bw.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Error saving menu "+e.getMessage());
        }
    }
}
