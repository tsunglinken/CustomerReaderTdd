package com.customer.tdd.model;

import com.customer.tdd.enums.Type;

import java.time.LocalDate;

public class Customer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public Event createEvent() {
        Event event = new Event();
        event.setType(Type.REMINDER_SENT);
        event.setCustomerName(getFullName());
        event.setTimestamp(LocalDate.now());
        return event;
    }
}
