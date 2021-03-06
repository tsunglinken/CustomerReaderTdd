package com.customer.tdd.service;

import com.customer.tdd.enums.Type;
import com.customer.tdd.model.Customer;
import com.customer.tdd.model.Event;
import com.customer.tdd.repository.impl.StubCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerReaderTest {
    @Mock
    private Logger logger;
    @Mock
    private StubCustomerRepository stubCustomerRepository;
    @Mock
    private EmailSender emailSender;
    @Mock
    private EventRecord eventRecord;
    @InjectMocks
    private CustomerReader customerReader;
    private Customer customer;
    private ArgumentCaptor<Event> eventArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
        eventShouldBe(Type.REMINDER_SENT, "Ken Chuang");
    }

    @Test
    public void save_customer_success(){
        doAnswer(invocation -> {
            Customer customer = (Customer) invocation.getArgument(0);
            customer.setId(2);
            return null;
        }).when(stubCustomerRepository).save(any(Customer.class));
        customerReader.saveCustomer("Ken", "Chuang", "magic3657@gmail.com");
        verify(logger).info("Saved customer with id {}", 2);
    }

    @Test
    public void spy_test(){
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls real methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
        assertEquals(100, spy.size());
    }


    private void eventShouldBe(Type expectedType, String expectedCustomerName) {
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
