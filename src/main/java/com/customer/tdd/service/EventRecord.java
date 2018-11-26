package com.customer.tdd.service;

import com.customer.tdd.model.Event;

import java.time.format.DateTimeFormatter;

public class EventRecord {
    public void recordEvent(Event event) {
        System.out.printf(String.format("CurrentTime is %s\nRecordEvent for type is %s\ncustomerName is %s",
                event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:sss")),
                event.getType(),
                event.getCustomerName()
        ));
    }
}
