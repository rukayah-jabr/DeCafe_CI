package com.example.decafe;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
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
}
