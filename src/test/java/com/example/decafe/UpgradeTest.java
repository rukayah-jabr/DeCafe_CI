package com.example.decafe;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpgradeTest {
    public ImageView imageView = new ImageView();

    @Test
    public void testUpgradeConstructor() {
        Upgrade upgrade = new Upgrade(20, false,"coffeeUpgrade.png", "coffeeUsed.png",  imageView);
        assertEquals(20,upgrade.getCoinsNeeded());
        assertEquals(false, upgrade.isAlreadyUsedOnce());
        assertEquals("coffeeUpgrade.png", upgrade.getImageUpgradeNotUsed());
        assertEquals("coffeeUsed.png", upgrade.getImageUpgradeUsed());
        assertEquals(imageView, upgrade.getUpgradeImageView());
    }
@Test
    public void testDoUpgrade() throws FileNotFoundException {
        Upgrade upgrade = new Upgrade(40, false,"coffeeUpgrade.png", "coffeeUsed.png",  imageView);
        assertEquals(0, upgrade.doUpgrades(40));
        assertEquals(10, upgrade.doUpgrades(50));
        assertEquals(-10, upgrade.doUpgrades(30));
    }
}