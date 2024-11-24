package com.university.byteme;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Customer> customers=new ArrayList<>();
    static Admin admin = new Admin("mess", "mess");
    static List<Item> menu = new ArrayList<>();
    static List<Orders> pendingOrders = new ArrayList<>();
    static List<Orders> ordersCompleted = new ArrayList<>();
    static List<Reviews> reviews = new ArrayList<>();


    //static List<String> vipCustomers= new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        customers.add(new Customer("abc", "abc", "vip" ));
        customers.add(new Customer("def", "def", "vip" ));
        customers.add(new Customer("raghav", "raghav", "regular" ));
        menu.add(new Item("Samosa", 20, true, "snacks"));
        menu.add(new Item("Spring Roll", 50, true, "snacks"));
        menu.add(new Item("Momos", 60, true, "snacks"));
        menu.add(new Item("Mojito", 60, true, "beverages"));
        menu.add(new Item("Coffee", 35, true, "beverages"));
        while (true){
            System.out.println("1. Login as Admin\n2. Login as Customer\n0. Exit");
            System.out.println("Enter choice: ");
            int choice = sc.nextInt();
            if (choice == 0) {
                System.out.println("Exiting the system...");
                break;
            }
            if (choice==1){
                Admin hij = loginAsAdmin();   // Admin hij = new Admin(); hij.loginAsadmin() technically right but practically some issue
                if (hij != null) {
                    adminInterface();
                } else {
                    System.out.println("exiting...");
                }
            }
            if (choice==2){
                Customer abc = loginAsCustomer();
                if (abc!=null){
                    customerInterface(abc);
                }
                else {
                    System.out.println("exiting...");
                }
            }
        }
    }

    static Admin loginAsAdmin() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = input.nextLine();

        if (email == null || email.isEmpty()) {
            System.out.println("Invalid email, exiting... ");
            return null;
        }

        if (email.equals(admin.getEmail())) {
            System.out.println("Enter password: ");
            String password = input.nextLine();

            if (admin.checkPassword(password)) {
                return admin;
            }

        }
        System.out.println("Admin with email: '" + email + "' not found");
        return null;
    }

    static Customer loginAsCustomer(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = input.nextLine();

        if (email == null || email.isEmpty()) {
            System.out.println("Invalid email, exiting... ");
            return null;
        }
        for (Customer customer: customers){
            if (customer.getEmail().equals(email)){
                System.out.println("Enter password: ");
                String password = input.nextLine();


                if (customer.checkPassword(password)) {
                    return customer;
                }
            }
        }
        System.out.println("Set Password");
        String password = input.nextLine();

        System.out.println("Do you want to become a VIP member by Paying Rs. 59 every 3 months (yes/no): ");
        String choice = input.nextLine();

        Customer c1 = null;

        if (choice.equalsIgnoreCase("yes")){
            System.out.println("Congratulations! You are a VIP Member now, your orders will be of higher priority :) \n");
            c1 = new Customer(email, password, "vip");
        }
        else{
            c1 = new Customer(email, password, "regular");
        }
        customers.add(c1);
        return c1;


        //return null;
    }

    static void adminInterface(){
        Scanner sc = new Scanner(System.in);
        while (true){
            admin.showMenu();
            System.out.println("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice==1){
                admin.viewMenu(menu);
            }
            else if (choice==2){
                admin.addItem(menu);
            } else if (choice==3) {
                admin.updateItem(menu);
            } else if (choice==4) {
                admin.removeItem(menu);
            } else if (choice==5) {
                admin.viewPendingOrders(pendingOrders, customers);
            } else if (choice==6) {
                admin.completeOrder(pendingOrders, ordersCompleted, customers);
            } else if (choice==7) {
                admin.dailySalesReport(ordersCompleted);
            } else if (choice==0) {
                return;
            } else{
                System.out.println("Invalid choice.");
            }
        }
    }

    static void customerInterface(Customer customer) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            customer.showMenu();
            System.out.println("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice==1){
                customer.browseMenu(menu);
            } else if (choice==2) {
                customer.searchMenu(menu);
            } else if (choice==3) {
                customer.addToCart(menu);
            } else if (choice==4) {
                customer.removeFromCart();
            } else if (choice==5) {
                customer.viewMyCart();
            } else if (choice==6) {
                customer.clearMyCart();
            } else if (choice==7) {
                customer.updateCart();
            } else if (choice==8) {
                customer.placeMyOrder(pendingOrders, customers);
            } else if (choice==9) {
                customer.viewPendingOrder(pendingOrders);
            }else if (choice==10) {
                customer.viewOrderHistory(ordersCompleted);
            } else if (choice==11) {
                customer.submitReviews(reviews);
            } else if (choice==12) {
                customer.seeReviews(reviews);
            } else if (choice==0) {
                return;
            } else{
                System.out.println("Invalid choice.");
            }
        }
    }
}
