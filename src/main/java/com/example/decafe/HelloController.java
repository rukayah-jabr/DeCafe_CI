package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.URL;
import java.util.*;

// Class that is responsible for every action taken in JavaFX GUI (connected via fxml files)
public class HelloController implements Initializable {

    //defining constants to prevent duplicating
    private static final String RESOURCES = "resources";
    private static final String EXAMPLE = "example";
    private static final String DECAFE = "decafe";
    private static final String COFFEE = "coffee";

    // Assets of the Start Screen
    @FXML
    private ImageView startButton;
    @FXML
    private ImageView startQuitButton;

    //Assets of the Game Screen
    // Image of the waiter
    public ImageView waiterImageView;

    // Label that shows the current amount of coins earned
    @FXML
    private Label coinsEarnedLabel;
    // Used for controlling the movement of the Player
    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    // Images of the Object the Player can interact with
    @FXML
    private ImageView coffeeMachineImageView;
    @FXML
    private ImageView cakeMachineImageView;

    // Progress bars used to show Production Progress
    @FXML
    private ProgressBar progressBarCoffee;
    @FXML
    private ProgressBar progressBarCake;

    // Images used to control Upgrades
    @FXML
    private ImageView upgradeCoffeeImageView;
    @FXML
    private ImageView upgradeCakeImageView;
    @FXML
    private ImageView upgradePlayerImageView;

    // Labels used for collision detection management
    @FXML
    private Label table1;
    @FXML
    private Label table2;
    @FXML
    private Label table3;
    @FXML
    private Label table4;
    @FXML
    private Label plantsAbove;
    @FXML
    private Label countRight;
    @FXML
    private Label countBelow;
    @FXML
    private Label customerTop1;
    @FXML
    private Label customerTop2;
    @FXML
    private Label customerTop3;
    @FXML
    private Label customerTop4;
    @FXML
    private Label customerBot1;
    @FXML
    private Label customerBot2;
    @FXML
    private Label customerBot3;
    @FXML
    private Label plant;
    @FXML
    private Label edgeBot;
    @FXML
    private Label edgeTop;
    @FXML
    private Label edgeLeft;
    @FXML
    private Label edgeRight;

    //for the customers
    //smiley images
    @FXML
    private ImageView smileyFirst;
    @FXML
    private ImageView smileySecond;
    @FXML
    private ImageView smileyThird;
    @FXML
    private ImageView smileyFourth;
    @FXML
    private ImageView smileyFifth;
    @FXML
    private ImageView smileySixth;
    @FXML
    private ImageView smileySeventh;

    //coin images
    @FXML
    private ImageView coinFirst;
    @FXML
    private ImageView coinSecond;
    @FXML
    private ImageView coinThird;
    @FXML
    private ImageView coinFourth;
    @FXML
    private ImageView coinFifth;
    @FXML
    private ImageView coinSixth;
    @FXML
    private ImageView coinSeventh;

    //order labels
    @FXML
    private ImageView orderlabel1 = new ImageView();
    @FXML
    private ImageView orderlabel2 = new ImageView();
    @FXML
    private ImageView orderlabel3 = new ImageView();
    @FXML
    private ImageView orderlabel4 = new ImageView();
    @FXML
    private ImageView orderlabel5 = new ImageView();
    @FXML
    private ImageView orderlabel6 = new ImageView();
    @FXML
    private ImageView orderlabel7 = new ImageView();

    //customer images
    @FXML
    private ImageView first;
    @FXML
    private ImageView second;
    @FXML
    private ImageView third;
    @FXML
    private ImageView fourth;
    @FXML
    private ImageView fifth;
    @FXML
    private ImageView sixth;
    @FXML
    private ImageView seventh;

    // for end screen
    @FXML
    private ImageView gameStartButton;
    private ImageView playAgainImage;
    private ImageView backToStartImage;
    @FXML
    private ImageView quitEndScreenImage;

    // Player object created to change Images and movement Speed
    private Player cofiBrew = new Player("CofiBrewUp.png", "CofiBrewCakeLeft.png", "CofiBrewCoffeeLeft.png", 4);
    // Game object used to control main methods
    private Game play;
    // Label array used for collision detection management
    private Label[] collisions;
    // Timer used to spawn customers or make them leave
    private Timer controllerTimer = new Timer();

