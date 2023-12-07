package com.example.decafe;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.*;


public class Customer {
    private String order; //The order of the customer
    private ImageView customer; //picture of the customer
    private ImageView orderLabel; //label that displays order
    private int chair; //number of chair the customer is sitting
    private Timer sixtySecondsTimer; //timer for the 60 seconds waiting time
    private static Timer controllerTimer; //timer for leaving, spawning
    private ImageView smiley; //picture of smiley for the mood of the customer
    private ImageView coinImage; //picture of the money the customer is leaving behind

    private boolean alreadyOrdered; //boolean to see if the customer has already ordered
    private boolean green; //boolean for smiley
    private boolean yellow; //boolean for smiley
    private boolean red; //boolean for smiley

    private boolean leftUnhappy = true; //boolean to see if customer has left unhappy (received wrong order or left after 60 secs)

    public static List<Customer> customersInCoffeeShop = new ArrayList<>(); //list with all customers that are in the café
    public static List<Customer> allCustomers = new ArrayList<>(); //list with all customers ever created
    public static List<Integer> freeChairs; //integer list with all chair numbers
    public static ImageView[] customerImages; //array with all customer pictures
    private static int freeSeatChosen = 0;
    public static ImageView[] smileyImages; //image for smiley
    public static ImageView[] orderLabels; //label for order
    public static ImageView[] coinImages; //image for coins

    // Constructors
    Customer(){}
    Customer(ImageView image, ImageView label, int chair, ImageView smiley, ImageView coinImage) {
        this.customer = image;
        this.orderLabel = label;
        this.alreadyOrdered = false;
        this.chair = chair;
        this.smiley = smiley;
        this.coinImage = coinImage;
        this.sixtySecondsTimer = new Timer();
    }

    // Getter
    public static Timer getControllerTimer() {
        return controllerTimer;
    }

    public Timer getSixtySecondsTimer() {
        return sixtySecondsTimer;
    }

    public static void addFreeSeat(int chairLeft) { //add chair number to the list when customer has left
        Customer.freeChairs.add(chairLeft);
    }

    public boolean isGreen() { //to see if the color of the smiley
        return green;
    }

    public boolean isRed() { //to see if the color of the smiley
        return red;
    }

    public boolean isYellow() { //to see if the color of the smiley
        return yellow;
    }

    public boolean isAlreadyOrdered() { //return if the customer has already ordered or not
        return this.alreadyOrdered;
    }

    public String getOrder() { //returns the order of the customer
        return order;
    }

    public int getChair() { //get the number of the chair the customer is sitting
        return chair;
    }

    public ImageView getImage() { //returns the image of the customer
        return this.customer;
    }

    public ImageView getLabel() { //returns the label of the customer
        return this.orderLabel;
    }

    public String getRandomOrder() { //returns random order

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> order = "cake";
            case 1 -> order = "coffee";
        }

