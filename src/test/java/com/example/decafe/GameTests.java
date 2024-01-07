package com.example.decafe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameTests {

    @InjectMocks
    private Game game;

    @Mock
    private Customer customer;


    @Test
    void testEarnCoinsFromCustomerSatisfactionGreenSmiley() {
        when(customer.isGreenSmiley()).thenReturn(true);
        game.earnCoinsFromCustomerSatisfaction(customer);
        assertEquals(7, game.getCoinsEarned());
    }

    @Test
    void testEarnCoinsFromCustomerSatisfactionYellowSmiley() {
        when(customer.isYellowSmiley()).thenReturn(true);
        game.earnCoinsFromCustomerSatisfaction(customer);
        assertEquals(5, game.getCoinsEarned());
    }

    @Test
    void testEarnCoinsFromCustomerSatisfactionRedSmiley() {
        when(customer.isGreenSmiley()).thenReturn(false);
        when(customer.isYellowSmiley()).thenReturn(false);
        when(customer.isRedSmiley()).thenReturn(true);

        game.earnCoinsFromCustomerSatisfaction(customer);
        assertEquals(3, game.getCoinsEarned());
    }

}
