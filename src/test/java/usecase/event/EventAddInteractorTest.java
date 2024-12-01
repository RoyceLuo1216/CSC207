package usecase.event;

import data_access.InMemoryDataAccessObject;
import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventAddInteractorTest {
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

    @Test
    public void successAddEvent(){

        EventInputData eventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        EventOutputBoundary successPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }
        };

        EventInputBoundary interactor = new EventInteractor(dataAccessObject, successPresenter);
        interactor.execute(eventAddInputData);

        RepeatEvent updatedEvent = (RepeatEvent) dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeEnd());
    }

    @Test
    public void failTimesNotCompatibleAddEvent(){
        EventInputData eventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        EventOutputBoundary successPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event can't be added, due to incompatible times", error);
            }
        };

        EventInputBoundary interactor = new EventInteractor(dataAccessObject, successPresenter);
        interactor.execute(eventAddInputData);
    }

    @Test
    public void failAlreadyAddedEvent(){
        EventInputData eventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        EventOutputBoundary successPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }
        };

        EventInputBoundary interactor = new EventInteractor(dataAccessObject, successPresenter);
        interactor.execute(eventAddInputData);


        EventInputData newEventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        EventOutputBoundary newSuccessPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event already exists", error);
            }
        };

        EventInputBoundary newInteractor = new EventInteractor(dataAccessObject, newSuccessPresenter);
        newInteractor.execute(newEventAddInputData);
    }
}