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

    String SRC = "src", MAIN = "main", RESOURCES = "resources", COM = "com", EXAMPLE = "example", DECAFE = "decafe";
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

    public Image createImageFilePath(String filename) throws FileNotFoundException {
        File filepath = new File("");
        String filePath = filepath.getAbsolutePath() + File.separator + SRC + File.separator + MAIN + File.separator + RESOURCES + File.separator
                + COM + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public static ImageView getCustomerImage(ImageView customer, ImageView[] searchArray ){
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

    public static void spawnCustomerIFLessThanThreeInShop(){
        if (customersInCoffeeShop.size() < 3 && freeChairs.size() != 0) {
            ImageView customerImage = getCustomerRandomPic();
            customerImage.setVisible(true);
            ImageView order = getCustomerOrderLabel(customerImage);
            ImageView smiley = getCustomerImage(customerImage, smileyImages);
            ImageView coin = getCustomerImage(customerImage, coinImages);
            String SRC = "src", MAIN = "main", RESOURCES = "resources", COM = "com", EXAMPLE = "example", DECAFE = "decafe";
            Customer customer = new Customer(customerImage, order, freeSeatChosen, smiley, coin);
            customersInCoffeeShop.add(customer); //to check if not more than 3 customers are in the store
            allCustomersCreated.add(customer); //to stop all timers that are still alive even after customer has
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + SRC + File.separator + MAIN + File.separator + RESOURCES + File.separator
                    + COM + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "doorBell.mp3";
            AudioClip doorBell = new AudioClip(new File(musicFile).toURI().toString());
            doorBell.play();
            customer.customerWaitingTime();
        }
    }


    public void startTimerToSpawnCustomers(int duration, Timer controllerTimer){
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomerIFLessThanThreeInShop();
                        controllerTimer.purge();
                    }
                },
                duration * 1000L
        );
    }

    public void startTimerForCustomersLeave(Customer customer){
        this.orderLabel.setVisible(false);
        this.customerSmileyMood.setVisible(false);
        spawingTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            leave(customer.getCustomerImage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        spawingTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsWaitingTimer.cancel();
    }
    //Methode for the general 60 seconds timer

    public void customerWaitingTime()  {
        Customer customer = this;
        TimerTask timerTask = new TimerTask() {
            int totalWaitingSecondsByCustomer = 60;
            @Override
            public void run() {
                totalWaitingSecondsByCustomer--;
                if (totalWaitingSecondsByCustomer == 59){
                    setCustomerMoodImage("smileygreen.png", true, false, false);
                }else if (totalWaitingSecondsByCustomer == 30){
                    setCustomerMoodImage("smileyyellow.png", false, true, false);
                }else if (totalWaitingSecondsByCustomer == 15){
                    setCustomerMoodImage("smileyred.png", false, false, true);
                }
                else if (totalWaitingSecondsByCustomer == 0){ //when the timer has finished - customer leaves
                    startTimerForCustomersLeave(customer);
                }
            }
        };
        this.sixtySecondsWaitingTimer.schedule(timerTask, 0, 1000);//it should call this methode every second
    }

    private void setCustomerMoodImage(String imageName, boolean green, boolean yellow, boolean red) {
        customerSmileyMood.setVisible(true);
        try {
            customerSmileyMood.setImage(createImageFilePath(imageName));
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // You might want to handle this exception more gracefully
        }
        isGreenSmiley = green;
        isYellowSmiley = yellow;
        isRedSmiley = red;
    }

    public void displayOrder(ImageView orderlabel) throws FileNotFoundException {
        this.customerOrder = getCustomerRandomOrder();
        setCustomerOrder(customerOrder);

        if (customerOrder.equals("cake")) {
            displayOrderBubble(orderlabel, "bubbleCake", chairsOccupiedByCustomers);
        } else if (customerOrder.equals("coffee")) {
            displayOrderBubble(orderlabel, "bubbleCoffee", chairsOccupiedByCustomers);
        }
        this.isCustomerOrdered = true;
    }

    private void displayOrderBubble(ImageView orderlabel, String bubbleType, int chairsOccupied) throws FileNotFoundException {
        String imageFileName;
        if (chairsOccupied == 0 || chairsOccupied == 1 || chairsOccupied == 4 || chairsOccupied == 6) {
            imageFileName = bubbleType + "TopLeft.png";
        } else if (chairsOccupied == 2 || chairsOccupied == 3) {
            imageFileName = bubbleType + "TopRight.png";
        } else if (chairsOccupied == 5) {
            imageFileName = bubbleType + "BottomRight.png";
        } else {
            return; // Handle other cases if needed
        }

        orderlabel.setVisible(true);
        orderlabel.setImage(createImageFilePath(imageFileName));
    }


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiterImage) throws FileNotFoundException{
        waiterImage.setImage(createImageFilePath(CofiBrew.getFilenameImageWithoutProduct())); //set CofiBrew without order
        if (CofiBrew.getProductInHand().equals(customer.getCustomerOrder())) { //if CofiBrew has the right order
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            this.isCustomerLeftUnhappy = false;
            startTimerForCustomersLeave(this); // start timer to leave the coffee shop (true - it was the right order)
            return true;
        } else {
            CofiBrew.setProductInHand("none"); // change product hold by player to none
            startTimerForCustomersLeave(this);  // start timer to leave the coffee shop (false - it was the wrong order)
            return false;
        }
    }
    //when the customer leaves after 60 seconds without being served or received wrong order

    public static void noMoneySpent(Customer customer) throws FileNotFoundException {
        customer.customerPaymentPicture.setVisible(false);
        customer.customerPaymentPicture.setDisable(true);
        freeChairs.add(customer.getChairsOccupiedByCustomers());
        customer.startTimerToSpawnCustomers(5, spawingTimer);
    }
    //Methode for when the customer leaves

    public void leave (ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShop.removeIf(customer -> customer.getCustomerImage().equals(customerImage)); //remove customer from customerList
        this.customerPaymentPicture.setVisible(true);
        this.customerPaymentPicture.setDisable(false);
        if (this.isCustomerLeftUnhappy){ //when customer leaves after 60 seconds or received wrong order
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + SRC + File.separator + MAIN + File.separator + RESOURCES + File.separator
                    + COM + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "wrongChoice.mp3";
            AudioClip wrongOrder = new AudioClip(new File(musicFile).toURI().toString());
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
            String musicFile = f.getAbsolutePath() + File.separator + SRC + File.separator + MAIN + File.separator + RESOURCES + File.separator +
                    COM + File.separator + EXAMPLE + File.separator + DECAFE + File.separator + "rightChoice.mp3";
            AudioClip rightOrder = new AudioClip(new File(musicFile).toURI().toString());
            rightOrder.play();
        }
    }

    // -----------------------Getter-------------------
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

    public ImageView getCustomerImage() {
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
    // -----------------------Setter-------------------
    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public static void setSpawingTimer(Timer spawingTimer) {
        Customer.spawingTimer = spawingTimer;
    }
}

