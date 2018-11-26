package com.customer.tdd.service;

import com.customer.tdd.model.Customer;

public class EmailSender {

    public void send(Customer customer) {
        System.out.printf(String.format("Send Email To %s", customer.getEmail()));
    }
}