    private File fileToGetBackgroundMusic = new File("");
    private String musicFileToGetBackgroundMusic = fileToGetBackgroundMusic.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "backgroundmusic.mp3";
    private AudioClip backgroundMusic = new AudioClip(new File(musicFileToGetBackgroundMusic).toURI().toString());

    // Method used to load a certain scene according to the name of the fxml file
    public void loadScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    // jump to end screen
    public void switchToEndScreen() throws IOException {
        backgroundMusic.stop();
        loadScene("endScreen.fxml");
    }

    // jump to start screen
    public void switchToStartScreen() throws IOException { // if button BACK TO START MENU is pressed
        loadScene("startScreen.fxml");
    }

    // jump from start screen to game screen
    public void switchToGameScreen() throws IOException { // if button PLAY AGAIN is pressed
        loadScene("gameScreen.fxml");
        if (Customer.customerImages[0] != null) {
            Customer customer = new Customer();
            customer.startTimerToSpawnCustomers(1, Customer.getSpawingTimer());
            customer.startTimerToSpawnCustomers(5, Customer.getSpawingTimer());
            customer.startTimerToSpawnCustomers(10, Customer.getSpawingTimer());
            Customer.allCustomersCreated.add(customer);
        }
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();
    }

    // jump to instructions
    public void switchToInstructions() throws IOException {
        loadScene("Instructions.fxml");
    }

