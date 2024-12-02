package view;

import interface_adapter.schedule.ScheduleController;
import interface_adapter.schedule.ScheduleState;
import interface_adapter.schedule.ScheduleViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * A ScheduleView class to visually display the schedule as a calendar.
 */
public class ScheduleView extends JPanel implements PropertyChangeListener {
    private static final int DIMENSION_10 = 10;
    private static final int DIMENSION_12 = 12;
    private static final int DIMENSION_20 = 20;
    private static final int GRID_ROWS = 24;
    private static final int GRID_COLUMNS = 7;
    private static final int LABEL_FONT_SIZE = 16;
    private static final int CELL_PADDING = 12;
    private static final Dimension FRAME_SIZE = new Dimension(1000, 800);
    private static final int ROW_HEIGHT = 60;
    private static final int WEEKDAY_COLUMN_WIDTH = 150;
    private final String viewName = "schedule";
    private ScheduleController scheduleController;
    private final ScheduleViewModel scheduleViewModel;

    /**
     * Constructs the ScheduleView with a given ScheduleViewModel.
     *
     * @param scheduleViewModel the ViewModel for the schedule
     */
    public ScheduleView(ScheduleViewModel scheduleViewModel) {
        this.scheduleViewModel = scheduleViewModel;
        this.scheduleViewModel.addPropertyChangeListener(this);
        this.setLayout(new GridBagLayout());
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel schedulePanel = new JPanel(new GridBagLayout());
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(schedulePanel);

        // Adjust scroll speed (faster scrolling)
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(DIMENSION_12);
        verticalScrollBar.setBlockIncrement(DIMENSION_20);
        scrollPane.setVerticalScrollBar(verticalScrollBar);

        // Add padding and scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, DIMENSION_20, DIMENSION_20, DIMENSION_10));
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // Add buttons
        addButtons(sidePanel);

        // Add time panel
        addTimePanel(schedulePanel, constraints);

        // Add weekday panel
        addWeekdayPanel(schedulePanel, constraints);

        // Render event buttons
        renderEventButtons(schedulePanel, constraints);

        // Add schedulePanel to the center of mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add sidePanel to the right of mainPanel
        mainPanel.add(sidePanel, BorderLayout.EAST);

        // Add mainPanel to the current view
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addButtons(JPanel sidePanel) {
        // Set the layout to BoxLayout with vertical orientation
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(75, 5, 0, DIMENSION_20));

        // Create buttons for Add Event, Time Estimation Chatbot, and Event Conflict Chatbot
        final JButton addEventButton = new JButton("Add Event");
        final JButton timeEstimationChatbotButton = new JButton("<html>Time Estimation<br>Chatbot</html>");
        final JButton eventConflictChatbotButton = new JButton("<html>Event Conflict<br>Chatbot</html>");

        // Add action listeners to the buttons
        addEventButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        scheduleController.popUpAddEventView();
                    }
                }
        );

        timeEstimationChatbotButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        scheduleController.popUpTimeEstimationChatbotView();
                    }
                }
        );

        eventConflictChatbotButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        scheduleController.popUpEventConflictChatbotView();
                    }
                }
        );

        // Add buttons to the sidePanel with vertical spacing
        sidePanel.add(addEventButton);
        sidePanel.add(Box.createVerticalStrut(DIMENSION_20));
        sidePanel.add(timeEstimationChatbotButton);
        sidePanel.add(Box.createVerticalStrut(DIMENSION_20));
        sidePanel.add(eventConflictChatbotButton);
    }

    private void renderEventButtons(JPanel panel, GridBagConstraints constraints) {
        Map<String, List<Object>> events = scheduleViewModel.getState().getAllEventDetails();

        events.forEach((eventName, details) -> {
            DayOfWeek startDay = (DayOfWeek) details.get(0);
            LocalTime startTime = (LocalTime) details.get(1);
            DayOfWeek endDay = (DayOfWeek) details.get(2);
            LocalTime endTime = (LocalTime) details.get(3);

            // Determine grid position based on event details
            int gridX = startDay.getValue() - 1;
            int startGridY = startTime.getHour();
            int endGridY = endTime.getHour();
            int gridHeight = endGridY - startGridY;

            constraints.gridx = gridX + 2;
            constraints.gridy = startGridY + 1;
            constraints.gridwidth = 1;
            constraints.gridheight = gridHeight;
            constraints.insets = new Insets(CELL_PADDING, 10, CELL_PADDING, CELL_PADDING);

            // Create event button
            JButton eventButton = new JButton(eventName);
            eventButton.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE));
            eventButton.setPreferredSize(new Dimension(100, ROW_HEIGHT));
            eventButton.addActionListener(e -> System.out.println("Event selected: " + eventName));

            // Add the button to the panel
            panel.add(eventButton, constraints);
        });
    }


    private void addTimePanel(JPanel panel, GridBagConstraints constraints) {

        for (int i = 0; i < GRID_ROWS; i++) {
            constraints.gridx = 0;
            constraints.gridy = i + 1;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            constraints.insets = new Insets(0, DIMENSION_10, 0, 0);

            JLabel timeLabel = new JLabel(i + ":00", SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
            timeLabel.setPreferredSize(new Dimension(100, ROW_HEIGHT));
            panel.add(timeLabel, constraints);
        }

    }

    private void addWeekdayPanel(JPanel panel, GridBagConstraints constraints) {

        final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int i = 0; i < GRID_COLUMNS; i++) {
            constraints.gridx = i + 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.insets = new Insets(0, DIMENSION_10, CELL_PADDING, 0);

            JLabel weekdayLabel = new JLabel(weekdays[i], SwingConstants.CENTER);
            weekdayLabel.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
            weekdayLabel.setPreferredSize(new Dimension(100, ROW_HEIGHT));
            panel.add(weekdayLabel, constraints);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            this.removeAll();
            initializeUserInterface();
            this.revalidate();
            this.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setScheduleController(ScheduleController controller) {
        this.scheduleController = controller;
    }
}
