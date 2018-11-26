package com.customer.tdd.repository.impl;

import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StubCustomerRepository implements IRepository<Customer> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<Customer> customers = new ArrayList<>();
    private Customer customer;

    public Customer findById(Integer id) {
        return customer;
    }

    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public void save(Customer customer) {
        logger.info("Save Success");
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
