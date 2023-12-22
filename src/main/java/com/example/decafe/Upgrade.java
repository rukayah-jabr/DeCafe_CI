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

    public void setAlreadyUsedOnce(boolean alreadyUsedOnce) {
        this.alreadyUsedOnce = alreadyUsedOnce;
    }

    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public int doUpgrades(int coin) throws FileNotFoundException {
        this.upgradeImageView.setImage(createImage(this.imageUpgradeUsed));
        this.upgradeImageView.setDisable(true);
        this.setAlreadyUsedOnce(true);
        coin -= this.getCoinsNeeded();
        return coin;
    }
}
