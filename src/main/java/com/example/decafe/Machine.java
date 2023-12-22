package com.example.decafe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;


public class Machine {
    private static final String COFFEE = "coffee";
    private static final String CAKE = "cake";
    private final String filenameImageMachineWithoutProduct;
    private final String filenameImageMachineWithProduct;
    private final String productType;
    private int duration;
    private Boolean produced;

    public Machine(int duration, String filenameImageMachineWithProduct, String filenameImageMachineWithoutProduct, String productType){
        this.duration = duration;
        this.produced = false;
        this.filenameImageMachineWithProduct = filenameImageMachineWithProduct;
        this.filenameImageMachineWithoutProduct = filenameImageMachineWithoutProduct;
        this.productType = productType;
    }

    public int getDuration() { return duration; }

    public Boolean getProduced() { return produced; }

    public void setDuration(int duration) { this.duration = duration; }

    public void setProduced(Boolean produced){ this.produced = produced; }

    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project

        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    public void doProgressBarAnimation(Timer productionTimer, ImageView machineImageView, ProgressBar machineProgressBar, Image imageProductProduced){

        machineImageView.setDisable(true);

        machineProgressBar.setVisible(true);

        Timeline task = new Timeline(

                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(machineProgressBar.progressProperty(), 0)
                ),
                new KeyFrame(
                        // Set the duration of the progressbar animation
                        Duration.seconds(this.getDuration()),
                        new KeyValue(machineProgressBar.progressProperty(), 1)
                )
        );

        int maxStatus = 12;

        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);

        Timeline timelineBar = new Timeline(
                new KeyFrame(

                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );

        timelineBar.setCycleCount(Animation.INDEFINITE);

        timelineBar.play();

        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {
            int statusNew = statusNewNumber.intValue();

            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);

            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        });
        task.playFromStart();


        productionTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // After a certain time was reached change the Image of the Machine
                        machineImageView.setImage(imageProductProduced);
                        // And make the Machine clickable again
                        machineImageView.setDisable(false);
                        // And stop all timers
                        task.stop();
                        timelineBar.stop();
                        productionTimer.cancel();
                    }
                },
                this.duration* 1000L
        );
    }


    public void displayProduct (ImageView waiterImageView, ImageView machineImageView, Player cofiBrew, ProgressBar machineProgressBar) throws FileNotFoundException {

        Timer productionTimer = new Timer();

        String imageMachine = this.filenameImageMachineWithProduct;
        String imageCofi = cofiBrew.getWaiterImageWithoutProduct();

        boolean gotProduced = false;

        if (Boolean.TRUE.equals(isNothingProducedAndNoProductInHand(cofiBrew))) {

            this.setProduced(true);
            gotProduced = true;

        } else if (Boolean.TRUE.equals(isNothingProducedAndCoffeeInHand(cofiBrew))) {

            this.setProduced(true);
            gotProduced = true;
            imageCofi = cofiBrew.getWaiterImageWithCoffee();

        } else if (Boolean.TRUE.equals(isNothingProducedAndCakeInHand(cofiBrew))) {

            this.setProduced(true);
            gotProduced = true;
            imageCofi = cofiBrew.getWaiterImageWithCake();
        } else {
            if (Boolean.TRUE.equals(isNoProductInHand(cofiBrew))) {

                this.setProduced(false);

                imageMachine = this.filenameImageMachineWithoutProduct;

                cofiBrew.setProductInHand(this.productType);
                if (this.productType.equals(COFFEE)) {

                    imageCofi = cofiBrew.getWaiterImageWithCoffee();
                } else {

                    imageCofi = cofiBrew.getWaiterImageWithCake();
                }
            } else {

                if (cofiBrew.getProductInHand().equals(COFFEE)) {

                    imageCofi = cofiBrew.getWaiterImageWithCoffee();
                } else {
                        imageCofi = cofiBrew.getWaiterImageWithCake();
                    }
            }
        }

        waiterImageView.setImage(createImage(imageCofi));

        if (gotProduced) {
            doProgressBarAnimation(productionTimer, machineImageView, machineProgressBar, createImage(imageMachine));
        } else {
            machineProgressBar.setVisible(this.getProduced());
            machineImageView.setImage(createImage(imageMachine));
        }

    }

    Boolean isNothingProducedAndNoProductInHand(Player cofiBrew) {
        return Boolean.TRUE.equals(!this.produced) && cofiBrew.getProductInHand().equals("none");
    }

    Boolean isNothingProducedAndCoffeeInHand(Player cofiBrew) {
        return Boolean.TRUE.equals(!this.produced) && cofiBrew.getProductInHand().equals(COFFEE);
    }

    Boolean isNothingProducedAndCakeInHand(Player cofiBrew) {
        return Boolean.TRUE.equals(!this.produced) && cofiBrew.getProductInHand().equals(CAKE);
    }

    Boolean isNoProductInHand(Player cofiBrew) {
        return cofiBrew.getProductInHand().equals("none");
    }
}