        return order;
    }

    public ImageView getCoinImage() { //returns the image of the coin
        return coinImage;
    }

    // Setter
    public void setOrder(String order) { //sets the order of the customer
        this.order = order;
    }

    public static void setControllerTimer(Timer controllerTimer) { //sets the timer
        Customer.controllerTimer = controllerTimer;
    }

    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    //Returns the appropriate image for the customer
    public static ImageView getImage(ImageView customer, ImageView[] searchArray ){
        ImageView wantedImage = new ImageView();

        if (customerImages[0].equals(customer)) {
            wantedImage = searchArray[0];
        } else if (customerImages[1].equals(customer)) {
            wantedImage = searchArray[1];
        } else if (customerImages[2].equals(customer)) {
            wantedImage = searchArray[2];
        } else if (customerImages[3].equals(customer)) {
            wantedImage = searchArray[3];
        } else if (customerImages[4].equals(customer)) {
            wantedImage = searchArray[4];
        } else if (customerImages[5].equals(customer)) {
            wantedImage = searchArray[5];
        } else if (customerImages[6].equals(customer)) {
            wantedImage = searchArray[6];
        }

        return wantedImage;
    }

    //Returns the appropriate label for the customer
    public static ImageView getLabel(ImageView customer) {

        ImageView customerOrder = new ImageView();

        if (customerImages[0].equals(customer)) {
            customerOrder = orderLabels[0];
        } else if (customerImages[1].equals(customer)) {
            customerOrder = orderLabels[1];
        } else if (customerImages[2].equals(customer)) {
            customerOrder = orderLabels[2];
        } else if (customerImages[3].equals(customer)) {
            customerOrder = orderLabels[3];
        } else if (customerImages[4].equals(customer)) {
            customerOrder = orderLabels[4];
        } else if (customerImages[5].equals(customer)) {
            customerOrder = orderLabels[5];
        } else if (customerImages[6].equals(customer)) {
            customerOrder = orderLabels[6];
        }

        return customerOrder;

    }

    //Returns random customer picture
    public static ImageView getRandomPic(){
        Random random = new Random();
        int index = freeChairs.get(random.nextInt(freeChairs.size()));
        freeSeatChosen = index;

        if (!freeChairs.contains(index)) { //when the customer is already visible make new random number
            getRandomPic();
        }

        freeChairs.remove(Integer.valueOf(index)); //remove the number from the number list of chairs so there are no duplicates

        return customerImages[index];
    }

    //Methode to spawn customers
    public static void spawnCustomers(){
        if (customersInCoffeeShop.size() < 3 && freeChairs.size() != 0) { //spawn a new customer this when under 3 customers are in the café
            ImageView customerImage = getRandomPic(); //get random picture from Array
            customerImage.setVisible(true); //make this picture visible

            ImageView order = getLabel(customerImage); //get the label for the customer
            ImageView smiley = getImage(customerImage, smileyImages); //gets the smiley picture for the customer
            ImageView coin = getImage(customerImage, coinImages); //gets the coin picture for the customer


            Customer customer = new Customer(customerImage, order, freeSeatChosen, smiley, coin); //make new customer object
            customersInCoffeeShop.add(customer); //to check if not more than 3 customers are in the store
            allCustomers.add(customer); //to stop all timers that are still alive even after customer has left
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "doorBell.mp3";
            AudioClip doorBell = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer doorBell = new MediaPlayer(sound);
            doorBell.play();
            customer.waitingTime(); //place customer in the waitingTime of  60 seconds
        }
    }

    //Timer to spawn the customers
    public void startTimerSpawn(int duration, Timer controllerTimer){
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomers();
                        controllerTimer.purge();
                    }
                },
                duration * 1000L
        );
    }

    //Methode for the timer when customer leaves
    public void startTimerLeave (Customer customer){
        this.orderLabel.setVisible(false);
        this.smiley.setVisible(false);
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            leave(customer.getImage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        controllerTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsTimer.cancel(); //cancel the 60 seconds when customer left
    }

    //Methode for the general 60 seconds timer
    public void waitingTime()  {
        Customer customer = this;
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;
            @Override
            public void run() {
                seconds --;
                if (seconds == 59){ //set green smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = true;
                    yellow = false;
                    red = false;
                }else if (seconds == 30){ //set yellow smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = true;
                    red = false;
                }else if (seconds == 15){ //set red smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyred.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = false;
                    red = true;
                }
                else if (seconds == 0){ //when the timer has finished - customer leaves
                    startTimerLeave(customer);
                }
            }
        };

        this.sixtySecondsTimer.schedule(timerTask, 0, 1000);//it should call this methode every second

    }

    //Methode to display order
    public void displayOrder(ImageView orderlabel) throws FileNotFoundException {
        this.order = getRandomOrder();
        setOrder(order);
        if(order.equals("cake")) {
            if (chair == 0 || chair == 1 || chair == 4 || chair == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeTopLeft.png"));
            } else if(chair == 2 || chair == 3){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeTopRight.png"));
            } else if(chair == 5) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCakeBottomRight.png"));
            }
        } else if(order.equals("coffee")){
            if (chair == 0 || chair == 1 || chair == 4 || chair == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeTopLeft.png"));
            } else if(chair == 2 || chair == 3){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeTopRight.png"));
            } else if(chair == 5){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImage("bubbleCoffeeBottomRight.png"));
            }
        }
        this.alreadyOrdered = true;
    }


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiterImage) throws FileNotFoundException{
        waiterImage.setImage(createImage(CofiBrew.getFilenameImageWithoutProduct())); //set CofiBrew without order
        if (CofiBrew.getProductInHand().equals(customer.getOrder())) { //if CofiBrew has the right order
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            this.leftUnhappy = false;
            startTimerLeave(this); // start timer to leave the coffee shop (true - it was the right order)
            return true;
        } else {
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            startTimerLeave(this);  // start timer to leave the coffee shop (false - it was the wrong order)
            return false;
        }
    }

    //when the customer leaves after 60 seconds without being served or received wrong order
    public static void noMoneySpent(Customer customer) throws FileNotFoundException {
        customer.coinImage.setVisible(false);
        customer.coinImage.setDisable(true);
        freeChairs.add(customer.getChair());
        customer.startTimerSpawn(5, controllerTimer);
    }

    //Methode for when the customer leaves
    public void leave (ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShop.removeIf(customer -> customer.getImage().equals(customerImage)); //remove customer from customerList
        this.coinImage.setVisible(true);
        this.coinImage.setDisable(false);
        if (this.leftUnhappy){ //when customer leaves after 60 seconds or received wrong order
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "wrongChoice.mp3";
            AudioClip wrongOrder = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer collectMoney = new MediaPlayer(sound);
            wrongOrder.play();
            this.coinImage.setImage(this.createImage("coin.png")); // set coin Image to empty plate
            this.coinImage.setOnMouseClicked(event1 -> { // set click event to this
                try {
                    noMoneySpent(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "rightChoice.mp3";
            AudioClip rightOrder = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer collectMoney = new MediaPlayer(sound);
            rightOrder.play();
        }
    }
}

