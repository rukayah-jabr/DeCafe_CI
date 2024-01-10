package com.example.decafe;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HelloControllerTest {
    HelloController helloController = new HelloController();
    ImageView imageView1 = new ImageView();
    ImageView imageView2 = new ImageView();
    ImageView imageView3 = new ImageView();
    ImageView imageViewExtra = new ImageView();

    Customer customer1 = new Customer(imageView1, imageView1, 3, imageView1, imageView1);
    Customer customer2 = new Customer(imageView2, imageView2, 4, imageView2,imageView2);
    Customer customer3 = new Customer(imageView3, imageView3, 5, imageView3, imageView3);

    @Test
    public void testFindCustomer(){
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);

        assertEquals(customer1, helloController.findCustomer(customerList, imageView1));
        assertEquals(customer2, helloController.findCustomer(customerList, imageView2));
        assertEquals(customer3, helloController.findCustomer(customerList, imageView3));
        assertNull(helloController.findCustomer(customerList, imageViewExtra));
    }

}
