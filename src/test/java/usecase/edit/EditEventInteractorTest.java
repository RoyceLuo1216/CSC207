package usecase.edit;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.Event;
import entities.eventEntity.RepeatEvent;
import entities.eventEntity.EventFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EditEventInteractorTest {

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
    void successEditFixedEventTest() {
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                                                    LocalTime.of(22, 0), LocalTime.of(0, 0));
        dataAccessObject.addEvent(event);

        EditEventInputData inputData = new EditEventInputData("Study for CSC207 Exam", "Fixed",
            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.of(21, 0), LocalTime.of(1, 0),
                                                                                                    new ArrayList<>());

        EditEventOutputBoundary successPresenter = new EditEventOutputBoundary() {

            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        EditEventInputBoundary interactor = new EditEventInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);

        Event updatedEvent = dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.TUESDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.WEDNESDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeEnd());
    }

    // success test
    @Test
    void successEditRepeatEventTest() {
        event = EventFactory.createRepeatEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                LocalTime.of(22, 0), LocalTime.of(0, 0),
                new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)));
        dataAccessObject.addEvent(event);

        EditEventInputData inputData = new EditEventInputData("Study for CSC207 Exam", "Repeat",
               DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.of(21, 0), LocalTime.of(1, 0),
               new ArrayList<>(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)));

        EditEventOutputBoundary successPresenter = new EditEventOutputBoundary() {

            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        EditEventInputBoundary interactor = new EditEventInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);

        RepeatEvent updatedEvent = (RepeatEvent) dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.TUESDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.WEDNESDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeEnd());
        assertEquals(new ArrayList<>(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)),
                updatedEvent.getDaysRepeated());
    }

    @Test
    void failureEventDoesNotExistTest() {
        EditEventInputData inputData = new EditEventInputData("Study for CSC207 Exam", "Fixed",
               DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(13, 0),
               new ArrayList<>());

        EditEventOutputBoundary failurePresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                fail("Use case failure is unexpected (the event does not exist).");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("The event, Study for CSC207 Exam does not exist.", error);
            }
        };

        EditEventInputBoundary interactor = new EditEventInteractor(dataAccessObject, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEventTypeChangeNotAllowedTest() {
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.MONDAY,
                                    LocalTime.of(10, 0), LocalTime.of(12, 0));
        dataAccessObject.addEvent(event);

        EditEventInputData inputData = new EditEventInputData("Study for CSC207 Exam", "repeat",
               DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(13, 0),
                new ArrayList<>());

        EditEventOutputBoundary failurePresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                fail("Use case failure is unexpected (the event does not exist).");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("The event type cannot be changed.", error);
            }
        };

        EditEventInputBoundary interactor = new EditEventInteractor(dataAccessObject, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failEditFixedEvent(){
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                LocalTime.of(22, 0), LocalTime.of(0, 0));
        dataAccessObject.addEvent(event);

        EditEventInputData inputData = new EditEventInputData("Study for CSC207 Exam", "Fixed",
                DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, LocalTime.of(21, 0), LocalTime.of(1, 0),
                new ArrayList<>());

        EditEventOutputBoundary successPresenter = new EditEventOutputBoundary() {

            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event can't be added, due to incompatible times", error);
            }
        };
        EditEventInputBoundary interactor = new EditEventInteractor(dataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}