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
        final Map<String, ScheduleState.EventDetails> events = scheduleViewModel.getState().getAllEventDetails();

        for (Map.Entry<String, ScheduleState.EventDetails> entry : events.entrySet()) {
            String eventName = entry.getKey();
            ScheduleState.EventDetails details = entry.getValue();

            // Determine grid position based on event details
            int startGridY = details.getStartTime().getHour();
            int endGridY = details.getEndTime().getHour();
            int gridX = details.getStartDay().getValue() - 1;

            // Adjust for 10-pixel left shift
            constraints.gridx = gridX + 1;
            constraints.gridy = startGridY;
            constraints.gridwidth = 1;
            constraints.gridheight = endGridY - startGridY;
            constraints.insets = new Insets(CELL_PADDING, 10, CELL_PADDING, CELL_PADDING);

            // Create event button
            JButton eventButton = new JButton(eventName);
            eventButton.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE));
            eventButton.addActionListener(e -> System.out.println("Event selected: " + eventName));
            panel.add(eventButton, constraints);
        }
    }

    private void addTimePanel(JPanel panel, GridBagConstraints constraints) {
        final JPanel timePanel = new JPanel(new GridLayout(GRID_ROWS, 1, 0, CELL_PADDING));
        timePanel.setPreferredSize(new Dimension(100, ROW_HEIGHT * GRID_ROWS));
        for (int i = 0; i < GRID_ROWS; i++) {
            JLabel timeLabel = new JLabel(i + ":00", SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
            timeLabel.setPreferredSize(new Dimension(100, ROW_HEIGHT));
            timePanel.add(timeLabel);
        }
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = GRID_ROWS;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, DIMENSION_10, 0, 0);
        panel.add(timePanel, constraints);
    }

    private void addWeekdayPanel(JPanel panel, GridBagConstraints constraints) {
        final JPanel weekdayPanel = new JPanel(new GridLayout(1, GRID_COLUMNS, CELL_PADDING, 0));
        weekdayPanel.setPreferredSize(new Dimension(WEEKDAY_COLUMN_WIDTH * GRID_COLUMNS, ROW_HEIGHT));
        final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String weekday : weekdays) {
            JLabel weekdayLabel = new JLabel(weekday, SwingConstants.CENTER);
            weekdayLabel.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
            weekdayPanel.add(weekdayLabel);
        }

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GRID_COLUMNS;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, DIMENSION_10, CELL_PADDING, 0);
        panel.add(weekdayPanel, constraints);
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
        return "schedule";
    }

    public void setScheduleController(ScheduleController controller) {
        this.scheduleController = controller;
    }
}
