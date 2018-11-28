package com.customer.tdd.service;

import com.customer.tdd.enums.Type;
import com.customer.tdd.model.Customer;
import com.customer.tdd.model.Event;
import com.customer.tdd.repository.impl.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MassUserRegistrationTest {
    @Mock
    private Logger logger;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRecord eventRecord;
    @InjectMocks
    private MassUserRegistration massUserRegistration;
    private ArgumentCaptor<Event> eventArgumentCaptor;
    private List<Customer> customers;
    private List<Event> events;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_one_customer(){
        givenCustomer(new Customer("Ken", "Chuang", "magic3657@gmail.com"));
        Customer customer = customers.get(0);
        massUserRegistration.register(customer.getFirstName(), customer.getLastName(), customer.getEmail());
        eventRecordCalledTimesShouldBe(customers.size());
        eventShouldBe();
    }

    @Test
    public void register_multiple_customer(){
        givenCustomer(new Customer("Ken", "Chuang", "magic3657@gmail.com"),
                new Customer("Sam", "Chu", "sam.chu@gmail.com"),
                new Customer("Jason", "Lin", "jason.lin@gmail.com"),
                new Customer("Ryan", "Su", "ryan.su@gmail.com"));
        massUserRegistration.massRegister(customers);
        eventRecordCalledTimesShouldBe(customers.size());
        eventShouldBe();
    }

    private void givenCustomer(Customer... customers) {
        doAnswer(invocationOnMock -> new Customer(invocationOnMock.getArgument(0),
                invocationOnMock.getArgument(1),
                invocationOnMock.getArgument(2)))
                .when(userRepository).saveCustomer(notNull(), notNull(), anyString());
        this.customers = Arrays.asList(customers);
    }

    private void eventRecordCalledTimesShouldBe(int times) {
        eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRecord, times(times)).recordEvent(eventArgumentCaptor.capture());
    }

    private void eventShouldBe() {
        events = eventArgumentCaptor.getAllValues();
        IntStream.range(0, events.size())
                .forEach(index -> {
                    Event event = events.get(index);
                    Customer customer = customers.get(index);
                    assertNotNull(event.getTimestamp());
                    assertEquals(customer.getFullName(), event.getCustomerName());
                    assertEquals(Type.REGISTRATION, event.getType());
                });
    }
}
