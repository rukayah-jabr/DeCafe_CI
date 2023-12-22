package com.example.decafe;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {
    private final String waiterImageWithoutProduct;
    private final String waiterImageWithCoffee;
    private final String waiterImageWithCake;
    private String productInHand;
    private int movementSpeed;

    // Constructor
    public Player(String waiterImageWithoutProduct, String waiterImageWithCake, String waiterImageWithCoffee, int movement) {
        this.waiterImageWithoutProduct = waiterImageWithoutProduct;
        this.waiterImageWithCake =  waiterImageWithCake;
        this.waiterImageWithCoffee = waiterImageWithCoffee;
        this.productInHand = "none";
        this.movementSpeed = movement;
    }

    //Getter
    public String getProductInHand() {
        return productInHand;
    }

    public String getFilenameImageWithoutProduct() {
        return waiterImageWithoutProduct;
    }

    public String getFilenameImageWithCake() {
        return waiterImageWithCake;
    }

    public String getFilenameImageWithCoffee() {
        return waiterImageWithCoffee;
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

