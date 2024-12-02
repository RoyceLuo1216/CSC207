package view;

import interface_adapter.schedule.ScheduleController;
import interface_adapter.schedule.ScheduleState;
import interface_adapter.schedule.ScheduleViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A ScheduleView class to visually display the schedule.
 */
public class ScheduleView extends JPanel implements PropertyChangeListener {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int GRID_ROWS = 24;
    private static final int GRID_COLUMNS = 7;
    private static final int LABEL_FONT_SIZE = 14;

    private final ScheduleViewModel scheduleViewModel;
    private final JFrame mainFrame;
    private final JPanel mainPanel;

    /**
     * Constructs the ScheduleView with a given ScheduleViewModel.
     *
     * @param scheduleViewModel the ViewModel for the schedule
     */
    public ScheduleView(ScheduleViewModel scheduleViewModel) {
        this.scheduleViewModel = scheduleViewModel;
        this.scheduleViewModel.addPropertyChangeListener(this);

        this.mainFrame = new JFrame("Schedule Viewer");
        this.mainPanel = new JPanel(new GridBagLayout());
    }

    /**
     * Initializes and displays the schedule view.
     */
    public void displaySchedule() {
        // Set up frame
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Constraints for GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // Add time and weekday panels
        addTimePanel(constraints);
        addWeekdayPanel(constraints);

        // Render events as buttons
        renderEventButtons();

        // Make the frame visible
        mainFrame.setVisible(true);
    }

    private void renderEventButtons() {
        final ScheduleState scheduleState = scheduleViewModel.getState();
        scheduleState.getEventButtonMap().forEach((buttonId, eventName) -> {
            final JButton eventButton = new JButton(eventName);
            eventButton.addActionListener(e -> handleEventButtonPress(buttonId));
            mainPanel.add(eventButton);
        });
    }

    private void handleEventButtonPress(String buttonId) {
        final String eventName = scheduleViewModel.getState().getEventName(buttonId);
        System.out.println("Event selected: " + eventName);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            mainPanel.removeAll();
            renderEventButtons();
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    private void addTimePanel(GridBagConstraints constraints) {
        final JPanel timePanel = new JPanel(new GridLayout(GRID_ROWS, 1));
        for (int i = 0; i < GRID_ROWS; i++) {
            JLabel timeLabel = new JLabel(i + ":00");
            timeLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE));
            timePanel.add(timeLabel);
        }
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = GRID_ROWS;
        mainPanel.add(timePanel, constraints);
    }

    private void addWeekdayPanel(GridBagConstraints constraints) {
        final JPanel weekdayPanel = new JPanel(new GridLayout(1, GRID_COLUMNS));
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
        mainPanel.add(weekdayPanel, constraints);
    }

    public String getViewName() {
        String viewName = "schedule";
        return viewName;
    }

    public void setScheduleController(ScheduleController controller) {
    }
}
