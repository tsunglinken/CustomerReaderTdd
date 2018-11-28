package com.customer.tdd.service;

import com.customer.tdd.enums.Type;
import com.customer.tdd.model.Customer;
import com.customer.tdd.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MassUserRegistration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository = new UserRepository();
    private EventRecord eventRecord = new EventRecord();

    public void register(String firstName, String lastName, String email) {
        Customer customer = userRepository.saveCustomer(firstName, lastName, email);
        eventRecord.recordEvent(customer.createEvent(Type.REGISTRATION));
        logger.info("Register Success !!!");
    }

    public void massRegister(List<Customer> customers) {
        customers.forEach(customer -> register(customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()));
    }
}
