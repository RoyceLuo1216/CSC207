package database;

import entity.Schedule;
import entity.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;

public class eventStorage {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Method to save schedule to events.json. Will override the previous events.json
     * @param Schedule schedule
     * @return saved schedule
     */
    public void saveSchedule(Schedule schedule) {
        JSONObject eventsObject = new JSONObject();

        for (Event event : schedule.getAllEvents()) {
            JSONObject eventJson = new JSONObject();

            String eventName = event.getEventName();
            if (eventName == null || eventName.isEmpty()) {
                eventName = "Unnamed Event";
            }

            eventJson.put("dayStart", event.getDayStart().format(formatter));
            eventJson.put("dayEnd", event.getDayEnd().format(formatter));
            eventJson.put("priorityLabel", event.getPriorityLabel());

            if (event instanceof FixedEvent) {
                eventJson.put("eventLabel", "fixed");
            } else if (event instanceof FlexibleEvent) {
                eventJson.put("eventLabel", "flexible");
                eventJson.put("timeAllocation", event.getTimeAllocation());
            } else if (event instanceof RepeatEvent) {
                eventJson.put("eventLabel", "repeat");
                eventJson.put("daysRepeated", event.getDaysRepeated());
            }

            JSONArray tasksJson = new JSONArray();
            event.getTasks().forEach(task -> tasksJson.add(task.toString()));

            eventJson.put("tasks", tasksJson);

            eventsObject.put(taskName, eventJson);
        }

        try (FileWriter file = new FileWriter("events.json")) {
            file.write(eventsObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * Method to retrieve saved json schedule. If there is no events.json file, returns an empty schedule class.
     * @return saved schedule
     */
    public Schedule getSchedule () {
        Schedule loadedSchedule = new Schedule()
        if (!Files.exists(Paths.get("events.json"))) {
            return loadedSchedule;
        }

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("events.json");
        JSONObject savedSchedule = (JSONObject) parser.parse(reader);

        for (Object key : savedSchedule.keySet()) {
            String eventName = (String) key;
            JSONObject eventDetails = (JSONObject) jsonObject.get(eventName);

            String dayStart = (String) eventDetails.get("dayStart");
            String dayEnd = (String) eventDetails.get("dayEnd");
            int priorityLabel = (int) eventDetails.get("priorityLabel");
            String eventLabel = (String) eventDetails.get("eventLabel");
            List<Task> tasks = (List<Task>) eventDetails.get("tasks")

            if (eventName == "flexible") {
                int timeAllocation = eventDetails.get("timeAllocation")
                loadedSchedule.createFlexibleEvent(eventName, priorityLabel, dayStart, dayEnd, tasks, timeAllocation);
            } else if (eventName == "repeat") {
                List<String> daysRepeated = eventDetails.get("daysRepeated")
                loadedSchedule.createRepeatedEvent(eventName, priorityLabel, dayStart, dayEnd, tasks, daysRepeated)
            } else {
                loadedSchedule.createFixedEvent(eventName, priorityLabel, dayStart, dayEnd, tasks)
            }
        }

        return loadedSchedule;
    }


}
