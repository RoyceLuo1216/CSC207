package usecase.eventInformation;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.Event;
import entities.eventEntity.EventFactory;
import entities.eventEntity.FixedEvent;
import entities.eventEntity.RepeatEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.edit.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventInformationInteractorTest {
    private InMemoryDataAccessObject dataAccessObject;
    private Event event;

    @BeforeEach
    void setUp() {
        dataAccessObject = new InMemoryDataAccessObject();
    }

    @AfterEach
    void tearDown() {
        dataAccessObject = null;
        event = null;
    }

    // fixed event
    @Test
    void successFixedEventInformationTest() {
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                LocalTime.of(22, 0), LocalTime.of(0, 0));
        dataAccessObject.addEvent(event);

        EventInformationInputData inputData = new EventInformationInputData("Study for CSC207 Exam");

        EventInformationOutputBoundary successPresenter = new EventInformationOutputBoundary() {

            @Override
            public void prepareView(EventInformationOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertEquals("Fixed", outputData.getEventType());
                assertEquals(DayOfWeek.MONDAY, outputData.getDayStart());
                assertEquals(DayOfWeek.TUESDAY, outputData.getDayEnd());
                assertEquals(LocalTime.of(22, 0), outputData.getTimeStart());
                assertEquals(LocalTime.of(0, 0), outputData.getTimeEnd());
                assertTrue(outputData.getDaysRepeated().isEmpty());
            }
        };
        EventInformationInputBoundary interactor = new EventInformationInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

    // repeat event
    @Test
    void successRepeatEventInformationTest() {
        event = EventFactory.createRepeatEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                LocalTime.of(22, 0), LocalTime.of(0, 0),
                new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)));
        dataAccessObject.addEvent(event);

        EventInformationInputData inputData = new EventInformationInputData("Study for CSC207 Exam");

        EventInformationOutputBoundary successPresenter = new EventInformationOutputBoundary() {

            @Override
            public void prepareView(EventInformationOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertEquals("Repeat", outputData.getEventType());
                assertEquals(DayOfWeek.MONDAY, outputData.getDayStart());
                assertEquals(DayOfWeek.TUESDAY, outputData.getDayEnd());
                assertEquals(LocalTime.of(22, 0), outputData.getTimeStart());
                assertEquals(LocalTime.of(0, 0), outputData.getTimeEnd());
                assertEquals(new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)),
                        outputData.getDaysRepeated());
            }
        };
        EventInformationInputBoundary interactor = new EventInformationInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }

}
