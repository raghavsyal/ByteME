package com.university.byteme;

import java.util.*;


public class Admin extends User{
    //private List<Item> menu;
//    private List<Orders> pendingOrders;
//    private List<Orders> ordersCompleted;

    public Admin(String name, String password) {
        super(name, password);
        //this.menu = menu;
//        this.pendingOrders = new ArrayList<>();
//        this.ordersCompleted = new ArrayList<>();
    }

    public void viewMenu(List<Item> menuList){
        System.out.println("                Menu");
        System.out.println();
        System.out.println("               Snacks");
        for (Item item: menuList){
            if (item.getCategory().equalsIgnoreCase("Snacks")){
                System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
            }
        }
        System.out.println();
        System.out.println("              Beverages");
        for (Item item: menuList){
            if (item.getCategory().equalsIgnoreCase("beverage") || item.getCategory().equalsIgnoreCase("beverages")){
                System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
            }
        }
    }

    public void addItem(List<Item> menuList){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        int price = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter category name (Snacks, Beverages): ");
        String category = sc.nextLine();
        Item item = new Item(name, price, true, category);
        menuList.add(item);
        System.out.println(item.getName() + " added to the menu.");
    }
    public void updateItem(List<Item> menuList){
        System.out.println("        Menu");
        for (Item item: menuList){
            System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
        }
        System.out.println("1. Update name\n2. Update Price\n3. Update Availability\n0. Exit");
        //if (item.getName().equals())
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        if (choice==1){
            System.out.print("Enter item name to update its name: ");
            String name = sc.next();
            for (Item items: menuList){
                if (items.getName().equals(name)){
                    System.out.print("Enter new name: ");
                    String newName = sc.next();
                    items.setName(newName);
                }
            }
        } else if (choice==2) {
            System.out.print("Enter item name to update its price: ");
            String name = sc.next();
            for (Item items: menuList){
                if (items.getName().equals(name)){
                    System.out.print("Enter new price: ");
                    int newPrice = sc.nextInt();
                    items.setPrice(newPrice);
                }
            }

        } else if (choice==3) {
            System.out.print("Enter item name to update its availability: ");
            String name = sc.next();
            for (Item items: menuList){
                if (items.getName().equalsIgnoreCase(name)){
                    System.out.print("change availability (enter yes/no): ");
                    String availability = sc.next();
                    if (availability.equals("yes")){
                        items.setAvailable(true);
                    } else if (availability.equals("no")) {
                        items.setAvailable(false);
                    }
                    else{
                        System.out.print("Invalid input");
                    }
                }
            }

        }

    }
    public void removeItem(List<Item> menuList){
        System.out.println("Menu");
        for (Item item: menuList){
            System.out.println("Item: "+item.getName()+" Price: "+item.getPrice()+" Available: "+(item.isAvailable() ? "Yes" : "No"));
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item name: ");
        String name = sc.next();
//        for (Item items : menuList) {
//            if (items.getName().equals(name)) {
//                menuList.remove(items);
//            }
//        }
        Iterator<Item> iterator = menuList.iterator();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equals(name)) {
                iterator.remove();
                System.out.println(name+" removed successfully.");
                return;
            }
        }
    }
    public void viewPendingOrders(List<Orders> pendingOrdersFromMain, List<Customer> customersFromMain){

        if (pendingOrdersFromMain.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Orders: ");
        for (Orders order: pendingOrdersFromMain){
            String customername = order.getCustomerName();
            String priority = null;
            for (Customer customer: customersFromMain){
                if (customer.getEmail().equals(customername)){
                     priority= customer.getPriority();
                }
            }
            System.out.println("Customer email: "+customername+", Priority: "+priority);
            String specialRequest = order.getSpecialRequest();
            System.out.println("Special Request: " + ((specialRequest != null && !specialRequest.isEmpty()) ? specialRequest : "None"));
            System.out.println("Items");

            Map<Item, Integer> items = order.getItemQuantity();

            if (items.isEmpty()) {
                System.out.println("  - No items in this order.");
                continue;
            }

            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                System.out.printf("  - %s: %d\n", item.getName(), quantity);
            }
            System.out.println();
        }
    }

    public void completeOrder(List<Orders> pendingOrdersFromMain, List<Orders> completeOrdersFromMain, List<Customer> customersFromMain){
        if (pendingOrdersFromMain.isEmpty()) {
            System.out.println("No pending orders to complete.");
            return;
        }
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter customer name to complete the order: ");
//        String name = sc.next();

        Orders orderProcess = null;

        for (Orders orders: pendingOrdersFromMain){

            for (Customer customer: customersFromMain){

                if (customer.getEmail().equals(orders.getCustomerName()) && customer.getPriority().equalsIgnoreCase("VIP")){
                    //System.out.println("Inside vip");
                    orderProcess=orders;
                    break;
                }
            }

            if (orderProcess!=null){
                break;
            }
        }

        if (orderProcess == null && !pendingOrdersFromMain.isEmpty()) {
            //System.out.println("Inside regular");
            orderProcess = pendingOrdersFromMain.get(0);
        }

        if (orderProcess != null) {
            pendingOrdersFromMain.remove(orderProcess);
            completeOrdersFromMain.add(orderProcess);
            System.out.println("Order for " + orderProcess.getCustomerName() + " has been completed.");
            FileHandling.saveOrderHistory(orderProcess.getCustomerName(), orderProcess);
        }


//        Iterator<Orders> iterator = pendingOrdersFromMain.iterator();
//        while (iterator.hasNext()) {
//            Orders orders = iterator.next();
//
//
//            if (name.equals(orders.getCustomerName())){
//
//                iterator.remove();
//                completeOrdersFromMain.add(orders);
//            }
//        }
    }

