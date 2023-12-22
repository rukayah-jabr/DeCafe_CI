package com.example.decafe;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Upgrade {
    private final int coinsNeeded;
    private boolean alreadyUsedOnce;
    private final String imageUpgradeNotUsed;
    private final String imageUpgradeUsed;
    private final ImageView upgradeImageView;

    Upgrade(int coinsNeeded, boolean alreadyUsedOnce, String imageUpgradeNotUsed, String imageUpgradeUsed, ImageView upgradeImageView){
        this.coinsNeeded = coinsNeeded;
        this.alreadyUsedOnce = alreadyUsedOnce;
        this.imageUpgradeNotUsed = imageUpgradeNotUsed;
        this.imageUpgradeUsed = imageUpgradeUsed;
        this.upgradeImageView = upgradeImageView;
    }

    public boolean isAlreadyUsedOnce() {
        return alreadyUsedOnce;
    }

    public int getCoinsNeeded() {
        return coinsNeeded;
    }

    public String getImageUpgradeUsed() {
        return imageUpgradeUsed;
    }

    public String getImageUpgradeNotUsed() {
        return imageUpgradeNotUsed;
    }

    public ImageView getUpgradeImageView() { return upgradeImageView; }

    // Setter
    public void setAlreadyUsedOnce(boolean alreadyUsedOnce) {
        this.alreadyUsedOnce = alreadyUsedOnce;
    }


    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    // Method used to use an Upgrade
    public int doUpgrades(int coin) throws FileNotFoundException {
        // Change Image to the "deactivated" Upgrade Image
        this.upgradeImageView.setImage(createImage(this.imageUpgradeUsed));
        // Disable the ImageView
        this.upgradeImageView.setDisable(true);
        // Set the Used variable to true
        this.setAlreadyUsedOnce(true);
        // Decrease the coins score according to the upgrade costs
        coin -= this.getCoinsNeeded();
        // return the new coin score
        return coin;
    }
}
