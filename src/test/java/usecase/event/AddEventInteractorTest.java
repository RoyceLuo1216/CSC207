package usecase.event;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.EventFactory;
import entities.eventEntity.FixedEvent;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import usecase.event.AddEventInputData;

public class AddEventInteractorTest {
    private InMemoryDataAccessObject dataAccessObject;
    private EventFactory eventFactory;

    @BeforeEach
    void setUp() {
        dataAccessObject = new InMemoryDataAccessObject();
        eventFactory = new EventFactory();
    }

    @AfterEach
    void tearDown() {
        dataAccessObject = null;
    }

    @Test
    public void successAddEvent(){
        AddEventInputData eventAddInputData = new AddEventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        AddEventOutputBoundary successPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }

            @Override
            public void backToScheduleView(){return;}
        };
        dataAccessObject = new InMemoryDataAccessObject();
        AddEventInputBoundary interactor = new AddEventInteractor(dataAccessObject, successPresenter, eventFactory);
        interactor.execute(eventAddInputData);

        FixedEvent updatedEvent = (FixedEvent) dataAccessObject.getEventByName("Study for CSC207 Exam").get();
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayStart());
        assertEquals(DayOfWeek.MONDAY, updatedEvent.getDayEnd());
        assertEquals(LocalTime.of(1, 0), updatedEvent.getTimeStart());
        assertEquals(LocalTime.of(21, 0), updatedEvent.getTimeEnd());
    }

    @Test
    public void failTimesNotCompatibleAddEvent(){
        AddEventInputData eventAddInputData = new AddEventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(21, 0),
                LocalTime.of(1, 0));

        AddEventOutputBoundary successPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event can't be added, due to incompatible times", error);
            }

            @Override
            public void backToScheduleView(){return;}
        };

        dataAccessObject = new InMemoryDataAccessObject();
        AddEventInputBoundary interactor = new AddEventInteractor(dataAccessObject, successPresenter, eventFactory);
        interactor.execute(eventAddInputData);
    }

    @Test
    public void failDayNotCompatibleAddEvent(){
        AddEventInputData addEventInputData = new AddEventInputData("Study for CSC207 Exam", DayOfWeek.TUESDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(12, 0),
                LocalTime.of(14, 0));

        AddEventOutputBoundary successPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event can't be added, due to incompatible times", error);
            }

            /**
             * Transitions back to the main view.
             */
            @Override
            public void backToScheduleView() {

            }
        };

        dataAccessObject = new InMemoryDataAccessObject();
        AddEventInputBoundary interactor = new AddEventInteractor(dataAccessObject, successPresenter, eventFactory);
        interactor.execute(addEventInputData);
    }

    @Test
    public void failAlreadyAddedEvent(){
        AddEventInputData eventAddInputData = new AddEventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        AddEventOutputBoundary successPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                assertEquals("Study for CSC207 Exam", outputData.getEventName());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Test failed");
            }

            @Override
            public void backToScheduleView(){return;}
        };

        dataAccessObject = new InMemoryDataAccessObject();
        AddEventInputBoundary interactor = new AddEventInteractor(dataAccessObject, successPresenter, eventFactory);
        interactor.execute(eventAddInputData);


        AddEventInputData newAddEventInputData = new AddEventInputData("Study for CSC207 Exam", DayOfWeek.MONDAY,
                DayOfWeek.MONDAY,
                LocalTime.of(1, 0),
                LocalTime.of(21, 0));

        AddEventOutputBoundary newSuccessPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                fail("Test failed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Event already exists", error);
            }

            @Override
            public void backToScheduleView(){return;}
        };

        AddEventInputBoundary newInteractor = new AddEventInteractor(dataAccessObject, newSuccessPresenter,
                eventFactory);
        newInteractor.execute(newAddEventInputData);
    }
}