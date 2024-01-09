package com.example.decafe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerConstructor() {
        Player player = new Player("image1", "image2", "image3", 10);
        assertEquals("image1", player.getWaiterImageWithoutProduct());
        assertEquals("image2", player.getWaiterImageWithCake());
        assertEquals("image3", player.getWaiterImageWithCoffee());
        assertEquals("none", player.getProductInHand());
        assertEquals(10, player.getMovement());
    }

    @Test
    void testSetAndGetProductInHand() {
        Player player = new Player("image1", "image2", "image3", 10);
        player.setProductInHand("coffee");
        assertEquals("coffee", player.getProductInHand());
    }

    @Test
    void testSetAndGetMovement() {
        Player player = new Player("image1", "image2", "image3", 10);
        player.setMovement(15);
        assertEquals(15, player.getMovement());
    }

}

