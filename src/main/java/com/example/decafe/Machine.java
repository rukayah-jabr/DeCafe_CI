package com.example.decafe;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// Class used to control all the methods needed to operate a Machine
public class Machine {
    private int duration; // The duration that is needed to produce a product - How long does it take to produce something?
    private Boolean produced; // Boolean that says if a product was already produced of if it needs to be produced
    private final String filenameImageMachineWithoutProduct; // Image of the Machine in the default state
    private final String filenameImageMachineWithProduct; // Image of the Machine with a product already produced
    private final String productType; // The type of the Machine (cake or coffee)

    // Constructor
    public Machine(int duration, String filenameImageMachineWithProduct, String filenameImageMachineWithoutProduct, String productType){
        this.duration = duration;
        this.produced = false;
        this.filenameImageMachineWithProduct = filenameImageMachineWithProduct;
        this.filenameImageMachineWithoutProduct = filenameImageMachineWithoutProduct;
        this.productType = productType;
    }
    //Getter
    public int getDuration() { return duration; }

    public Boolean getProduced() { return produced; }

    //Setter
    public void setDuration(int duration) { this.duration = duration; }

    public void setProduced(Boolean produced){ this.produced = produced; }

    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    // Method used to animate the progressbar above the Machine
    public void doProgressBarAnimation(Timer productionTimer, ImageView machineImageView, ProgressBar machineProgressBar, Image imageProductProduced){
        // During the Animation the Player should not be able to click the machine therefore disable it
        machineImageView.setDisable(true);
        // The Progressbar gets shown
        machineProgressBar.setVisible(true);
        // For the animation is used a code from Stackoverflow and modified it a bit
        // code from https://stackoverflow.com/questions/18539642/progressbar-animated-javafx
        // create a new Timeline task
        Timeline task = new Timeline(
                // With two KeyFrame Animations
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
        // Set the maxStatus
        int maxStatus = 12;
        // Create the Property that holds the current status count
        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        // Create the timeline that loops the statusCount till the maxStatus
        Timeline timelineBar = new Timeline(
                new KeyFrame(
                        // Set this value for the speed of the animation
                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );
        // The animation should be infinite
        timelineBar.setCycleCount(Timeline.INDEFINITE);
        // play the timeline
        timelineBar.play();
        // Add a listener to the status property
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {
            int statusNew = statusNewNumber.intValue();
            // Remove old status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);
            // Add current status pseudo from progress-bar
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        });
        task.playFromStart();

        // Schedule how long the animation should go
        // Idea from https://stackoverflow.com/questions/2258066/run-a-java-function-after-a-specific-number-of-seconds
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

    // Method to display a Product / change the state of a Machine
    public void displayProduct (ImageView waiterImageView, ImageView machineImageView, Player cofiBrew, ProgressBar machineProgressBar) throws FileNotFoundException {
        // create new Timer object
        Timer productionTimer = new Timer();
        // Set default image of Waiter and Machine
        String imageMachine = this.filenameImageMachineWithProduct;
        String imageCofi = cofiBrew.getFilenameImageWithoutProduct();
        // Set boolean got produced to false - used to check if a product was produced when clicked on Machine
        boolean gotProduced = false;

        if (!this.produced && cofiBrew.getProductInHand().equals("none")) { // If Machine hasn't produced something and the waiter has nothing in his hands
            // Set both booleans to produced
            this.setProduced(true);
            gotProduced = true;
        } else if (!this.produced && cofiBrew.getProductInHand().equals("coffee")) { // If machine hasn't produced anything and the waiter has coffee in his hands
            // Set both booleans to produced
            this.setProduced(true);
            gotProduced = true;
            // Change Image to waiter with Coffee (since it's not the same as the default)
            imageCofi = cofiBrew.getFilenameImageWithCoffee();
        } else if (!this.produced && cofiBrew.getProductInHand().equals("cake")) { // If machine hasn't produced anything and the waiter has cake in his hands
            // Set both booleans to produced
            this.setProduced(true);
            gotProduced = true;
            // Change Image to waiter with Cake (since it's not the same as the default)
            imageCofi = cofiBrew.getFilenameImageWithCake();
        } else { // If something was already produced
            if (cofiBrew.getProductInHand().equals("none")){ // And the waiter hasn't anything in his hands
                // Set produced boolean to false since nothing was produces
                this.setProduced(false);
                // Change Image to Machine without product (since product was taken)
                imageMachine = this.filenameImageMachineWithoutProduct;
                // Set the product the player obtain to whatever type the machine is
                cofiBrew.setProductInHand(this.productType);
                if (this.productType.equals("coffee")){ // If the type of the machine is coffee
                    // Change the images of the waiter, so he holds coffee
                    imageCofi = cofiBrew.getFilenameImageWithCoffee();
                } else { // If the type of the machine is cake
                    // Change the images of the waiter, so he holds cake
                    imageCofi = cofiBrew.getFilenameImageWithCake();
                }
            } else { // If coffee Brew has something in his hands (so he can't pick up the product produced)
                    // Keep the picture the same
                    if (cofiBrew.getProductInHand().equals("coffee")){ // So if he holds coffee at the moment
                        // Set Image to waiter with coffee
                        imageCofi = cofiBrew.getFilenameImageWithCoffee();
                    } else { // If he holds cake at the moment
                        // Set Image to waiter with cake
                        imageCofi = cofiBrew.getFilenameImageWithCake();
                    }
            }
        }
        // Set the waiter Image to whatever images was chosen above
        waiterImageView.setImage(createImage(imageCofi));

        if (gotProduced) { // If something was produced
            // Animate the progress bar and wait till the product gets produced
            doProgressBarAnimation(productionTimer, machineImageView, machineProgressBar, createImage(imageMachine));
        } else { // If nothing was produced
            // Make the progress bar visible if something was produced and if not so set it invisible
            machineProgressBar.setVisible(this.getProduced());
            // Set the machine Image to whatever images was chosen above
            machineImageView.setImage(createImage(imageMachine));
        }
    }

}
