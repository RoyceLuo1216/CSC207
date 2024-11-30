package view;

import entities.ScheduleEntity.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A ScheudleView class to visually display our schedule.
 */
public class ScheduleView extends JPanel implements ActionListener, PropertyChangeListener {

    private Schedule schedule;
    private boolean visible;
    private JFrame mainFrame;

    /**
     * Constructor for the ScheuldeView class.
     */
    public ScheduleView() {
        schedule = new Schedule();
        mainFrame = new JFrame("Schedule Viewer");
    }

    public static void main(String[] args) {
        ScheduleView scheduleView = new ScheduleView();
        scheduleView.setVisible();
        scheduleView.displaySchedule();
    }

    /**
     * Display our schedule.
     */
    public void displaySchedule() {

        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());

        //Constraints parameter for our grid
        GridBagConstraints c = new GridBagConstraints();

        //Create the panel of time labels
        createTimesPanel(main, c);

        //Create the panel of weekday labels
        createWeekdaysPanel(main, c);

        //Create the panel of events
        createEventButton(main, c);

        //Tests to create an event
        createEventButton(main, c, "Homework", "Tuesday", 9, 13);
        createEventButton(main, c, "Midterm", "Wednesday", 14, 17);

        mainFrame.setContentPane(main);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(560, 500);
        mainFrame.setVisible(visible);
    }

    /**
     * Make shceudle visible
     */
    public void setVisible() {
        visible = true;
    }

    /**
     * Make schedule invisible
     */
    public void setInvisible() {
        visible = false;
    }

    /**
     * Create a panel of times ont he left side
     * @param panel The main panel we want to add to
     * @param c Constraints for our grid
     */
    private static void createTimesPanel(JPanel panel, GridBagConstraints c) {

        List<JButton> result = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            JButton label = new JButton(Integer.toString(i) + ":00");
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
     * Create a panel of weekdays ont he left side
     * @param panel The main panel we want to add to
     * @param c Constraints for our grid
     */
    private static void createWeekdaysPanel(JPanel panel, GridBagConstraints c) {

        List<String> weekdays =
                Arrays.asList(new String[]{"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"});
        List<JButton> result = new ArrayList<>();

        for (int i = 0; i < weekdays.size(); i++) {
            JButton weekdayLabel = new JButton(weekdays.get(i));
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
     * Create an event button
     * @param panel The main panel we want to add to
     * @param c Constraints for our grid
     * @param name Name of event
     * @param weekday Weekday
     * @param start Start time
     * @param end End time
     */
    // TODO: This method assumes event happens on same day, update so it can do an event over multiple dayss
    // TODO: Update this class to use event class. Had trouble importing classes so this was not possible
    private static void createEventButton(JPanel panel, GridBagConstraints c,
                                          String name, String weekday, int start, int end) {
        int weekdayNum = 0;
        if (weekday.equals("Sunday")){
            weekdayNum = 1;
        }
        if (weekday.equals("Monday")){
            weekdayNum = 4;
        }
        if (weekday.equals("Tuesday")){
            weekdayNum = 7;
        }
        if (weekday.equals("Wednesday")){
            weekdayNum = 10;
        }
        if (weekday.equals("Thursday")){
            weekdayNum = 13;
        }
        if (weekday.equals("Friday")){
            weekdayNum = 16;
        }
        if (weekday.equals("Saturday")){
            weekdayNum = 19;
        }

        c.fill = GridBagConstraints.BOTH;
        c.gridx = weekdayNum;
        c.gridy = start * 2 + 2;
        c.gridwidth = 3;
        c.gridheight = (end - start) * 2 + 2;
        JButton result = new JButton(name);
        panel.add(result, c);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