    // key events if wasd-keys are pressed
    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(true);
            case A -> aPressed.set(true);
            case S -> sPressed.set(true);
            case D -> dPressed.set(true);
        }
    }

    // key events if wasd-keys are released
    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(false);
            case A -> aPressed.set(false);
            case S -> sPressed.set(false);
            case D -> dPressed.set(false);
        }
    }

    // for smoother motion
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            int movementVariable = cofiBrew.getMovement();
            double move = movementVariable; // store movementVariable in new variable
            String movement = "none";

            // if two keys are pressed at once and player moves diagonally - correct diagonal speed
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            // control waiter via wasd keys ([0|0] top-left, [100|100] bottom-right)

            double xMove = 0; // move on x-axis
            double yMove = 0; // move on y-axis

            // if waiter should move up
            if (wPressed.get()) {
                yMove = -move; // negative move because otherwise waiter would move down
                movement = "up";
            }

            // if waiter should move down
            if (sPressed.get()) {
                yMove = move;
                movement = "down";
            }

            // if waiter should move left
            if (aPressed.get()) {
                xMove = -move; // negative move because otherwise waiter would move right
               movement = "left";
            }

            // if waiter should move right
            if (dPressed.get()) {
                xMove = move;
                movement = "right";
            }

            // set x and y coordinates of waiter
            waiterImageView.setLayoutX(waiterImageView.getLayoutX() + xMove);
            waiterImageView.setLayoutY(waiterImageView.getLayoutY() + yMove);

            handleCollision(xMove, yMove, movement);
        }

        private void handleCollision(double xMove, double yMove, String movement) {
            // if collision is detected, set x and y coordinates back to where no collision occurred
            if (checkForCollision(waiterImageView)) {
                waiterImageView.setLayoutX(waiterImageView.getLayoutX() - xMove);
                waiterImageView.setLayoutY(waiterImageView.getLayoutY() - yMove);
            } else {
                if (movement.equals("up")){
                    try {
                        if (cofiBrew.getProductInHand().equals("none")) {
                            waiterImageView.setImage(createImage("CofiBrewUp.png"));
                        } else if (cofiBrew.getProductInHand().equals("cake")){
                            waiterImageView.setImage(createImage("CofiBrewCakeUp.png"));
                        } else if (cofiBrew.getProductInHand().equals(COFFEE)){
                            waiterImageView.setImage(createImage("CofiBrewCoffeeUp.png"));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (movement.equals("down")){
                    try {
                        if (cofiBrew.getProductInHand().equals("none")) {
                            waiterImageView.setImage(createImage("CofiBrewDown.png"));
                        } else if (cofiBrew.getProductInHand().equals("cake")){
                            waiterImageView.setImage(createImage("CofiBrewCakeDown.png"));
                        } else if (cofiBrew.getProductInHand().equals(COFFEE)){
                            waiterImageView.setImage(createImage("CofiBrewCoffeeDown.png"));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (movement.equals("left")){
                    try {
                        if (cofiBrew.getProductInHand().equals("none")) {
                            waiterImageView.setImage(createImage("CofiBrewLeft.png"));
                        } else if (cofiBrew.getProductInHand().equals("cake")){
                            waiterImageView.setImage(createImage("CofiBrewCakeLeft.png"));
                        } else if (cofiBrew.getProductInHand().equals(COFFEE)){
                            waiterImageView.setImage(createImage("CofiBrewCoffeeLeft.png"));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (movement.equals("right")){
                    try {
                        if (cofiBrew.getProductInHand().equals("none")) {
                            waiterImageView.setImage(createImage("CofiBrewRight.png"));
                        } else if (cofiBrew.getProductInHand().equals("cake")){
                            waiterImageView.setImage(createImage("CofiBrewCakeRight.png"));
                        } else if (cofiBrew.getProductInHand().equals(COFFEE)){
                            waiterImageView.setImage(createImage("CofiBrewCoffeeRight.png"));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyPressed.addListener(((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        }));

        // transparent labels on top of the images to look for collisions
        collisions = new Label[]{plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};

        // initialise ImagesViews and Labels so Customer Class can operate with them
        Customer.customerImages = new ImageView[]{first, second, third, fourth, fifth, sixth, seventh}; //make customer ImageView[]
        Customer.smileyImages = new ImageView[]{smileyFirst, smileySecond, smileyThird, smileyFourth, smileyFifth, smileySixth, smileySeventh}; //make smiley ImageView[]
        Customer.orderLabels = new ImageView[]{orderlabel1, orderlabel2, orderlabel3, orderlabel4, orderlabel5, orderlabel6, orderlabel7}; //make label label[]
        Customer.coinImages = new ImageView[]{coinFirst, coinSecond, coinThird, coinFourth, coinFifth, coinSixth, coinSeventh}; //make coin ImageView[]
        Customer.freeChairs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)); //make freeChairs Array
        Customer.setSpawingTimer(controllerTimer); //set the static timer t
        play = new Game(upgradeCoffeeImageView, upgradeCakeImageView, upgradePlayerImageView); // initialise Game Object with upgrade ImageViews

    }


    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        File fileToGetProject = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = fileToGetProject.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    // start screen - change start button on mouse entered
    public void changeStartCoffeeImage() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffeeHot.png"));
    }

    // start screen - change coffee button on mouse exited
    public void changeStartCoffeeImageBack() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffee.png"));
    }
    // start screen - change Quit Button on mouse entered
    public void changeQuitStartScreen() throws FileNotFoundException {
        startQuitButton.setImage(createImage("quitEndScreenBrighter.png"));
    }

    // start screen - change Quit Button when mouse exited
    public void changeQuitStartScreenBack() throws FileNotFoundException {
        startQuitButton.setImage(createImage("quitEndScreen.png"));
    }

    // instructions - change GOT IT! on mouse entered
    public void changeStartImage() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotIt.png"));
    }

    // instructions - change GOT IT! on mouse exited
    public void changeStartImageBack() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotItBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse entered
    public void changePlayAgain() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgainBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse exited
    public void changePlayAgainBack() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgain.png"));
    }

    // end screen - change BackToStartMenu Button when mouse entered
    public void changeBackToStartMenu() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenuBrighter.png"));
    }

    // end screen - change BackToStartMenu Button when mouse exited
    public void changeBackToStartMenuBack() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenu.png"));
    }

    // end screen - change Quit Button when mouse entered
    public void changeQuitEndScreen() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreenBrighter.png"));
    }

    // end screen - change Quit Button when mouse exited
    public void changeQuitEndScreenBack() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreen.png"));
    }

    // if waiter is near coffee machine, change appearance when clicked
    public void showCoffee() throws FileNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(coffeeMachineImageView.getBoundsInParent())) {
            play.getCoffeeMachine().displayProduct(waiterImageView, coffeeMachineImageView, cofiBrew, progressBarCoffee);
            File fileToGetCoffeeSound = new File("");
            String musicFileToGetCoffeeSound = fileToGetCoffeeSound.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "test_sound.wav";
            AudioClip coffeeSound = new AudioClip(new File(musicFileToGetCoffeeSound).toURI().toString());
            coffeeSound.play();
        }
    }

    // if waiter is near cake machine, change appearance when clicked
    public void showCake() throws FileNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(cakeMachineImageView.getBoundsInParent())) {
            play.getCakeMachine().displayProduct(waiterImageView, cakeMachineImageView, cofiBrew, progressBarCake);
            File fileToGetCakeSound = new File("");
            String musicFileToGetCakeSound = fileToGetCakeSound.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "test_sound.wav";
            AudioClip cakeSound = new AudioClip(new File(musicFileToGetCakeSound).toURI().toString());
            cakeSound.play();
        }
    }

    // if no product is held by waiter
    public void noProduct() throws FileNotFoundException {
        if (cofiBrew.getProductInHand().equals(COFFEE) || cofiBrew.getProductInHand().equals("cake")) {
            File fileToGetTrashSound = new File("");
            String musicFileToGetTrashSound = fileToGetTrashSound.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "trashSound.mp3";
            AudioClip trashSound = new AudioClip(new File(musicFileToGetTrashSound).toURI().toString());
            trashSound.play();
            waiterImageView.setImage(createImage(cofiBrew.getWaiterImageWithoutProduct()));
            cofiBrew.setProductInHand("none");
        }
    }

    // find the customer in the customerList and return it
    public Customer findCustomer(List<Customer> customerList, ImageView customerImageView) {
        for (Customer customer : customerList) {
            if (customer.getCustomerImage().equals(customerImageView)) {
                return customer;
            }
        }
        return null; // if not found return null
    }

    // Method used to display a customer, check if and order was right and set Images for coin ImagesViews
    public void displayPerson(MouseEvent event) throws IOException {
        ImageView customerImageView = (ImageView) event.getSource(); //get the Customer of the clicked Image
        Customer customer = findCustomer(Customer.customersInCoffeeShop, customerImageView); //make new customer object

        if (!customer.isCustomerOrdered()) { //if customer has not ordered yet, display an order
            customer.displayOrder(customer.getCustomerOrderLabel());
        } else {
            if (customerImageView.getBoundsInParent().intersects(waiterImageView.getBoundsInParent())) { // If customer has already ordered and waiter is near the customer
                try {
                    customer.startTimerToSpawnCustomers(5, Customer.getSpawingTimer()); // spawn a new customer if a chair is free
                } catch (NullPointerException e) {
                    switchToEndScreen();
                }
                if (customer.checkOrder(cofiBrew, customer, waiterImageView)) { // check if order the waiter has in his hands is the one the customer ordered
                    checkCustomerHappiness(customer);
                }
            }
        }
    }

    private void checkCustomerHappiness(Customer customer) throws FileNotFoundException {
        String moneyImage = ""; // if so set the relating coin ImageView
        if (customer.isGreenSmiley()) { // if customer left happy
            moneyImage = play.getFilenameImageDollar();
        } else if (customer.isYellowSmiley()) { // if customer left normal
            moneyImage = play.getFilenameImageFourCoins();
        } else if (customer.isRedSmiley()) { // if customer left sad
            moneyImage = play.getFilenameImageThreeCoins();
        }
        customer.getCustomerPaymentPicture().setImage(createImage(moneyImage)); //set coin image
        customer.getCustomerPaymentPicture().setOnMouseClicked(event1 -> { // set click event for coin image
            try {
                getMoney(event1, customer); // if coin Image is clicked jump to this method
            } catch (IOException e) {
                try {
                    switchToEndScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Method to check if an Upgrade can be made (check if player has earned enough coins and if it was already used or not)
    public void checkUpgradePossible(Upgrade upgrade) throws FileNotFoundException {
        play.checkUpgradePossible(upgrade);
    }

    // Method to actively use an Upgrade
    public void doUpgrade(MouseEvent e) throws FileNotFoundException {
        // activate the Upgrade (according to whatever ImageView was chosen)
        play.doUpgrade(((ImageView) e.getSource()).getId(), cofiBrew);
        // set the coin label to the correct amount of coins (coins earned - upgrade costs)
        coinsEarnedLabel.setText(String.valueOf(play.getCoinsEarned()));

        File fileToGetUpgradeSound = new File("");
        String musicFileToGetUpgradeSound = fileToGetUpgradeSound.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "upgradeSound.wav";
        AudioClip getUpgrade = new AudioClip(new File(musicFileToGetUpgradeSound).toURI().toString());
        getUpgrade.play();

        // check if other upgrades are still possible or if they need to be "deactivated"
        checkUpgradePossible(play.getCoffeeMachineUpgrade());
        checkUpgradePossible(play.getCakeMachineUpgrade());
        checkUpgradePossible(play.getPlayerMovmentUpgrade());

    }

    // check if collisions occur
    public boolean checkForCollision(ImageView waiter) {
        for (Label collision : collisions) { // iterate through labels
            if (waiter.getBoundsInParent().intersects(collision.getBoundsInParent())) { // if waiter would collide with a label
                return true; // return true
            }
        }
        return false;
    }

    // Method used when coin Image is clicked on
    public void getMoney(MouseEvent e, Customer customer) throws IOException {
        File fileToGetCollectMoneySound = new File("");
        String musicFileToGetCollectMoneySound = fileToGetCollectMoneySound.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + RESOURCES + File.separator + "com" + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "coinsSound.wav";
        AudioClip collectMoney = new AudioClip(new File(musicFileToGetCollectMoneySound).toURI().toString());
        collectMoney.play();
        Customer.addFreeSeat(customer.getChairsOccupiedByCustomers()); // add the seat chosen from the customer to the freeSeatsArray again
        play.earnCoinsFromCustomerSatisfaction(customer); // set the money earned according to what amount of money the customer left
        ((ImageView) e.getSource()).setVisible(false); // disable the coin Image and make it invisible
        ((ImageView) e.getSource()).setDisable(true);

        if (play.getCoinsEarned() < 80) { // check if enough coins were earned to end the game
            checkUpgradePossible(play.getCoffeeMachineUpgrade()); // if not, check if any upgrades would be possible
            checkUpgradePossible(play.getCakeMachineUpgrade());
            checkUpgradePossible(play.getPlayerMovmentUpgrade());
            coinsEarnedLabel.setText(String.valueOf(play.getCoinsEarned())); // refresh the coin score shown in GUI

            try {
                customer.startTimerToSpawnCustomers(5, Customer.getSpawingTimer()); // spawn a new customer
            } catch (NullPointerException y) {
                switchToEndScreen();
            }
        } else { // if enough coins were earned
            stopTimers(); // stop all the timers
            switchToEndScreen(); // switch to the end screen
        }
    }

    // Method used to stop all the timers activated by spawning customers
    public void stopTimers() {
        for (Customer customer : Customer.allCustomersCreated) { // cancel all 60 seconds timers
            if (customer.getSixtySecondsWaitingTimer() != null) {
                customer.getSixtySecondsWaitingTimer().cancel();
            }
        }
        Customer.allCustomersCreated.clear(); // clear all Lists created by spawning/despawning customer
        Customer.customersInCoffeeShop.clear();
        Customer.freeChairs.clear();
        cofiBrew.setProductInHand("none"); // clear Cofis hand
        controllerTimer.cancel(); // cancel spawn/leaving timer
        Customer.getSpawingTimer().cancel();
    }

    // end game (called when exit clicked) - in Game Screen
    public void endGameQuick() {
        stopTimers();
        backgroundMusic.stop();
        Platform.exit();
        System.exit(0);
    }

    // end game (called when quit button is clicked) - in End Screen and Start Screen
    public void endGame() {
        Platform.exit();
        backgroundMusic.stop();
        System.exit(0);
    }

    public ImageView getStartButton() {
        return startButton;
    }

    public void setStartButton(ImageView startButton) {
        this.startButton = startButton;
    }

    public ImageView getStartQuitButton() {
        return startQuitButton;
    }

    public void setStartQuitButton(ImageView startQuitButton) {
        this.startQuitButton = startQuitButton;
    }

    public Label getCoinsEarnedLabel() {
        return coinsEarnedLabel;
    }

    public void setCoinsEarnedLabel(Label coinsEarnedLabel) {
        this.coinsEarnedLabel = coinsEarnedLabel;
    }

    public boolean iswPressed() {
        return wPressed.get();
    }

    public BooleanProperty wPressedProperty() {
        return wPressed;
    }

    public void setwPressed(boolean wPressed) {
        this.wPressed.set(wPressed);
    }

    public boolean isaPressed() {
        return aPressed.get();
    }

    public BooleanProperty aPressedProperty() {
        return aPressed;
    }

    public void setaPressed(boolean aPressed) {
        this.aPressed.set(aPressed);
    }

    public boolean issPressed() {
        return sPressed.get();
    }

    public BooleanProperty sPressedProperty() {
        return sPressed;
    }

    public void setsPressed(boolean sPressed) {
        this.sPressed.set(sPressed);
    }

    public boolean isdPressed() {
        return dPressed.get();
    }

    public BooleanProperty dPressedProperty() {
        return dPressed;
    }

    public void setdPressed(boolean dPressed) {
        this.dPressed.set(dPressed);
    }

    public Boolean getKeyPressed() {
        return keyPressed.get();
    }

    public BooleanBinding keyPressedProperty() {
        return keyPressed;
    }

    public ImageView getCoffeeMachineImageView() {
        return coffeeMachineImageView;
    }

    public void setCoffeeMachineImageView(ImageView coffeeMachineImageView) {
        this.coffeeMachineImageView = coffeeMachineImageView;
    }

    public ImageView getCakeMachineImageView() {
        return cakeMachineImageView;
    }

    public void setCakeMachineImageView(ImageView cakeMachineImageView) {
        this.cakeMachineImageView = cakeMachineImageView;
    }

    public ProgressBar getProgressBarCoffee() {
        return progressBarCoffee;
    }

    public void setProgressBarCoffee(ProgressBar progressBarCoffee) {
        this.progressBarCoffee = progressBarCoffee;
    }

    public ProgressBar getProgressBarCake() {
        return progressBarCake;
    }

    public void setProgressBarCake(ProgressBar progressBarCake) {
        this.progressBarCake = progressBarCake;
    }

    public ImageView getUpgradeCoffeeImageView() {
        return upgradeCoffeeImageView;
    }

    public void setUpgradeCoffeeImageView(ImageView upgradeCoffeeImageView) {
        this.upgradeCoffeeImageView = upgradeCoffeeImageView;
    }

    public ImageView getUpgradeCakeImageView() {
        return upgradeCakeImageView;
    }

    public void setUpgradeCakeImageView(ImageView upgradeCakeImageView) {
        this.upgradeCakeImageView = upgradeCakeImageView;
    }

    public ImageView getUpgradePlayerImageView() {
        return upgradePlayerImageView;
    }

    public void setUpgradePlayerImageView(ImageView upgradePlayerImageView) {
        this.upgradePlayerImageView = upgradePlayerImageView;
    }

    public Label getTable1() {
        return table1;
    }

    public void setTable1(Label table1) {
        this.table1 = table1;
    }

    public Label getTable2() {
        return table2;
    }

    public void setTable2(Label table2) {
        this.table2 = table2;
    }

    public Label getTable3() {
        return table3;
    }

    public void setTable3(Label table3) {
        this.table3 = table3;
    }

    public Label getTable4() {
        return table4;
    }

    public void setTable4(Label table4) {
        this.table4 = table4;
    }

    public Label getPlantsAbove() {
        return plantsAbove;
    }

    public void setPlantsAbove(Label plantsAbove) {
        this.plantsAbove = plantsAbove;
    }

    public Label getCountRight() {
        return countRight;
    }

    public void setCountRight(Label countRight) {
        this.countRight = countRight;
    }

    public Label getCountBelow() {
        return countBelow;
    }

    public void setCountBelow(Label countBelow) {
        this.countBelow = countBelow;
    }

    public Label getCustomerTop1() {
        return customerTop1;
    }

    public void setCustomerTop1(Label customerTop1) {
        this.customerTop1 = customerTop1;
    }

    public Label getCustomerTop2() {
        return customerTop2;
    }

    public void setCustomerTop2(Label customerTop2) {
        this.customerTop2 = customerTop2;
    }

    public Label getCustomerTop3() {
        return customerTop3;
    }

    public void setCustomerTop3(Label customerTop3) {
        this.customerTop3 = customerTop3;
    }

    public Label getCustomerTop4() {
        return customerTop4;
    }

    public void setCustomerTop4(Label customerTop4) {
        this.customerTop4 = customerTop4;
    }

    public Label getCustomerBot1() {
        return customerBot1;
    }

    public void setCustomerBot1(Label customerBot1) {
        this.customerBot1 = customerBot1;
    }

    public Label getCustomerBot2() {
        return customerBot2;
    }

    public void setCustomerBot2(Label customerBot2) {
        this.customerBot2 = customerBot2;
    }

    public Label getCustomerBot3() {
        return customerBot3;
    }

    public void setCustomerBot3(Label customerBot3) {
        this.customerBot3 = customerBot3;
    }

    public Label getPlant() {
        return plant;
    }

    public void setPlant(Label plant) {
        this.plant = plant;
    }

    public Label getEdgeBot() {
        return edgeBot;
    }

    public void setEdgeBot(Label edgeBot) {
        this.edgeBot = edgeBot;
    }

    public Label getEdgeTop() {
        return edgeTop;
    }

    public void setEdgeTop(Label edgeTop) {
        this.edgeTop = edgeTop;
    }

    public Label getEdgeLeft() {
        return edgeLeft;
    }

    public void setEdgeLeft(Label edgeLeft) {
        this.edgeLeft = edgeLeft;
    }

    public Label getEdgeRight() {
        return edgeRight;
    }

    public void setEdgeRight(Label edgeRight) {
        this.edgeRight = edgeRight;
    }

    public ImageView getSmileyFirst() {
        return smileyFirst;
    }

    public void setSmileyFirst(ImageView smileyFirst) {
        this.smileyFirst = smileyFirst;
    }

    public ImageView getSmileySecond() {
        return smileySecond;
    }

    public void setSmileySecond(ImageView smileySecond) {
        this.smileySecond = smileySecond;
    }

    public ImageView getSmileyThird() {
        return smileyThird;
    }

    public void setSmileyThird(ImageView smileyThird) {
        this.smileyThird = smileyThird;
    }

    public ImageView getSmileyFourth() {
        return smileyFourth;
    }

    public void setSmileyFourth(ImageView smileyFourth) {
        this.smileyFourth = smileyFourth;
    }

    public ImageView getSmileyFifth() {
        return smileyFifth;
    }

    public void setSmileyFifth(ImageView smileyFifth) {
        this.smileyFifth = smileyFifth;
    }

    public ImageView getSmileySixth() {
        return smileySixth;
    }

    public void setSmileySixth(ImageView smileySixth) {
        this.smileySixth = smileySixth;
    }

    public ImageView getSmileySeventh() {
        return smileySeventh;
    }

    public void setSmileySeventh(ImageView smileySeventh) {
        this.smileySeventh = smileySeventh;
    }

    public ImageView getCoinFirst() {
        return coinFirst;
    }

    public void setCoinFirst(ImageView coinFirst) {
        this.coinFirst = coinFirst;
    }

    public ImageView getCoinSecond() {
        return coinSecond;
    }

    public void setCoinSecond(ImageView coinSecond) {
        this.coinSecond = coinSecond;
    }

    public ImageView getCoinThird() {
        return coinThird;
    }

    public void setCoinThird(ImageView coinThird) {
        this.coinThird = coinThird;
    }

    public ImageView getCoinFourth() {
        return coinFourth;
    }

    public void setCoinFourth(ImageView coinFourth) {
        this.coinFourth = coinFourth;
    }

    public ImageView getCoinFifth() {
        return coinFifth;
    }

    public void setCoinFifth(ImageView coinFifth) {
        this.coinFifth = coinFifth;
    }

    public ImageView getCoinSixth() {
        return coinSixth;
    }

    public void setCoinSixth(ImageView coinSixth) {
        this.coinSixth = coinSixth;
    }

    public ImageView getCoinSeventh() {
        return coinSeventh;
    }

    public void setCoinSeventh(ImageView coinSeventh) {
        this.coinSeventh = coinSeventh;
    }

    public ImageView getOrderlabel1() {
        return orderlabel1;
    }

    public void setOrderlabel1(ImageView orderlabel1) {
        this.orderlabel1 = orderlabel1;
    }

    public ImageView getOrderlabel2() {
        return orderlabel2;
    }

    public void setOrderlabel2(ImageView orderlabel2) {
        this.orderlabel2 = orderlabel2;
    }

    public ImageView getOrderlabel3() {
        return orderlabel3;
    }

    public void setOrderlabel3(ImageView orderlabel3) {
        this.orderlabel3 = orderlabel3;
    }

    public ImageView getOrderlabel4() {
        return orderlabel4;
    }

    public void setOrderlabel4(ImageView orderlabel4) {
        this.orderlabel4 = orderlabel4;
    }

    public ImageView getOrderlabel5() {
        return orderlabel5;
    }

    public void setOrderlabel5(ImageView orderlabel5) {
        this.orderlabel5 = orderlabel5;
    }

    public ImageView getOrderlabel6() {
        return orderlabel6;
    }

    public void setOrderlabel6(ImageView orderlabel6) {
        this.orderlabel6 = orderlabel6;
    }

    public ImageView getOrderlabel7() {
        return orderlabel7;
    }

    public void setOrderlabel7(ImageView orderlabel7) {
        this.orderlabel7 = orderlabel7;
    }

    public ImageView getFirst() {
        return first;
    }

    public void setFirst(ImageView first) {
        this.first = first;
    }

    public ImageView getSecond() {
        return second;
    }

    public void setSecond(ImageView second) {
        this.second = second;
    }

    public ImageView getThird() {
        return third;
    }

    public void setThird(ImageView third) {
        this.third = third;
    }

    public ImageView getFourth() {
        return fourth;
    }

    public void setFourth(ImageView fourth) {
        this.fourth = fourth;
    }

    public ImageView getFifth() {
        return fifth;
    }

    public void setFifth(ImageView fifth) {
        this.fifth = fifth;
    }

    public ImageView getSixth() {
        return sixth;
    }

    public void setSixth(ImageView sixth) {
        this.sixth = sixth;
    }

    public ImageView getSeventh() {
        return seventh;
    }

    public void setSeventh(ImageView seventh) {
        this.seventh = seventh;
    }

    public ImageView getGameStartButton() {
        return gameStartButton;
    }

    public void setGameStartButton(ImageView gameStartButton) {
        this.gameStartButton = gameStartButton;
    }

    public ImageView getPlayAgainImage() {
        return playAgainImage;
    }

    public void setPlayAgainImage(ImageView playAgainImage) {
        this.playAgainImage = playAgainImage;
    }

    public ImageView getBackToStartImage() {
        return backToStartImage;
    }

    public void setBackToStartImage(ImageView backToStartImage) {
        this.backToStartImage = backToStartImage;
    }

    public ImageView getQuitEndScreenImage() {
        return quitEndScreenImage;
    }

    public void setQuitEndScreenImage(ImageView quitEndScreenImage) {
        this.quitEndScreenImage = quitEndScreenImage;
    }

    public Player getCofiBrew() {
        return cofiBrew;
    }

    public void setCofiBrew(Player cofiBrew) {
        this.cofiBrew = cofiBrew;
    }

    public Game getPlay() {
        return play;
    }

    public void setPlay(Game play) {
        this.play = play;
    }

    public Label[] getCollisions() {
        return collisions;
    }

    public void setCollisions(Label[] collisions) {
        this.collisions = collisions;
    }

    public Timer getControllerTimer() {
        return controllerTimer;
    }

    public void setControllerTimer(Timer controllerTimer) {
        this.controllerTimer = controllerTimer;
    }

    public File getFileToGetBackgroundMusic() {
        return fileToGetBackgroundMusic;
    }

    public void setFileToGetBackgroundMusic(File fileToGetBackgroundMusic) {
        this.fileToGetBackgroundMusic = fileToGetBackgroundMusic;
    }

    public String getMusicFileToGetBackgroundMusic() {
        return musicFileToGetBackgroundMusic;
    }

    public void setMusicFileToGetBackgroundMusic(String musicFileToGetBackgroundMusic) {
        this.musicFileToGetBackgroundMusic = musicFileToGetBackgroundMusic;
    }

    public AudioClip getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(AudioClip backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }
}
