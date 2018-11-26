package com.customer.tdd.service;

import com.customer.tdd.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public void send(Customer customer) {
        logger.info("Send Email To {}", customer.getEmail());
    }
}
