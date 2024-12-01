package usecase.event;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.FixedEvent;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventAddInteractorTest {
    private InMemoryDataAccessObject dataAccessObject;

    @BeforeEach
    void setUp() {
        dataAccessObject = new InMemoryDataAccessObject();
    }

    @AfterEach
    void tearDown() {
        dataAccessObject = null;
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

            @Override
            public void backToMainView(){return;}
        };
        dataAccessObject = new InMemoryDataAccessObject();
        EventInputBoundary interactor = new EventInteractor(dataAccessObject, successPresenter);
        interactor.execute(eventAddInputData);

        FixedEvent updatedEvent = (FixedEvent) dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeEnd());
    }

    @Test
    public void failTimesNotCompatibleAddEvent(){
        EventInputData eventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(21, 0),
                LocalTime.of(1, 0));

        EventOutputBoundary successPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event can't be added, due to incompatible times", error);
            }

            @Override
            public void backToMainView(){return;}
        };

        dataAccessObject = new InMemoryDataAccessObject();
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

            @Override
            public void backToMainView(){return;}
        };

        dataAccessObject = new InMemoryDataAccessObject();
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

            @Override
            public void backToMainView(){return;}
        };

        EventInputBoundary newInteractor = new EventInteractor(dataAccessObject, newSuccessPresenter);
        newInteractor.execute(newEventAddInputData);
    }
}