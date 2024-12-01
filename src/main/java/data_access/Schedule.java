package data_access;

import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;
import usecase.delete.*;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing a ScheduleUseCase with a list of events. This class handles the
 * creation, deletion, and retrieval of events, and also provides a framework
 * for scheduling flexible events around fixed ones.
 */
public class Schedule implements DeleteEventDataAccessInterface {
    private final List<Event> events;

    /**
     * Constructor for the ScheduleUseCase class.
     */
    public Schedule() {
        this.events = new ArrayList<>();
    }

    public boolean addEvent(Event event) {
        return events.add(event);
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


//    /**
//     * Method to create a fixed event with specified start and end times.
//     * @param name              the name of the event
//     * @param priority          the priority of the event (on a scale of 1-5)
//     * @param dayStart          the date and time the event starts
//     * @param dayEnd            the date and time the event ends
//     * @return true if the event was successfully added
//     */
//    public boolean createFixedEvent(String name, int priority, LocalDateTime dayStart,
//                                    LocalDateTime dayEnd) {
//        FixedEvent event = new FixedEvent(dayStart, dayEnd, name, priority);
//        return events.add(event);
//    }
//
//
//    /**
//     * Method to create a flexible event that specifies a time allocation.
//     * @param name              the name of the event
//     * @param priority          the priority of the event (on a scale of 1-5)
//     * @param dayStart          the date and time the event starts
//     * @param dayEnd            the date and time the event ends
//     * @param timeAllocation    the amount of time allocated for the event (in hours)
//     * @return true if the event was successfully added
//     */
//    public boolean createFlexibleEvent(String name, int priority, LocalDateTime dayStart,
//                                       LocalDateTime dayEnd, float timeAllocation) {
//        FlexibleEvent event = new FlexibleEvent(dayStart, dayEnd, name, priority, timeAllocation);
//        return events.add(event);
//    }

    /**
     * Method to create a repeat event with specified start and end times.
     *
     * @param name         the name of the event
     * @param dayStart     the day the event starts
     * @param dayEnd       the day the event ends
     * @param timeStart    the time the event starts
     * @param timeEnd      the time the event ends
     * @param daysRepeated the days on which the event repeats
     * @return true if the event was successfully added
     */
    public boolean createRepeatEvent(String name, DayOfWeek dayStart, DayOfWeek dayEnd,
                                     LocalTime timeStart, LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        // Create a new RepeatEvent object
        RepeatEvent event = new RepeatEvent(dayStart, dayEnd, name, timeStart, timeEnd, daysRepeated);
        // Add the event to the events collection
        return events.add(event);
    }


    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    @Override
    public boolean deleteEvent(String name) {
        Optional<Event> eventToRemove = getEventByName(name);
        return eventToRemove.map(events::remove).orElse(false);
    }

    /**
     * Method to automatically schedule flexible events around fixed events.
     * This placeholder method could be expanded with API calls to Cohere or
     * OR-Tools to handle flexible scheduling.
     *
     * @return true if the scheduling process completed successfully
     */
    public boolean scheduleEvents() {
        // Placeholder for scheduling logic.
        // Example: Use Cohere API to get potential time slots for flexible events.
        // Then, apply OR-Tools for precise optimization.
        System.out.println("Scheduling events...");

        // Implement scheduling logic here
        return true;
    }

    /**
     * Method to get all events in the schedule.
     *
     * @return a list of all the events currently in the schedule
     */
    public List<Event> getAllEvents() {
        return events;
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
    /**
     * Method to find an event by a specific day and time.
     *
     * @param day  the day of the week to search for an event
     * @param time the time to search for an event
     * @return an Optional containing the event at that day and time, or an empty Optional
     */
    public Optional<Event> getEventByDayAndTime(DayOfWeek day, LocalTime time) {
        for (Event event : events) {
            DayOfWeek startDay = event.getDayStart();
            DayOfWeek endDay = event.getDayEnd();
            LocalTime startTime = event.getTimeStart();
            LocalTime endTime = event.getTimeEnd();

            // Check if the day falls within the event's days and the time within the event's time range
            if ((day.equals(startDay) || day.equals(endDay) || (day.compareTo(startDay) > 0 && day.compareTo(endDay) < 0))
                    && (time.equals(startTime) || time.equals(endTime) || (time.isAfter(startTime) && time.isBefore(endTime)))) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }




    public List<Event> getEvents() {
        return this.events;
    }


}
