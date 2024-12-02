package usecase.repeat;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.RepeatEvent;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepeatInteractorTest {
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
    public void successAddRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<>();
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

            @Override
            public void backToMainView(){return;}
        };
        dataAccessObject = new InMemoryDataAccessObject();
        RepeatInputBoundary interactor = new RepeatInteractor(dataAccessObject, successPresenter);
        interactor.execute(repeatInputData);

        RepeatEvent updatedEvent = (RepeatEvent) dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeEnd());
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
                LocalTime.of(21, 0),
                LocalTime.of(1, 0),
                repeatedDays);

        RepeatOutputBoundary successPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
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
        RepeatInputBoundary interactor = new RepeatInteractor(dataAccessObject, successPresenter);
        interactor.execute(repeatInputData);
    }

    @Test
    public void failDayNotCompatibleAddRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<DayOfWeek>();
        repeatedDays.add(DayOfWeek.MONDAY);
        repeatedDays.add(DayOfWeek.TUESDAY);
        repeatedDays.add(DayOfWeek.WEDNESDAY);

        RepeatInputData repeatInputData = new RepeatInputData("Study for CSC207 Exam", DayOfWeek.TUESDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                repeatedDays);

        RepeatOutputBoundary successPresenter = new RepeatOutputBoundary() {
            @Override
            public void prepareSuccessView(RepeatOutputData outputData) {
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
        RepeatInputBoundary interactor = new RepeatInteractor(dataAccessObject, successPresenter);
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

            @Override
            public void backToMainView(){return;}
        };
        dataAccessObject = new InMemoryDataAccessObject();
        RepeatInputBoundary interactor = new RepeatInteractor(dataAccessObject, successPresenter);
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
                assertEquals("Event already exists", error);
            }

            @Override
            public void backToMainView(){return;}
        };

        RepeatInputBoundary newInteractor = new RepeatInteractor(dataAccessObject, newSuccessPresenter);
        newInteractor.execute(newRepeatInputData);
    }

    @Test
    public void failNoRepeatEvent(){
        List<DayOfWeek> repeatedDays = new ArrayList<DayOfWeek>();

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
                assertEquals("Please enter more than 0 repeat days", error);
            }

            @Override
            public void backToMainView(){return;}
        };

        dataAccessObject = new InMemoryDataAccessObject();
        RepeatInputBoundary interactor = new RepeatInteractor(dataAccessObject, successPresenter);
        interactor.execute(repeatInputData);
    }
}