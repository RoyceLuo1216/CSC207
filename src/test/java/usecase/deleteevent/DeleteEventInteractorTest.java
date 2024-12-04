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
        private String currentEventName;

        public MockDataAccessObject(boolean eventExists, String currentEventName) {
            this.eventExists = eventExists;
            this.currentEventName = currentEventName;
            this.deleteCalled = false;
        }

        @Override
        public void deleteEvent(String eventName) {
            if (eventExists && eventName.equals(currentEventName)) {
                deleteCalled = true;
            }
        }

        @Override
        public Optional<Event> getEventByName(String name) {
            if (eventExists && name.equals(currentEventName)) {
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

        @Override
        public String getCurrentEventName() {
            return currentEventName;
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

        @Override
        public void backToScheduleView() {
            // Simulate transition back to schedule view
        }

        @Override
        public void editView() {
            // Simulate transition back to edit view
        }

        /**
         * gets event details.
         *
         * @param deleteEventOutputData output data.
         */
        @Override
        public void prepareEventDetails(DeleteEventOutputData deleteEventOutputData) {

        }

        /**
         * Notify view to remove event button
         *
         * @param eventName
         */
        @Override
        public void removeEventFromView(String eventName) {

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
        MockDataAccessObject mockDao = new MockDataAccessObject(true, eventName);
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);

        // Act
        interactor.deleteEvent();

        // Assert
        assertTrue(mockDao.isDeleteCalled(), "Event deletion should be called on DAO.");
        assertTrue(mockPresenter.isSuccessCalled(), "Success presenter method should be called.");
        assertFalse(mockPresenter.isFailureCalled(), "Failure presenter method should not be called.");
        assertEquals(eventName, mockPresenter.getSuccessEventName(), "Correct event name should be passed to presenter.");
    }

    @Test
    void testDeleteEvent_Failure_NoCurrentEvent() {
        // Arrange
        MockDataAccessObject mockDao = new MockDataAccessObject(false, "");
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);

        // Act
        interactor.deleteEvent();

        // Assert
        assertFalse(mockDao.isDeleteCalled(), "Event deletion should not be called on DAO when no event exists.");
        assertFalse(mockPresenter.isSuccessCalled(), "Success presenter method should not be called.");
        assertTrue(mockPresenter.isFailureCalled(), "Failure presenter method should be called.");
        assertEquals("No current event to delete.", mockPresenter.getFailureMessage(), "Correct failure message should be passed to presenter.");
    }

    @Test
    void testFetchEventDetails_Success() {
        // Arrange
        String eventName = "Weekly Meeting";
        MockDataAccessObject mockDao = new MockDataAccessObject(true, eventName);
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);

        // Act
        interactor.fetchEventDetails();

        // Assert
        assertFalse(mockPresenter.isFailureCalled(), "Failure presenter method should not be called.");
        assertTrue(mockPresenter.isSuccessCalled(), "Success presenter method should be called.");
        assertEquals(eventName, mockPresenter.getSuccessEventName(), "Correct event name should be fetched and passed to presenter.");
    }

    @Test
    void testFetchEventDetails_Failure_NoCurrentEvent() {
        // Arrange
        MockDataAccessObject mockDao = new MockDataAccessObject(false, "");
        MockPresenter mockPresenter = new MockPresenter();
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(mockPresenter, mockDao);

        // Act
        interactor.fetchEventDetails();

        // Assert
        assertTrue(mockPresenter.isFailureCalled(), "Failure presenter method should be called when no current event exists.");
        assertEquals("No current event found.", mockPresenter.getFailureMessage(), "Correct failure message should be passed to presenter.");
        assertFalse(mockPresenter.isSuccessCalled(), "Success presenter method should not be called.");
    }
}
