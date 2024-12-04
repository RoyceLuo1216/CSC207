package data_access;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import entities.eventEntity.Event;
import entities.eventEntity.RepeatEvent;
import usecase.chatbot_event_conflict.EventConflictChatbotDataAccessInterface;
import usecase.delete.DeleteEventDataAccessInterface;
import usecase.edit.EditEventDataAccessInterface;
import usecase.event.AddEventDataAccessInterface;
import usecase.eventInformation.EventInformationDataAccessInterface;
import usecase.repeat.RepeatEventDataAccessInterface;
import usecase.schedule.ScheduleDataAccessInterface;

/**
 * Class representing a ScheduleUseCase with a list of events. This class handles the
 * creation, deletion, and retrieval of events, and also provides a framework
 * for scheduling flexible events around fixed ones.
 */
public class InMemoryDataAccessObject implements DeleteEventDataAccessInterface,
        EditEventDataAccessInterface, ScheduleDataAccessInterface,
        AddEventDataAccessInterface, RepeatEventDataAccessInterface,
        EventConflictChatbotDataAccessInterface, EventInformationDataAccessInterface {
    private final List<Event> events;
    private String currentEventName;

    /**
     * Constructor for the ScheduleUseCase class.
     */
    public InMemoryDataAccessObject() {
        this.events = new ArrayList<>();
        this.currentEventName = "";
    }

    /**
     * Adding an event to our schedule using Event Factory.
     *
     * @param event event object to be added.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean removeEvent(String name) {
        return events.removeIf(event -> event.getEventName().equalsIgnoreCase(name));
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     */
    @Override
    public void deleteEvent(String name) {
        System.out.println("before deletion num of events: " + getEvents().size());

        final Optional<Event> eventToRemove = getEventByName(name);
        eventToRemove.map(events::remove);
        System.out.println("Deleting event: " + name);
        System.out.println("num of events: " + getEvents().size());
    }

    /**
     * Method to get all events in the schedule.
     *
     * @return a list of all the events currently in the schedule
     */
    public List<Event> getAllEvents() {
        return this.events;
    }

    /**
     * Sets the current event to the one we want.
     *
     * @param eventName name of the event we are using.
     */
    @Override
    public void setCurrentEventName(String eventName) {
        this.currentEventName = eventName;
        System.out.println("Current event name is: " + currentEventName);
    }

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */

    @Override
    public Optional<Event> getEventByName(String name) {
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(name)) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    /**
     * Method to find the name of the current event stored in memory.
     *
     * @return the name of the current event as a string.
     */
    @Override
    public String getCurrentEventName() {
        return this.currentEventName;
    }

    /**
     * Retrieves all events as a Map<String, List<Object>>.
     *
     * @return a map of event names to their details
     */
    @Override
    public Map<String, Map<String, Object>> getAllEventInfo() {
        // Assume `events` is a List<Event> containing all events in memory
        List<Event> myEvents = this.events;

        Map<String, Map<String, Object>> eventDetailsMap = new HashMap<>();

        for (Event event : myEvents) {
            Map<String, Object> eventDetails = new HashMap<>();
            eventDetails.put("dayStart", event.getDayStart());
            eventDetails.put("timeStart", event.getTimeStart());
            eventDetails.put("dayEnd", event.getDayEnd());
            eventDetails.put("timeEnd", event.getTimeEnd());
            eventDetails.put("eventType", event.getEventType());

            eventDetailsMap.put(event.getEventName(), eventDetails);
        }

        return eventDetailsMap;
    }


    /**
     * Returns the details (time, day, etc.) associated with the current event stored in memory.
     *
     * @return a list of event details or an empty list if the event is not found.
     */
    @Override
    public List<Object> getCurrentEventDetails() {
        // Retrieve the current event by name
        final Optional<Event> currentEventOptional = getEventByName(currentEventName);

        if (currentEventOptional.isPresent()) {
            final Event currentEvent = currentEventOptional.get();

            // Build the details list
            final List<Object> details = new ArrayList<>();
            details.add(currentEvent.getEventName());
            details.add(currentEvent.getEventType());
            details.add(currentEvent.getDayStart());
            details.add(currentEvent.getDayEnd());
            details.add(currentEvent.getTimeStart());
            details.add(currentEvent.getTimeEnd());

            // Add repeated days:
            // For non-repeating events, set repeat days to just the start day
            if ("Repeat".equalsIgnoreCase(currentEvent.getEventType())) {
                final RepeatEvent repeatEvent = (RepeatEvent) currentEvent;
                details.add(repeatEvent.getDaysRepeated());
            }
            else {
                // For non-repeat events, include just the start day as the repeat day
                details.add(List.of(currentEvent.getDayStart()));
            }

            return details;
        }

        // Return an empty list if no current event is found
        return new ArrayList<>();
    }

    /**
     * Method to get all events of a specified type in the schedule.
     *
     * @param eventType the class type of events to filter by (e.g., FixedEvent.class)
     * @return a list of events of the specified type
     */
    public <T extends Event> List<T> getEventsByType(Class<T> eventType) {
        return events.stream()
                .filter(eventType::isInstance)
                .map(eventType::cast)
                .collect(Collectors.toList());
    }

    /**
     * Method to find an event by a specific day and time.
     *
     * @param day  the day of the week to search for an event
     * @param time the time to search for an event
     * @return an Optional containing the event at that day and time, or an empty Optional
     */
    public Optional<Event> getEventByDayAndTime(DayOfWeek day, LocalTime time) {
        for (Event event : events) {
            final DayOfWeek startDay = event.getDayStart();
            final DayOfWeek endDay = event.getDayEnd();
            final LocalTime startTime = event.getTimeStart();
            final LocalTime endTime = event.getTimeEnd();

            // Check if the day falls within the event's days and the time within the event's time range
            if ((day.equals(startDay) || day.equals(endDay) || day.compareTo(startDay) > 0 && day.compareTo(endDay) < 0)
                    && (time.equals(startTime) || time.equals(endTime) || time.isAfter(startTime)
                    && time.isBefore(endTime))) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<String> getAllEventNames() {
        return events.stream()
                .map(Event::getEventName)
                .collect(Collectors.toList());
    }

    public List<Event> getEvents() {
        return this.events;
    }

}