//    public void dailySalesReport(List<Orders> completeOrdersFromMain){
//        System.out.println("|             Daily Sales Report            |");
//        System.out.println("|   Item         |   Quantity   |   Price   |");
//        int totalSales = 0;
//        for (Orders orders: completeOrdersFromMain){
//            Map<Item, Integer> items = orders.getItemQuantity();
//
//            if (items.isEmpty()) {
//                System.out.println("No Sales Today");
//                continue;
//            }
//            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
//                Item item = entry.getKey();
//                int quantity = entry.getValue();
//                System.out.printf("|   %-12s |   %-10d |   %-8d |\n", item.getName(), quantity, quantity*(item.getPrice()));
//                totalSales+=quantity*(item.getPrice());
//            }
//            System.out.println();
//        }
//        System.out.printf("|  Total Sales   |              |   %-8d |\n", totalSales);

//            |           Daily Sales Report              |
//            |   Item         |   Quantity   |   Price   |
//            |   samosa       |   2          |   40      |
//            |   springroll   |   1          |   50   |
//
//            |   Item         |   Quantity   |   Price   |
//            |   momos        |   2          |   100     |
//            |   samosa       |   1          |   20      |
//
//            |   Total Sales  |            210          |



//    }

    public void dailySalesReport(List<Orders> completeOrdersFromMain){
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("|             Daily Sales Report             |");
        System.out.println("----------------------------------------------");
        System.out.println("|      Item      |   Quantity   |    Cost    |");
        System.out.println("----------------------------------------------");
        Map<Item, Integer> itemsTogether = new TreeMap<>();

        for (Orders orders : completeOrdersFromMain) {
            Map<Item, Integer> items = orders.getItemQuantity();

            if (items.isEmpty()) {
              System.out.println("No Sales Today");
              continue;
           }

            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
//                itemsTogether.put(item, quantity);
                itemsTogether.put(item, itemsTogether.getOrDefault(item,0)+quantity);
            }
        }

        int totalSales = 0;
        for (Map.Entry<Item, Integer> entry : itemsTogether.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("|   %-12s |   %-10d |   %-8d |\n", item.getName(), quantity, quantity*(item.getPrice()));
            totalSales+=quantity*(item.getPrice());
        }
        System.out.println("----------------------------------------------");
        System.out.printf("|  Total Sales   |              |   %-8d |\n", totalSales);
    }

//    public List<Item> getMenu() {
//        return menu;
//    }

//    public void addPendingOrder(Orders order) {
//        pendingOrders.add(order);
//    }

    @Override
    void showMenu() {
        System.out.print("\nAdmin Interface:\n1. View Menu\n2. Add Items to Menu\n3. Update Item in Menu\n4. Remove Item from Menu\n" +
                "5. View Pending Orders\n6. Process Order\n" +
                "7. Generate Daily Sales Report\n0. Exit\n");  //process order - complete order
    }
}
