package view;

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
        mainFrame.setSize(800, 600);

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
        final JPanel timePanel = new JPanel(new GridLayout(24, 1));
        for (int i = 0; i < 24; i++) {
            timePanel.add(new JLabel(i + ":00"));
        }
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 24;
        mainPanel.add(timePanel, constraints);
    }

    private void addWeekdayPanel(GridBagConstraints constraints) {
        final JPanel weekdayPanel = new JPanel(new GridLayout(1, 7));
        final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String weekday : weekdays) {
            weekdayPanel.add(new JLabel(weekday, SwingConstants.CENTER));
        }

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 7;
        constraints.gridheight = 1;
        mainPanel.add(weekdayPanel, constraints);
    }
}
