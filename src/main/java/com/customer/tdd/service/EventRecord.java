package com.customer.tdd.service;

import com.customer.tdd.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventRecord {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public void recordEvent(Event event) {
        logger.info("CurrentTime is {}\nRecordEvent for type is {}\ncustomerName is {}",
                getTimestampFormat(event.getTimestamp()),
                event.getType(),
                event.getCustomerName()
        );
    }

    private String getTimestampFormat(LocalDate timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:sss"));
    }
}
