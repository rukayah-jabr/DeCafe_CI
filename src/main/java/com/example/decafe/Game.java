package com.example.decafe;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

//Class that is used mainly to control certain assets of the Game like Machines, Upgrades and the Coin Score
public class Game {
    private final Machine coffeeMachine;
    private final Machine cakeMachine;
    private final Upgrade coffeeMachineUpgrade;
    private final Upgrade cakeMachineUpgrade;
    private final Upgrade playerMovmentUpgrade;
    private int coinsEarned;
    private final String filenameImageThreeCoins;
    private final String filenameImageFourCoins;
    private final String filenameImageDollar;
    private Customer customer;

    // Constructor
    Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradePlayer){
        this.coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        this.cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
        this.coffeeMachineUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png",  upgradeCoffee);
        this.cakeMachineUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", upgradeCake);
        this.playerMovmentUpgrade = new Upgrade(40, false, "upgradeSkates.png", "upgradeSkatesUsed.png",  upgradePlayer);
        this.coinsEarned = 0;
        this.filenameImageDollar = "5coins.png";
        this.filenameImageFourCoins = "4coins.png";
        this.filenameImageThreeCoins = "3coins.png";
    }

    public void checkUpgradePossible(Upgrade upgrade) throws FileNotFoundException {
        if (!upgrade.isAlreadyUsedOnce() && this.coinsEarned >= upgrade.getCoinsNeeded()){ // If upgrade was not already used and the Player earned enough coins to buy it
            upgrade.getUpgradeImageView().setDisable(false);
            upgrade.getUpgradeImageView().setImage(customer.createImageFilePath(upgrade.getImageUpgradeNotUsed()));
        } else {
            upgrade.getUpgradeImageView().setDisable(true);
            upgrade.getUpgradeImageView().setImage(customer.createImageFilePath(upgrade.getImageUpgradeUsed()));
        }
    }
    // Method to do a certain upgrade

    public void doUpgrade(String type, Player CofiBrew) throws FileNotFoundException {
        switch (type) { // Switch the type of upgrade you received
            case "coffee" -> { // If the player chose the coffee upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = coffeeMachineUpgrade.doUpgrades(coinsEarned);
                // Increase the speed of the Coffee Machine
                coffeeMachine.setProductionTime(2);
            }
            case "cake" -> { // If the player chose the cake upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = cakeMachineUpgrade.doUpgrades(coinsEarned);
                // Increase the speed of the Cake Machine
                cakeMachine.setProductionTime(2);
            }
            case "player" -> { // If the player chose the player upgrade
                // Set the coin score according to what the upgrade cost + change Image and Disable upgrade
                coinsEarned = playerMovmentUpgrade.doUpgrades(coinsEarned);
                // Increase the movement speed of the Player
                CofiBrew.setMovement(6);
            }
        }
    }


    public void earnCoinsFromCustomerSatisfaction(Customer customer){
        if (customer.isGreenSmiley()){
            this.coinsEarned += 7;
        } else if (customer.isYellowSmiley()){
            this.coinsEarned += 5;
        }else if (customer.isRedSmiley()){
            this.coinsEarned += 3;
        }
    }

    // Getter
    public Machine getCakeMachine() {
        return cakeMachine;
    }

    public Machine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Upgrade getCakeMachineUpgrade() {
        return cakeMachineUpgrade;
    }

    public Upgrade getCoffeeMachineUpgrade() {
        return coffeeMachineUpgrade;
    }

    public Upgrade getPlayerMovmentUpgrade() {
        return playerMovmentUpgrade;
    }

    public String getFilenameImageThreeCoins() {
        return filenameImageThreeCoins;
    }

    public String getFilenameImageFourCoins() {
        return filenameImageFourCoins;
    }

    public String getFilenameImageDollar() {
        return filenameImageDollar;
    }

    public int getCoinsEarned() { return coinsEarned; }
}
