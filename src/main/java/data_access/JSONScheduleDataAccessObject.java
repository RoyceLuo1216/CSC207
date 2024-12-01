package data_access;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: ASK IF WE CAN DELETE EVENTSTORAGE

// TODO: add whatever interfaces are needed in the dao here

/**
 * Data Access Object for json storage.
 */
public class JSONScheduleDataAccessObject {

    private static final String SCHEDULE_FILE_PATH = "schedule.json";

    /**
     * Method to save schedule to events.json. Will override the previous events.json
     * @param dataAccessObject schedule
     */
    public void saveSchedule(InMemoryDataAccessObject dataAccessObject) {
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final File file = new File(SCHEDULE_FILE_PATH);

            objectMapper.writeValue(file, dataAccessObject);

        }
        catch (IOException error) {
            System.err.println("Error writing to file " + SCHEDULE_FILE_PATH + ": " + error.getMessage());
            error.printStackTrace();
        }
    }

    /**
     * Method to retrieve saved json schedule. If there is no events.json file, returns an empty schedule class.
     * @return saved schedule
     */
    public InMemoryDataAccessObject getSchedule() {
        final File file = new File(SCHEDULE_FILE_PATH);
        InMemoryDataAccessObject dataAccessObject = new InMemoryDataAccessObject();
        if (!file.exists()) {
            System.out.println("No saved schedule found, returning empty schedule.");
        }
        else {
            final ObjectMapper objectMapper = new ObjectMapper();

            try {
                dataAccessObject = objectMapper.readValue(file, InMemoryDataAccessObject.class);
            }
            catch (IOException error) {
                System.err.println("Error reading from file " + SCHEDULE_FILE_PATH + ": " + error.getMessage());
                error.printStackTrace();
            }
        }
        return dataAccessObject;
    }

}
