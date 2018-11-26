package com.customer.tdd.service;

import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.impl.StubCustomerRepository;
import org.slf4j.Logger;

public class CustomerReader {
    private Logger logger;
    private StubCustomerRepository stubCustomerRepository;
    private EmailSender emailSender;
    private EventRecord eventRecord;

    public CustomerReader(StubCustomerRepository stubCustomerRepository, EmailSender emailSender, EventRecord eventRecord, Logger logger) {
        this.stubCustomerRepository = stubCustomerRepository;
        this.emailSender = emailSender;
        this.eventRecord = eventRecord;
        this.logger = logger;
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

    public void saveCustomer(String fisrtName, String lastName, String email) {
        Customer customer = new Customer(fisrtName, lastName, email);
        stubCustomerRepository.save(customer);
        logger.info("Saved customer with id {}", customer.getId());
    }
}
