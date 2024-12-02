package usecase.chatbot_event_conflict;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import entities.eventEntity.Event;

/**
 * This class helps create the abstraction to access the schedule which stores our events.
 */
public interface EventConflictChatbotDataAccessInterface {

    /**
     * Method to find an event by a specific day and time.
     *
     * @param day  the day of the week to search for an event
     * @param time the time to search for an event
     * @return an Optional containing the event at that day and time, or an empty Optional
     */
    Optional<Event> getEventByDayAndTime(DayOfWeek day, LocalTime time);
}