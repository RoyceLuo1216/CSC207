package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import usecase.delete.DeleteEventDataAccessInterface;
import usecase.delete.DeleteEventInputBoundary;
import usecase.delete.DeleteEventInputData;
import usecase.delete.DeleteEventInteractor;
import usecase.delete.DeleteEventOutputBoundary;
import usecase.delete.DeleteEventOutputData;


import java.util.Optional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventInteractorTest {

    private static class MockDataAccessObject implements DeleteEventDataAccessInterface {
        private boolean eventExists;
        private boolean deleteCalled;
        private final LocalDateTime startTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        private final LocalDateTime endTime = LocalDateTime.of(2024, 12, 25, 12, 0);

        public MockDataAccessObject(boolean eventExists) {
            this.eventExists = eventExists;
            this.deleteCalled = false;
        }

        @Override
        public boolean deleteEvent(String eventName) {
            if (eventExists) {
                deleteCalled = true;
                return true;
            }
            return false;
        }

        @Override
        public Optional<entities.EventEntity.Event> getEventByName(String name) {
            return eventExists ? Optional.of(new entities.EventEntity.FixedEvent(startTime, endTime,
                    "Christmas Brunch", 4)) : Optional.empty();
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
