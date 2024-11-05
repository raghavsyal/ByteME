package com.university.byteme;

public class Item implements Comparable<Item>{
    private String name;
    private int price;
    private boolean available;
    private String category;

    public Item(String name, int price, boolean available, String category) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.category=category;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int compareTo(Item other) {
        return this.name.compareTo(other.name);
    }
}
