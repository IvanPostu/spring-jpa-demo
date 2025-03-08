package com.iv.kafkademo1.demo1common.entity;


public class PurchaseRequest2 {

    private String prNumber;
    private int amount;
    private String currency;

    public PurchaseRequest2() {

    }

    public PurchaseRequest2(int id, String prNumber, int amount, String currency) {
        this.prNumber = prNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    @Override
    public String toString() {
        return "PurchaseRequest2 [prNumber=" + prNumber + ", amount=" + amount + ", currency=" + currency
                + "]";
    }

}

