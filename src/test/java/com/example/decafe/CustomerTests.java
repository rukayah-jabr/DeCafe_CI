package com.example.decafe;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CustomerTests {

    private Customer customer;


    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testGetCustomerRandomOrder() {
        Customer customer = new Customer();
        String randomOrder = customer.getCustomerRandomOrder();

        assertNotNull(randomOrder);
        assertTrue(randomOrder.equals("cake") || randomOrder.equals("coffee"));
    }

    @Test
    void testCreateImageFilePath() {
        Customer customer = new Customer();
        assertThrows(FileNotFoundException.class, () -> customer.createImageFilePath("wrongpath.jpg"));
    }

    @Test
    void testStartTimerToSpawnCustomers() {
        Timer mockTimer = mock(Timer.class);
        Customer customer = new Customer();

        customer.startTimerToSpawnCustomers(5, mockTimer); // 5 seconds
        verify(mockTimer).schedule(any(TimerTask.class), eq(5000L));
    }






}
