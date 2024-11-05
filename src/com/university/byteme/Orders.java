package com.university.byteme;

import java.util.Map;

public class Orders {
    private long orderNo;
    private String customerName;
    private long billamount;
    private Map<Item, Integer> itemQuantity;
    private String specialRequest;


//    public Orders(long orderNo, String customerName, Map<Item, Integer> itemQuantity) {
//        this.orderNo = orderNo;
//        this.customerName = customerName;
//        //this.billamount = billamount;
//        this.itemQuantity = itemQuantity;
//    }

    public Orders(String customerName, Map<Item, Integer> itemQuantity, String specialRequest) {
        //this.orderNo = orderNo;
        this.customerName = customerName;
        //this.billamount = billamount;
        this.itemQuantity = itemQuantity;
        this.specialRequest=specialRequest;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getBillamount() {
        return billamount;
    }

    public Map<Item, Integer> getItemQuantity() {
        return itemQuantity;
    }
}
