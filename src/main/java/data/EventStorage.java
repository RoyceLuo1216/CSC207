package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import data_access.InMemoryDataAccessObject;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EventStorage {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Method to save schedule to events.json. Will override the previous events.json
     *
     * @param inMemoryDataAccessObject schedule
     * @return saved schedule
     */
    public void saveSchedule(InMemoryDataAccessObject inMemoryDataAccessObject) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File("schedule.json");

            objectMapper.writeValue(file, inMemoryDataAccessObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve saved json schedule. If there is no events.json file, returns an empty schedule class.
     *
     * @return saved schedule
     */
    public InMemoryDataAccessObject getSchedule() {
        File file = new File("schedule.json");
        if (!file.exists()) {
            return new InMemoryDataAccessObject();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(file, InMemoryDataAccessObject.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InMemoryDataAccessObject();
    }


}
