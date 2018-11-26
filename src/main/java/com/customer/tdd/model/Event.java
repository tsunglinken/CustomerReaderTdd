package com.customer.tdd.model;

import java.time.LocalDate;

public class Event {
    public enum Type {REMINDER_SENT, REGISTRATION, INVOICE_ISSUED,PAYMENT, SETTLEMENT};

    private Type type;
    private String customerName;
    private LocalDate timestamp;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
