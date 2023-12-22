package com.example.decafe;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {
    private final String filenameImageWithoutProduct;
    private final String filenameImageWithCoffee;
    private final String filenameImageWithCake;
    private String productInHand;
    private int movementSpeed;

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

