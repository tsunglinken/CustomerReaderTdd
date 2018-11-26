package com.customer.tdd.service;

import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.impl.StubCustomerRepository;

import java.util.List;

public class CustomerReader {
    private StubCustomerRepository stubCustomerRepository;
    private EmailSender emailSender;
    private EventRecord eventRecord;
    private List<Customer> customers;

    public CustomerReader(StubCustomerRepository stubCustomerRepository, EmailSender emailSender, EventRecord eventRecord) {
        this.stubCustomerRepository = stubCustomerRepository;
        this.emailSender = emailSender;
        this.eventRecord = eventRecord;
    }

    public String findFullName(int id) {
        Customer customer = stubCustomerRepository.findById(id);
        if(customer == null){
            return null;
        }
        emailSender.send(customer);
        eventRecord.recordEvent(customer.createEvent());
        return customer.getFullName();
    }

    private boolean isNotFound(List<Customer> customers) {
        return customers.isEmpty();
    }
}
