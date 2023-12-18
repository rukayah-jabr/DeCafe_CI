package com.example.decafe;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.util.*;


public class Customer {
    private String customerOrder;
    private ImageView customerPicture;
    private ImageView orderLabel;
    private int chairsOccupiedByCustomers;
    private Timer sixtySecondsWaitingTimer;
    private static Timer spawingTimer;
    private ImageView customerSmileyMood;
    private ImageView customerPaymentPicture;

    private boolean isCustomerOrdered;
    private boolean isGreenSmiley;
    private boolean isYellowSmiley;
    private boolean isRedSmiley;

    private boolean isCustomerLeftUnhappy = true; // (received wrong order or left after 60 secs)


    public static List<Customer> customersInCoffeeShop = new ArrayList<>();
    public static List<Customer> allCustomersCreated = new ArrayList<>();
    public static List<Integer> freeChairs;
    public static ImageView[] customerImages;
    private static int freeSeatChosen = 0;
    public static ImageView[] smileyImages;
    public static ImageView[] orderLabels;
    public static ImageView[] coinImages;

    // Constructors
    Customer(){}
    Customer(ImageView image, ImageView label, int chairsOccupiedByCustomers, ImageView customerSmileyMood, ImageView customerPaymentPicture) {
        this.customerPicture = image;
        this.orderLabel = label;
        this.isCustomerOrdered = false;
        this.chairsOccupiedByCustomers = chairsOccupiedByCustomers;
        this.customerSmileyMood = customerSmileyMood;
        this.customerPaymentPicture = customerPaymentPicture;
        this.sixtySecondsWaitingTimer = new Timer();
    }

    // Getter
    public static Timer getSpawingTimer() {
        return spawingTimer;
    }

    public Timer getSixtySecondsWaitingTimer() {
        return sixtySecondsWaitingTimer;
    }

    public static void addFreeSeat(int chairLeft) {
        Customer.freeChairs.add(chairLeft);
    }

    public boolean isGreenSmiley() {
        return isGreenSmiley;
    }

    public boolean isRedSmiley() {
        return isRedSmiley;
    }

    public boolean isYellowSmiley() {
        return isYellowSmiley;
    }

    public boolean isCustomerOrdered() {
        return this.isCustomerOrdered;
    }

    public String getCustomerOrder() {
        return customerOrder;
    }

    public int getChairsOccupiedByCustomers() {
        return chairsOccupiedByCustomers;
    }

    public ImageView getCustomerMoodImage() {
        return this.customerPicture;
    }

    public ImageView getCustomerOrderLabel() {
        return this.orderLabel;
    }

