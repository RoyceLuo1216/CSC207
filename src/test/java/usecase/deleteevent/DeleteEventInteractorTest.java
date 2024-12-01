package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import usecase.delete.*;
import entities.eventEntity.Event;
import entities.eventEntity.FixedEvent;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventInteractorTest {

    private static class MockDataAccessObject implements DeleteEventDataAccessInterface {
        private boolean eventExists;
        private boolean deleteCalled;

        public MockDataAccessObject(boolean eventExists) {
            this.eventExists = eventExists;
            this.deleteCalled = false;
        }

        @Override
        public void deleteEvent(String eventName) {
            if (eventExists) {
                deleteCalled = true;
            }
        }

        @Override
        public Optional<Event> getEventByName(String name) {
            if (eventExists) {
                return Optional.of(new FixedEvent(
                        DayOfWeek.WEDNESDAY,        // Start day
                        DayOfWeek.WEDNESDAY,        // End day
                        name,                       // Event name
                        LocalTime.of(10, 0),        // Start time
                        LocalTime.of(12, 0)         // End time
                ));
            }
            return Optional.empty();
        }

        public boolean isDeleteCalled() {
            return deleteCalled;
        }
    }

    private static class MockPresenter implements DeleteEventOutputBoundary {
        private boolean successCalled;
        private boolean failureCalled;
        private String successEventName;
        private String failureMessage;

        @Override
        public void presentSuccess(DeleteEventOutputData outputData) {
            successCalled = true;
            successEventName = outputData.getEventName();
        }

        @Override
        public void presentFailure(String errorMessage) {
            failureCalled = true;
            failureMessage = errorMessage;
        }

        public boolean isSuccessCalled() {
            return successCalled;
        }

        public boolean isFailureCalled() {
            return failureCalled;
        }

        public String getSuccessEventName() {
            return successEventName;
        }

        public String getFailureMessage() {
            return failureMessage;
        }
    }

    @Test
    void testDeleteEvent_Success() {
        // Arrange
        String eventName = "Christmas Brunch";
        MockDataAccessObject mockDao = new MockDataAccessObject(true);
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);
        DeleteEventInputData inputData = new DeleteEventInputData(eventName);

        // Act
        interactor.execute(inputData);

        // Assert
        assertTrue(mockDao.isDeleteCalled(), "Event deletion should be called on DAO.");
        assertTrue(mockPresenter.isSuccessCalled(), "Success presenter method should be called.");
        assertFalse(mockPresenter.isFailureCalled(), "Failure presenter method should not be called.");
        assertEquals(eventName, mockPresenter.getSuccessEventName(), "Correct event name should be passed to presenter.");
    }

    @Test
    void testDeleteEvent_Failure_EventDoesNotExist() {
        // Arrange
        String eventName = "Nonexistent Event";
        MockDataAccessObject mockDao = new MockDataAccessObject(false);
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);
        DeleteEventInputData inputData = new DeleteEventInputData(eventName);

        // Act
        interactor.execute(inputData);

        // Assert
        assertFalse(mockDao.isDeleteCalled(), "Event deletion should not be called on DAO when event does not exist.");
        assertFalse(mockPresenter.isSuccessCalled(), "Success presenter method should not be called.");
        assertTrue(mockPresenter.isFailureCalled(), "Failure presenter method should be called.");
        assertEquals("The event, Nonexistent Event does not exist.", mockPresenter.getFailureMessage(), "Correct failure message should be passed to presenter.");
    }
}
