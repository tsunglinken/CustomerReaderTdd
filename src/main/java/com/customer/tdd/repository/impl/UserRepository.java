package com.customer.tdd.repository.impl;

import com.customer.tdd.model.Customer;

public class UserRepository {

    public Customer saveCustomer(String firstName, String lastName, String email) {
        return new Customer(firstName, lastName, email);
    }
}