    public String getCustomerRandomOrder() {

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> customerOrder = "cake";
            case 1 -> customerOrder = "coffee";
        }
        return customerOrder;
    }

    public ImageView getCustomerPaymentPicture() {
        return customerPaymentPicture;
    }

    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public static void setSpawingTimer(Timer spawingTimer) {
        Customer.spawingTimer = spawingTimer;
    }


    public Image createImageFilePath(String filename) throws FileNotFoundException {
        String SRC = "src", MAIN = "main", RESOURCES = "resources", COM = "com", EXAMPLE = "example", DECAFE = "decafe";
        File filepath = new File("");
        String filePath = filepath.getAbsolutePath() + File.separator + SRC + File.separator + MAIN + File.separator + RESOURCES + File.separator
                + COM + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public static ImageView getCustomerMoodImage(ImageView customer, ImageView[] searchArray ){
        ImageView wantedImage = new ImageView();
        for (int i = 0; i < customerImages.length; i++) {
            if (customerImages[i].equals(customer)) {
                wantedImage = searchArray[i];
            }
        }
        return wantedImage;
    }

    public static ImageView getCustomerOrderLabel(ImageView customer) {
        ImageView customerOrder = new ImageView();
        for (int i = 0; i < customerImages.length; i++) {
            if (customerImages[i].equals(customer)) {
                customerOrder = orderLabels[i];
            }
        }
        return customerOrder;
    }


    public static ImageView getCustomerRandomPic(){
        Random random = new Random();
        int index = freeChairs.get(random.nextInt(freeChairs.size()));
        freeSeatChosen = index;

        if (!freeChairs.contains(index)) { //when the customer is already visible make new random number
            getCustomerRandomPic();
        }

        freeChairs.remove(Integer.valueOf(index)); //remove the number from the number list of chairs so there are no duplicates

        return customerImages[index];
    }

    
    public static void spawnCustomers(){
        if (customersInCoffeeShop.size() < 3 && freeChairs.size() != 0) { //spawn a new customer this when under 3 customers are in the café
            ImageView customerImage = getCustomerRandomPic(); //get random picture from Array
            customerImage.setVisible(true); //make this picture visible

            ImageView order = getCustomerOrderLabel(customerImage); //get the label for the customer
            ImageView smiley = getCustomerMoodImage(customerImage, smileyImages); //gets the smiley picture for the customer
            ImageView coin = getCustomerMoodImage(customerImage, coinImages); //gets the coin picture for the customer


            Customer customer = new Customer(customerImage, order, freeSeatChosen, smiley, coin); //make new customer object
            customersInCoffeeShop.add(customer); //to check if not more than 3 customers are in the store
            allCustomersCreated.add(customer); //to stop all timers that are still alive even after customer has left
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
        this.customerSmileyMood.setVisible(false);
        spawingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            leave(customer.getCustomerMoodImage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        spawingTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsWaitingTimer.cancel(); //cancel the 60 seconds when customer left
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
                    customerSmileyMood.setVisible(true);
                    try {
                        customerSmileyMood.setImage(createImageFilePath("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    isGreenSmiley = true;
                    isYellowSmiley = false;
                    isRedSmiley = false;
                }else if (seconds == 30){ //set yellow smiley when the customer has just spawned
                    customerSmileyMood.setVisible(true);
                    try {
                        customerSmileyMood.setImage(createImageFilePath("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    isGreenSmiley = false;
                    isYellowSmiley = true;
                    isRedSmiley = false;
                }else if (seconds == 15){ //set red smiley when the customer has just spawned
                    customerSmileyMood.setVisible(true);
                    try {
                        customerSmileyMood.setImage(createImageFilePath("smileyred.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    isGreenSmiley = false;
                    isYellowSmiley = false;
                    isRedSmiley = true;
                }
                else if (seconds == 0){ //when the timer has finished - customer leaves
                    startTimerLeave(customer);
                }
            }
        };

        this.sixtySecondsWaitingTimer.schedule(timerTask, 0, 1000);//it should call this methode every second

    }

    //Methode to display order
    public void displayOrder(ImageView orderlabel) throws FileNotFoundException {
        this.customerOrder = getCustomerRandomOrder();
        setCustomerOrder(customerOrder);
        if(customerOrder.equals("cake")) {
            if (chairsOccupiedByCustomers == 0 || chairsOccupiedByCustomers == 1 || chairsOccupiedByCustomers == 4 || chairsOccupiedByCustomers == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCakeTopLeft.png"));
            } else if(chairsOccupiedByCustomers == 2 || chairsOccupiedByCustomers == 3){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCakeTopRight.png"));
            } else if(chairsOccupiedByCustomers == 5) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCakeBottomRight.png"));
            }
        } else if(customerOrder.equals("coffee")){
            if (chairsOccupiedByCustomers == 0 || chairsOccupiedByCustomers == 1 || chairsOccupiedByCustomers == 4 || chairsOccupiedByCustomers == 6) {
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCoffeeTopLeft.png"));
            } else if(chairsOccupiedByCustomers == 2 || chairsOccupiedByCustomers == 3){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCoffeeTopRight.png"));
            } else if(chairsOccupiedByCustomers == 5){
                orderlabel.setVisible(true);
                orderlabel.setImage(createImageFilePath("bubbleCoffeeBottomRight.png"));
            }
        }
        this.isCustomerOrdered = true;
    }


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiterImage) throws FileNotFoundException{
        waiterImage.setImage(createImageFilePath(CofiBrew.getFilenameImageWithoutProduct())); //set CofiBrew without order
        if (CofiBrew.getProductInHand().equals(customer.getCustomerOrder())) { //if CofiBrew has the right order
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            this.isCustomerLeftUnhappy = false;
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
        customer.customerPaymentPicture.setVisible(false);
        customer.customerPaymentPicture.setDisable(true);
        freeChairs.add(customer.getChairsOccupiedByCustomers());
        customer.startTimerSpawn(5, spawingTimer);
    }

    //Methode for when the customer leaves
    public void leave (ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShop.removeIf(customer -> customer.getCustomerMoodImage().equals(customerImage)); //remove customer from customerList
        this.customerPaymentPicture.setVisible(true);
        this.customerPaymentPicture.setDisable(false);
        if (this.isCustomerLeftUnhappy){ //when customer leaves after 60 seconds or received wrong order
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "wrongChoice.mp3";
            AudioClip wrongOrder = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer collectMoney = new MediaPlayer(sound);
            wrongOrder.play();
            this.customerPaymentPicture.setImage(this.createImageFilePath("coin.png")); // set coin Image to empty plate
            this.customerPaymentPicture.setOnMouseClicked(event1 -> { // set click event to this
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

