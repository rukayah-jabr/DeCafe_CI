package com.example.decafe;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MachineTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    void testCreateImageCorrectType() {

        Machine coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        Machine cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");

        String filename = "CofiBrewUp.png";

        try {
            Image resultImage = coffeeMachine.createImage(filename);
            assertEquals(Image.class, resultImage.getClass());
        } catch (IOException e) {
            fail("FileNotFoundException should not be thrown", e);
        }
    }

    @Test
    void testCreateImageNotNull() {

        Machine coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        Machine cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");

        String filename = "CofiBrewUp.png";

        try {
            Image resultImage = coffeeMachine.createImage(filename);
            assertNotNull(resultImage, "Image should not be null");
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not be thrown", e);

        }
    }

    @Test
    void testCreateImageFileExists() {
        Machine coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        Machine cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");

        String filename = "CofiBrewUp.png";

        try {
            Image resultImage = coffeeMachine.createImage(filename);
            assertNotNull(resultImage, "Image should not be null");
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not be thrown when the file exists", e);
        }
    }


    @Test
    void testCreateImageFileNotExists() {
        Machine coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        Machine cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");

        String filename = "non_existent_image.png";

        assertThrows(FileNotFoundException.class, () -> coffeeMachine.createImage(filename),
                "FileNotFoundException should be thrown when the file does not exist");
    }

    @Test
    void testDoProgressBarAnimation() {

        Machine machine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");

        String filename = "CofiBrewUp.png";
        // Set up necessary stubbing for createImage method
        Image mockImage = mock(Image.class);
        try {
            when(machine.createImage(anyString())).thenReturn(mockImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Set up mock objects
        Timer mockTimer = mock(Timer.class);
        ImageView mockImageView = mock(ImageView.class);
        ProgressBar mockProgressBar = mock(ProgressBar.class);

        // Test the doProgressBarAnimation method
        assertDoesNotThrow(() -> machine.doProgressBarAnimation(mockTimer, mockImageView, mockProgressBar, mockImage));

        // Verify that the expected methods were called
        verify(mockImageView, times(1)).setDisable(true);
        verify(mockProgressBar, times(1)).setVisible(true);

        // Use ArgumentCaptor to capture the TimerTask passed to productionTimer.schedule
        ArgumentCaptor<TimerTask> timerTaskCaptor = ArgumentCaptor.forClass(TimerTask.class);
        verify(mockTimer).schedule(timerTaskCaptor.capture(), any(Long.class));

        // Simulate the run method of TimerTask to verify additional behavior
        TimerTask capturedTimerTask = timerTaskCaptor.getValue();
        capturedTimerTask.run();

        // Verify that the expected methods were called when the TimerTask runs
        verify(mockImageView, times(1)).setImage(mockImage);
        verify(mockImageView, times(1)).setDisable(false);
    }

    @Test
    void testDisplayProduct() throws FileNotFoundException {

        Player cofiBrew = mock(Player.class);

        Machine machine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");

        String filename = "CofiBrewUp.png";
        // Set up necessary stubbing for Player's getProductInHand method
        when(cofiBrew.getProductInHand()).thenReturn("none");

        // Set up necessary stubbing for createImage method
        Image mockImage = mock(Image.class);
        when(machine.createImage(anyString())).thenReturn(mockImage);

        // Set up ImageView and ProgressBar (replace ImageView and ProgressBar with the actual class names)
        ImageView waiterImageView = new ImageView();
        ImageView machineImageView = new ImageView();
        ProgressBar machineProgressBar = new ProgressBar();

        // Test the displayProduct method
        assertDoesNotThrow(() -> machine.displayProduct(waiterImageView, machineImageView, cofiBrew, machineProgressBar));

        // Assert that the appropriate methods were called
        verify(machine, times(1)).createImage(anyString());
        verify(cofiBrew, atLeastOnce()).getProductInHand();
        verify(machineImageView, times(1)).setImage(mockImage);
    }

}
