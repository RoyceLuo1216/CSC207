package usecase.repeatEventTestCase;

import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;
import entities.ScheduleEntity.Schedule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import usecase.repeat.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepeatEventInteractorTest {
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
    public void successAddRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<DayOfWeek>();
        repeatedDays.add(DayOfWeek.MONDAY);
        repeatedDays.add(DayOfWeek.TUESDAY);
        repeatedDays.add(DayOfWeek.WEDNESDAY);

        RepeatInputData repeatInputData = new RepeatInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(21, 0),
                LocalTime.of(1, 0),
                repeatedDays);

        RepeatOutputBoundary successPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }
        };

        RepeatInputBoundary interactor = new RepeatInteractor(schedule, successPresenter);
        interactor.execute(repeatInputData);

        RepeatEvent updatedEvent = (RepeatEvent) schedule.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeEnd());
        assertEquals(repeatedDays, updatedEvent.getDaysRepeated());
    }

    @Test
    public void failTimesNotCompatibleAddRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<DayOfWeek>();
        repeatedDays.add(DayOfWeek.MONDAY);
        repeatedDays.add(DayOfWeek.TUESDAY);
        repeatedDays.add(DayOfWeek.WEDNESDAY);

        RepeatInputData repeatInputData = new RepeatInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0),
                repeatedDays);

        RepeatOutputBoundary successPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Unable to add repeat event", error);
            }
        };

        RepeatInputBoundary interactor = new RepeatInteractor(schedule, successPresenter);
        interactor.execute(repeatInputData);
    }

    @Test
    public void failAlreadyAddedRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<DayOfWeek>();
        repeatedDays.add(DayOfWeek.MONDAY);
        repeatedDays.add(DayOfWeek.TUESDAY);
        repeatedDays.add(DayOfWeek.WEDNESDAY);

        RepeatInputData repeatInputData = new RepeatInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0),
                repeatedDays);

        RepeatOutputBoundary successPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }
        };

        RepeatInputBoundary interactor = new RepeatInteractor(schedule, successPresenter);
        interactor.execute(repeatInputData);


        List<DayOfWeek> newRepeatedDays = new ArrayList<DayOfWeek>();
        repeatedDays.add(DayOfWeek.MONDAY);
        repeatedDays.add(DayOfWeek.TUESDAY);
        repeatedDays.add(DayOfWeek.WEDNESDAY);

        RepeatInputData newRepeatInputData = new RepeatInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0),
                repeatedDays);

        RepeatOutputBoundary newSuccessPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Unable to add repeat event", error);
            }
        };

        RepeatInputBoundary newInteractor = new RepeatInteractor(schedule, newSuccessPresenter);
        interactor.execute(newRepeatInputData);
    }
}
