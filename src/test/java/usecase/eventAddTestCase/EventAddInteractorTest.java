package usecase.eventAddTestCase;

import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;
import entities.ScheduleEntity.Schedule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import usecase.event.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventAddInteractorTest {
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
    }

    @AfterEach
    void tearDown() {
        schedule = null;
    }

    @Test
    public void successAddEvent(){

        EventInputData eventAddInputData = new EventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(21, 0),
                LocalTime.of(1, 0));

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

        EventInputBoundary interactor = new EventInteractor(schedule, successPresenter);
        interactor.execute(eventAddInputData);

        RepeatEvent updatedEvent = (RepeatEvent) schedule.getEventByName("Study for CSC207 Exam").get();
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
                assertEquals("Unable to add repeat event", error);
            }
        };

        EventInputBoundary interactor = new EventInteractor(schedule, successPresenter);
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

        EventInputBoundary interactor = new EventInteractor(schedule, successPresenter);
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
                assertEquals("Unable to add repeat event", error);
            }
        };

        EventInputBoundary newInteractor = new EventInteractor(schedule, newSuccessPresenter);
        newInteractor.execute(newEventAddInputData);
    }
}
