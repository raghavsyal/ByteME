package com.university.byteme;

import java.util.*;

public class Cart {
    private Map<Item, Integer> itemQuantity;

    public Cart(Map<Item, Integer> items) {
        this.itemQuantity = new TreeMap<>(items);
    }

    public Cart(){
        this.itemQuantity=new TreeMap<>(Comparator.comparing(Item::getName));
    }

    public void addItems(Item item, int quantity){
        itemQuantity.put(item,quantity);
    }

    public void removeItem() {
        System.out.println("My Cart: ");
        viewCart();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter item name to remove from cart: ");
        String name = sc.nextLine();
//        for (Item item: itemQuantity.keySet()){
//            if (item.getName().equalsIgnoreCase(name)){
//                itemQuantity.remove(item);
//            }
//        }

        Item item = null;
        for (Item i : itemQuantity.keySet()){
            if (i.getName().equalsIgnoreCase(name)){
                item = i;
                break;
            }
        }
        removeItem(item);
    }


    public void removeItem(Item item){
//        System.out.println("asdhj");
        if (item!=null){
            itemQuantity.remove(item);
        }
    }

    public void updateQuantity(Item item, int quantity) {
        if (itemQuantity.containsKey(item)) {
            itemQuantity.put(item, quantity);
        }
    }

    public void viewCart(){
        if (itemQuantity.isEmpty()){
            System.out.println("Cart Empty \n");
        }
        else{
            System.out.println("          Your Cart");
            for (Map.Entry<Item, Integer> i: itemQuantity.entrySet()){
                Item item = i.getKey();
                int quantity = i.getValue();

                System.out.printf("%s - quantity: %d, price: %d", item.getName(), quantity, quantity*(item.getPrice()));
                System.out.println();
            }

        }
    }

    public void clearCart(){
        itemQuantity.clear();
        System.out.println("Cart Cleared");
    }
    public Map<Item, Integer> getItemQuantity(){
        return itemQuantity;
    }

}
