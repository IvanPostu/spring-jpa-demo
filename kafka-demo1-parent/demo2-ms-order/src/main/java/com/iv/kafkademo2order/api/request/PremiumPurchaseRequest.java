package com.iv.kafkademo2order.api.request;

public class PremiumPurchaseRequest {

    private String item;

    private String purchaseNumber;

    private String username;

    public String getItem() {
        return item;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
