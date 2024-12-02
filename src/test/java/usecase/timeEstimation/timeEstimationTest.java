package usecase.timeEstimation;

import adapter.CohereClient;
import org.junit.jupiter.api.BeforeEach;
import usecase.chatbot_time_estimation.ChatbotInputData;
import usecase.chatbot_time_estimation.TimeEstimationInteractor;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class timeEstimationTest {

    /* Tests that a single event prompt works when running cohereTimeAllocation call. */
    @Test
    public void successSingleEvent() {
        timeEstimationDummyOutputBoundary outputBoundary = new timeEstimationDummyOutputBoundary();
        TimeEstimationInteractor interactor = new TimeEstimationInteractor(outputBoundary);
        ChatbotInputData inputData = new ChatbotInputData("Go on a walk");

        // Capture the console output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(baos));

        // Execute the interactor, which should print to the console
        interactor.execute(inputData);

        String consoleOutput = baos.toString().trim();  // remove newlines

        assertTrue(consoleOutput.contains("minutes"));

        System.setOut(originalSystemOut);
    }

    /* Tests that a multiple event prompt works when running cohereTimeAllocation call. */
    @Test
    public void successMultipleEvents() {
        timeEstimationDummyOutputBoundary outputBoundary = new timeEstimationDummyOutputBoundary();
        TimeEstimationInteractor interactor = new TimeEstimationInteractor(outputBoundary);
        ChatbotInputData inputData = new ChatbotInputData("Go on a walk, make dinner, do laundry," +
                " and pick up kids from soccer practice");

        // Capture the console output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(baos));

        // Execute the interactor, which should print to the console
        interactor.execute(inputData);

        String consoleOutput = baos.toString().trim();  // remove newlines

        assertTrue(consoleOutput.contains("hour"));

        System.setOut(originalSystemOut);
    }

    /* Tests that a cohereTimeAllocation call returns a error message when given an innapropriate prompt. */
    @Test
    public void failureIncorrectPrompt() {
        timeEstimationDummyOutputBoundary outputBoundary = new timeEstimationDummyOutputBoundary();
        TimeEstimationInteractor interactor = new TimeEstimationInteractor(outputBoundary);
        ChatbotInputData inputData = new ChatbotInputData("lorem ipsum goo goo gaa gaa banana irony" +
                " lil wayne down");

        // Capture the console output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(baos));

        // Execute the interactor, which should print to the console
        interactor.execute(inputData);

        String consoleOutput = baos.toString().trim();  // remove newlines

        assertTrue(consoleOutput.contains("invalid"));

        System.setOut(originalSystemOut);
    }
}