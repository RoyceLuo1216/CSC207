package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data_access.InMemoryDataAccessObject;


/**
 * A ScheudleView class to visually display our schedule.
 */
public class ScheduleView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final Map<String, Integer> WEEKDAY_NUMBERS = Map.of(
            "Sunday", 1,
            "Monday", 4,
            "Tuesday", 7,
            "Wednesday", 10,
            "Thursday", 13,
            "Friday", 16,
            "Saturday", 19
    );
    private final InMemoryDataAccessObject inMemoryDataAccessObject;
    private boolean visible;
    private final JFrame mainFrame;

    /**
     * Constructor for the ScheuldeView class.
     */
    public ScheduleView() {
        inMemoryDataAccessObject = new InMemoryDataAccessObject();
        mainFrame = new JFrame("Schedule Viewer");
    }

    /**
     * Schedule view main class.
     */
    public static void main(String[] args) {
        final ScheduleView scheduleView = new ScheduleView();
        scheduleView.setVisible();
        scheduleView.displaySchedule();
    }

    /**
     * Create a panel of times ont he left side.
     *
     * @param panel The main panel we want to add to
     * @param c     Constraints for our grid
     */
    private static void createTimesPanel(JPanel panel, GridBagConstraints c) {

        List<JButton> result = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            JButton label = new JButton(i + ":00");
            result.add(label);
        }

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        JLabel timeLabel = new JLabel("Time");
        panel.add(timeLabel, c);
        for (int i = 0; i < 24; i++) {
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 2 * (i + 1);
            c.gridwidth = 1;
            c.gridheight = 2;
            panel.add(result.get(i), c);
        }

    }

    /**
     * Create a panel of weekdays on the left side.
     *
     * @param panel The main panel we want to add to
     * @param c     Constraints for our grid
     */
    private static void createWeekdaysPanel(JPanel panel, GridBagConstraints c) {

        final List<String> weekdays =
                Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun");
        final List<JButton> result = new ArrayList<>();

        for (int i = 0; i < weekdays.size(); i++) {
            final JButton weekdayLabel = new JButton(weekdays.get(i));
            result.add(weekdayLabel);
        }

        for (int i = 0; i < 7; i++) {
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 3 * i + 1;
            c.gridy = 0;
            c.gridwidth = 3;
            panel.add(result.get(i), c);
        }
    }

    private static void createEventButton(JPanel panel, GridBagConstraints c) {
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 22;
        c.gridy = 10;
        c.gridwidth = 1;
        c.gridheight = 2;
        JButton result = new JButton("Add Event");
        panel.add(result, c);
    }

    /**
     * Create an event button.
     *
     * @param panel   The main panel we want to add to
     * @param constraints       Constraints for our grid
     * @param name    Name of event
     * @param weekday Weekday
     * @param start   Start time
     * @param end     End time
     */
    // TODO: This method assumes event happens on same day, update so it can do an event over multiple dayss
    // TODO: Update this class to use event class. Had trouble importing classes so this was not possible

    private static void createEventButton(JPanel panel, GridBagConstraints constraints, String name, String weekday, int start, int end) {
        final int weekdayNum = getWeekdayNumber(weekday);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = weekdayNum;
        constraints.gridy = start * 2 + 2;
        constraints.gridwidth = 3;
        constraints.gridheight = (end - start) * 2 + 2;

        final JButton result = new JButton(name);
        panel.add(result, constraints);
    }

    private static int getWeekdayNumber(String weekday) {
        if (!WEEKDAY_NUMBERS.containsKey(weekday)) {
            throw new IllegalArgumentException("Invalid weekday: " + weekday);
        }
        return WEEKDAY_NUMBERS.get(weekday);
    }

    /**
     * Display our schedule.
     */
    public void displaySchedule() {

        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());

        // Constraints parameter for our grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Create the panel of time labels
        createTimesPanel(main, constraints);

        // Create the panel of weekday labels
        createWeekdaysPanel(main, constraints);

        // Create the panel of events
        createEventButton(main, constraints);

        // Tests to create an event
        createEventButton(main, constraints, "Homework", "Tuesday", 9, 13);
        createEventButton(main, constraints, "Midterm", "Wednesday", 14, 17);

        mainFrame.setContentPane(main);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(560, 500);
        mainFrame.setVisible(visible);
    }

    /**
     * Make schedule visible.
     */
    public void setVisible() {
        visible = true;
    }

    /**
     * Make schedule invisible.
     */
    public void setInvisible() {
        visible = false;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
