package com.customer.tdd.repository.impl;

import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class StubCustomerRepository implements IRepository<Customer> {

    private List<Customer> customers = new ArrayList<Customer>();
    private Customer customer;

    public Customer findById(Integer id) {
        return customer;
    }

    public List<Customer> findAll() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
