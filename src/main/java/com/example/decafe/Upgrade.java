package com.example.decafe;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

// Function used to control all the methods used for Upgrades
public class Upgrade {
    private final int CoinsNeeded; // The coins needed to use/do the Upgrade
    private boolean alreadyUsedOnce; // Boolean that indicates if the Upgrade was already used or not
    private final String filenameUpgradeNotUsed; // Image of "deactivated" Upgrade
    private final String filenameUpgradeUsed; // Image of "activated" Upgrade
    private final ImageView upgradeImageView; // ImageView that is related to the Upgrade

    // Constructor
    Upgrade(int coinsNeeded, boolean alreadyUsedOnce, String filenameUpgradeNotUsed, String filenameUpgradeUsed, ImageView upgradeImageView){
        this.CoinsNeeded = coinsNeeded;
        this.alreadyUsedOnce = alreadyUsedOnce;
        this.filenameUpgradeNotUsed = filenameUpgradeNotUsed;
        this.filenameUpgradeUsed = filenameUpgradeUsed;
        this.upgradeImageView = upgradeImageView;
    }

    // Getter
    public boolean isAlreadyUsedOnce() {
        return alreadyUsedOnce;
    }

    public int getCoinsNeeded() {
        return CoinsNeeded;
    }

    public String getFilenameUpgradeUsed() {
        return filenameUpgradeUsed;
    }

    public String getFilenameUpgradeNotUsed() {
        return filenameUpgradeNotUsed;
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
        this.upgradeImageView.setImage(createImage(this.filenameUpgradeUsed));
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
