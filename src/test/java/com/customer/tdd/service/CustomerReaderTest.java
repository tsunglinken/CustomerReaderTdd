package com.customer.tdd.service;

import com.customer.tdd.model.Customer;
import com.customer.tdd.model.Event;
import com.customer.tdd.repository.impl.StubCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerReaderTest {
    @Mock
    private StubCustomerRepository stubCustomerRepository;
    @Mock
    private EmailSender emailSender;
    @Mock
    private EventRecord eventRecord;
    private CustomerReader customerReader;
    private Customer customer;
    private ArgumentCaptor<Event> eventArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerReader = new CustomerReader(stubCustomerRepository, emailSender, eventRecord);
    }

    @Test
    public void full_name_is_ken_chuang(){
        givenCustomerById(1, new Customer(1, "Ken", "Chuang", "ken.chuang@gmail.com"));
        fullNameShouldBe("Ken Chuang");
    }

    @Test
    public void full_name_is_null(){
        givenCustomerById(1, null);
        fullNameShouldBe(null);
    }

    @Test
    public void email_send_be_called(){
        givenCustomerById(1, new Customer(1, "Ken", "Chuang", "ken.chuang@gmail.com"));
        fullNameShouldBe("Ken Chuang");
        emailSenderCalledTimesShouldBe(1);
    }

    @Test
    public void email_send_not_be_called(){
        givenCustomerById(1, null);
        fullNameShouldBe(null);
        emailSenderCalledTimesShouldBe(0);
    }

    @Test
    public void event_record_be_called(){
        givenCustomerById(1, new Customer(1, "Ken", "Chuang", "ken.chuang@gmail.com"));
        fullNameShouldBe("Ken Chuang");
        eventRecordCalledTimesShouldBe(1);
    }

    @Test
    public void event_record_not_be_called(){
        givenCustomerById(1, null);
        fullNameShouldBe(null);
        eventRecordCalledTimesShouldBe(0);
    }

    @Test
    public void event_record_be_called_and_type_is_REMINDER_SENT(){
        givenCustomerById(1, new Customer(1, "Ken", "Chuang", "ken.chuang@gmail.com"));
        fullNameShouldBe("Ken Chuang");
        eventRecordCalledTimesShouldBe(1);
        eventShouldBe(Event.Type.REMINDER_SENT, "Ken Chuang");
    }

    private void eventShouldBe(Event.Type expectedType, String expectedCustomerName) {
        Event event = eventArgumentCaptor.getValue();
        assertNotNull(event.getTimestamp());
        assertEquals(expectedType, event.getType());
        assertEquals(expectedCustomerName, event.getCustomerName());
    }

    private void givenCustomerById(Integer id, Customer customer) {
        this.customer = customer;
        when(stubCustomerRepository.findById(id)).thenReturn(customer);
    }

    private List<Customer> givenCustomers(Customer... customers) {
        return Arrays.asList(customers);
    }

    private void fullNameShouldBe(String expected) {
        assertEquals(expected, customerReader.findFullName(1));
    }

    private void emailSenderCalledTimesShouldBe(int expected) {
        verify(emailSender, times(expected)).send(customer);
    }

    private void eventRecordCalledTimesShouldBe(int expected) {
        eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRecord, times(expected)).recordEvent(eventArgumentCaptor.capture());
    }
}
