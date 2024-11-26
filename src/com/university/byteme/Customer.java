package com.university.byteme;

import java.util.*;

public class Customer extends User{
    private Cart cart;
    private List<Orders> orderHistory;
    private String priority;
    private String specialRequest;

    public Customer(String email, String password, String priority) {
        super(email, password);
        this.priority=priority;
        this.cart = new Cart();
    }

    public String getPriority() {
        return priority;
    }

    public void browseMenu(List<Item> menu){
        System.out.println("                Menu");
        System.out.println();
        System.out.println("               Snacks");
        for (Item item: menu){
            if (item.getCategory().equalsIgnoreCase("Snacks")){
                System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
            }
        }
        System.out.println();
        System.out.println("               Beverages");
        for (Item item: menu){
            if (item.getCategory().equalsIgnoreCase("beverage") || item.getCategory().equalsIgnoreCase("beverages")){
                System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
            }
        }
    }
    public void searchMenu(List<Item> menu){
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose search option:");
        System.out.println("1. Search by Item Name");
        System.out.println("2. Search by Category");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice==1) {
            System.out.print("Enter item name: ");
            String name = sc.nextLine();
            for (Item item : menu) {
                if (item.getName().equalsIgnoreCase(name)) {
                    System.out.printf("%s - price: %d, available: %s \n", item.getName(), item.getPrice(), item.isAvailable() ? "yes" : "no");
                }
            }
        } else if (choice==2) {
            System.out.print("Enter category (snacks, beverages): ");
            String category = sc.nextLine();
            for (Item item : menu) {
                if (item.getCategory().equalsIgnoreCase(category)) {
                    System.out.printf("%s - price: %d, available: %s\n", item.getName(), item.getPrice(), item.isAvailable() ? "yes" : "no");
                }
            }
        } else {
            System.out.println("Invalid Choice");
            return;
        }
    }

    public static boolean outOfStock(Item item){
        if (item.isAvailable()){
            return true;
        }
        else {
            return false;
        }
    }

    public void addToCart(List<Item> menu){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        for (Item item: menu){
            if (item.getName().equalsIgnoreCase(name)){
                if (item.isAvailable()) {
                    cart.addItems(item, quantity);
                }
                else{
                    System.out.println("Item not available, can't add to cart.");
                }
            }
        }
        FileHandling.saveCart(this.getEmail(), cart.getItemQuantity());

    }

    public void updateCart() {
        cart.viewCart();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the item name to update: ");
        String itemName = sc.nextLine();
        System.out.print("Enter the new quantity: ");
        int newQuantity = sc.nextInt();

        for (Map.Entry<Item, Integer> entry : cart.getItemQuantity().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (entry.getKey().getName().equalsIgnoreCase(itemName)) {
                if (newQuantity==0){
                    cart.removeItem(item);
                    FileHandling.saveCart(this.getEmail(), cart.getItemQuantity());
//                    System.out.println("inside remove from cart");
                    return;
                }
                cart.getItemQuantity().put(entry.getKey(), newQuantity);
                System.out.println("Quantity updated successfully.");
                FileHandling.saveCart(this.getEmail(), cart.getItemQuantity());
                return;
            }
        }

        System.out.println("Item not found in your cart.");
    }


    public void removeFromCart(){
//      print myCart, input itemname to remove, iterate over myCart, if name matches remove it
        cart.removeItem();
        FileHandling.saveCart(this.getEmail(), cart.getItemQuantity());
    }

    public void viewMyCart(){
        cart.viewCart();
        System.out.println("Any Special Requests (yes/no): ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();
        sc.nextLine();
        String request = "";
        if (choice.equalsIgnoreCase("yes")){
            System.out.println("Add request: ");
            request = sc.nextLine();
        }
        else{
            return;
        }
        this.specialRequest=request;
    }
    public void clearMyCart(){
        cart.clearCart();
        FileHandling.saveCart(this.getEmail(), cart.getItemQuantity());
    }
    public void placeMyOrder(List<Orders> pendingOrders, List<Customer> customersFromMain){
        if (cart.getItemQuantity().isEmpty()) {
            System.out.println("Cart is empty. Please add items before placing an order.");
            return;
        }
        Orders order = new Orders(this.getEmail(), new TreeMap<>(cart.getItemQuantity()), specialRequest);
        pendingOrders.add(order);
        System.out.println("Placing order for " + this.getEmail());
        cart.clearCart();
        FileHandling.clearCartFile(this.getEmail());
        this.specialRequest="";
        System.out.println("Order placed successfully");
        displayBill(order);
        FileHandling.savePendingOrder(pendingOrders, customersFromMain);
    }

    private void displayBill(Orders order) {
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("|                  Order Bill                |");
        System.out.println("----------------------------------------------");
        System.out.println("|      Item      |   Quantity   |    Price   |");

        int totalAmount = 0;

        for (Map.Entry<Item, Integer> entry : order.getItemQuantity().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * item.getPrice();
            totalAmount += itemTotal;

            System.out.printf("|   %-12s |   %-10d |   %-8d |\n", item.getName(), quantity, itemTotal);
        }

        System.out.println("----------------------------------------------");
        System.out.printf("|  TotalAmount   |              |   %-8d |\n", totalAmount);
        System.out.println("----------------------------------------------");


        System.out.println();
    }

    public void viewPendingOrder(List<Orders> pendingOrdersFromMain){
        if (pendingOrdersFromMain.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        for (Orders orders: pendingOrdersFromMain){
            if (orders.getCustomerName().equals(this.getEmail())){
                System.out.println("Order details for "+orders.getCustomerName()+": ");
                for (Map.Entry<Item, Integer> entry : orders.getItemQuantity().entrySet()) {
                    Item item = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.printf("Item: %s - Quantity: %d - Price: %d ", item.getName(), quantity, quantity*(item.getPrice()));
                    System.out.println();
                }
                String specialRequest = orders.getSpecialRequest();
                System.out.println("Special Request: " + ((specialRequest != null && !specialRequest.isEmpty()) ? specialRequest : "None"));
            }
        }
    }
    public void viewOrderHistory(List<Orders> completeOrdersFromMain){

        for (Orders orders : completeOrdersFromMain) {
            if (orders.getCustomerName().equals(this.getEmail())) {
                System.out.println("Order details for " + orders.getCustomerName() + ": ");
                System.out.println("Items in the order:");
                for (Map.Entry<Item, Integer> entry : orders.getItemQuantity().entrySet()) {
                    Item item = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.printf("Item: %s - Quantity: %d - Price: %d", item.getName(), quantity, quantity * (item.getPrice()));
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    public void submitReviews(List<Reviews> reviews){
        Scanner sc = new Scanner(System.in);
        System.out.println("Add review: ");
        String description = sc.nextLine();

        reviews.add(new Reviews(description));
        System.out.println("Review submitted.");
    }

    public void seeReviews(List<Reviews> reviews){
        for (Reviews review : reviews ){
            review.printReviews();
        }
    }

    @Override
    void showMenu() {
        System.out.print("\nCustomer interface\n1. View Menu\n2. Search Item\n3. Add item to Cart\n4. Remove item from Cart\n5. View Cart" +
                "\n6. Clear Cart\n7. Update Quantity\n8. Place Order\n9. View Pending Orders\n10. Order History\n11. Add Reviews\n12. See Reviews\n0. Exit\n");
    }
}
