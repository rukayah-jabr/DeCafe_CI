package com.example.decafe;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {
    private final String filenameImageWithoutProduct; // Image of the waiter without anything in his hands
    private final String filenameImageWithCoffee; // Image of the waiter with coffee in his hands
    private final String filenameImageWithCake; // Image of the waiter with cake in his hands
    private String productInHand; // The type of product the waiter holds in his hands (Coffee or Cake)
    private int movementSpeed; // the movement speed at which the waiter moves

    // Constructor
    public Player(String filenameImageWithoutProduct, String filenameImageWithCake, String filenameImageWithCoffee, int movement) {
        this.filenameImageWithoutProduct = filenameImageWithoutProduct;
        this.filenameImageWithCake =  filenameImageWithCake;
        this.filenameImageWithCoffee = filenameImageWithCoffee;
        this.productInHand = "none";
        this.movementSpeed = movement;
    }

    //Getter
    public String getProductInHand() {
        return productInHand;
    }

    public String getFilenameImageWithoutProduct() {
        return filenameImageWithoutProduct;
    }

    public String getFilenameImageWithCake() {
        return filenameImageWithCake;
    }

    public String getFilenameImageWithCoffee() {
        return filenameImageWithCoffee;
    }

    public int getMovement() {
        return movementSpeed;
    }

    //Setter
    public void setProductInHand(String productInHand) {
        this.productInHand = productInHand;
    }

    public void setMovement(int movement) {
        this.movementSpeed = movement;
    }
}

