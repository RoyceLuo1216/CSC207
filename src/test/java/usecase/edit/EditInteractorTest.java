package usecase.edit;

import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;
import entities.ScheduleEntity.Schedule;
import factory.EventFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EditInteractorTest {

    private Schedule schedule;
    private Event event;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
    }

    @AfterEach
    void tearDown() {
        schedule = null;
        event = null;
    }

    @Test
    void successEditFixedEventTest() {
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                                                    LocalTime.of(22, 0), LocalTime.of(0, 0));
        schedule.addEvent(event);

        EditInputData inputData = new EditInputData("Study for CSC207 Exam", "Fixed",
            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.of(21, 0), LocalTime.of(1, 0),
                                                                                                    new ArrayList<>());

        EditOutputBoundary successPresenter = new EditOutputBoundary() {

            @Override
            public void prepareSuccessView(EditOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        EditInputBoundary interactor = new EditInteractor(schedule, successPresenter);
        interactor.execute(inputData);

        Event updatedEvent = schedule.getEventByName("Study for CSC207 Exam").get();
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
        schedule.addEvent(event);

        EditInputData inputData = new EditInputData("Study for CSC207 Exam", "Repeat",
               DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.of(21, 0), LocalTime.of(1, 0),
               new ArrayList<>(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)));

        EditOutputBoundary successPresenter = new EditOutputBoundary() {

            @Override
            public void prepareSuccessView(EditOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        EditInputBoundary interactor = new EditInteractor(schedule, successPresenter);
        interactor.execute(inputData);

        RepeatEvent updatedEvent = (RepeatEvent) schedule.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.TUESDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.WEDNESDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeEnd());
        assertEquals(new ArrayList<>(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY)),
                updatedEvent.getDaysRepeated());
    }

    @Test
    void failureEventDoesNotExistTest() {
        EditInputData inputData = new EditInputData("Study for CSC207 Exam", "Fixed",
               DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(13, 0),
               new ArrayList<>());

        EditOutputBoundary failurePresenter = new EditOutputBoundary() {
            @Override
            public void prepareSuccessView(EditOutputData outputData) {
                fail("Use case failure is unexpected (the event does not exist).");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("The event, Study for CSC207 Exam does not exist.", error);
            }
        };

        EditInputBoundary interactor = new EditInteractor(schedule, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEventTypeChangeNotAllowedTest() {
        event = EventFactory.createFixedEvent("Study for CSC207 Exam", DayOfWeek.MONDAY, DayOfWeek.MONDAY,
                                    LocalTime.of(10, 0), LocalTime.of(12, 0));
        schedule.addEvent(event);

        EditInputData inputData = new EditInputData("Study for CSC207 Exam", "repeat",
               DayOfWeek.TUESDAY, DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(13, 0),
                new ArrayList<>());

        EditOutputBoundary failurePresenter = new EditOutputBoundary() {
            @Override
            public void prepareSuccessView(EditOutputData outputData) {
                fail("Use case failure is unexpected (the event does not exist).");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("The event type cannot be changed.", error);
            }
        };

        EditInputBoundary interactor = new EditInteractor(schedule, failurePresenter);
        interactor.execute(inputData);
    }
}