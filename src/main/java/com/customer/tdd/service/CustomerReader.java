package com.customer.tdd.service;

import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.impl.StubCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerReader {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private StubCustomerRepository stubCustomerRepository;
    private EmailSender emailSender;
    private EventRecord eventRecord;

    public String findFullName(int id) {
        Customer customer = stubCustomerRepository.findById(id);
        if(customer == null){
            return null;
        }
        emailSender.send(customer);
        eventRecord.recordEvent(customer.createEvent());
        return customer.getFullName();
    }

    public void saveCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        stubCustomerRepository.save(customer);
        logger.info("Saved customer with id {}", customer.getId());
    }
}
