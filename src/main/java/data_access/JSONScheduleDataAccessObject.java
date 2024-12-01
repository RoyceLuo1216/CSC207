package data_access;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

// TODO: ASK IF WE CAN DELETE EVENTSTORAGE

// TODO: add whatever interfaces are needed in the dao here

/**
 * Data Access Object for json storage.
 */
public class JSONScheduleDataAccessObject {

    private static final String SCHEDULE_FILE_PATH = "schedule.json";

    /**
     * Method to save schedule to events.json. Will override the previous events.json
     * @param schedule schedule
     * @return saved schedule
     */
    public void saveSchedule(InMemoryDataAccessObject schedule) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File(SCHEDULE_FILE_PATH);

            objectMapper.writeValue(file, schedule);

        } catch (IOException e) {
            System.err.println("Error writing to file " + SCHEDULE_FILE_PATH + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve saved json schedule. If there is no events.json file, returns an empty schedule class.
     * @return saved schedule
     */
    public InMemoryDataAccessObject getSchedule () {
        File file = new File(SCHEDULE_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No saved schedule found, returning empty schedule.");
            return new Schedule();
        }

        ObjectMapper final objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(file, InMemoryDataAccessObject.class);

        } catch (IOException e) {
            System.err.println("Error reading from file " + SCHEDULE_FILE_PATH + ": " + e.getMessage());
            e.printStackTrace();
        }
        return new InMemoryDataAccessObject();
    }

}
