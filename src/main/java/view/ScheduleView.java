package view;

import interface_adapter.schedule.ScheduleController;
import interface_adapter.schedule.ScheduleState;
import interface_adapter.schedule.ScheduleViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * A ScheduleView class to visually display the schedule as a calendar.
 */
public class ScheduleView extends JPanel implements PropertyChangeListener {

    private static final int GRID_ROWS = 24;
    private static final int GRID_COLUMNS = 7;
    private static final int LABEL_FONT_SIZE = 16;
    private static final int CELL_PADDING = 12;
    private static final String FONT = "Arial";
    private static final int TIME_COLUMN_WIDTH = 100;
    private static final int ROW_HEIGHT = 60;
    private static final int WEEKDAY_COLUMN_WIDTH = 150;
    private static final Dimension PANEL_PADDING = new Dimension(10, 20);

    private final ScheduleViewModel scheduleViewModel;
    private ScheduleController scheduleController;

    /**
     * Constructs the ScheduleView with a given ScheduleViewModel.
     *
     * @param scheduleViewModel the ViewModel for the schedule
     */
    public ScheduleView(final ScheduleViewModel scheduleViewModel) {
        this.scheduleViewModel = scheduleViewModel;
        this.scheduleViewModel.addPropertyChangeListener(this);
        this.setLayout(new GridBagLayout());
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        final JScrollPane scrollPane = new JScrollPane(mainPanel);

        scrollPane.setBorder(BorderFactory.createEmptyBorder(PANEL_PADDING.width, PANEL_PADDING.height,
                PANEL_PADDING.height, PANEL_PADDING.width));
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        addTimePanel(mainPanel, constraints);
        addWeekdayPanel(mainPanel, constraints);
        renderEventButtons(mainPanel, constraints);
    }

    private void renderEventButtons(final JPanel panel, final GridBagConstraints constraints) {
        final Map<String, ScheduleState.EventDetails> events = scheduleViewModel.getState().getAllEventDetails();

        for (Map.Entry<String, ScheduleState.EventDetails> entry : events.entrySet()) {
            final String eventName = entry.getKey();
            final ScheduleState.EventDetails details = entry.getValue();

            final int startGridY = details.getStartTime().getHour();
            final int endGridY = details.getEndTime().getHour();
            final int gridX = details.getStartDay().getValue() - 1;

            constraints.gridx = gridX + 1;
            constraints.gridy = startGridY;
            constraints.gridwidth = 1;
            constraints.gridheight = endGridY - startGridY;
            constraints.insets = new Insets(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING);

            final JButton eventButton = new JButton(eventName);
            eventButton.setFont(new Font(FONT, Font.PLAIN, LABEL_FONT_SIZE));
            eventButton.addActionListener(e -> System.out.println("Event selected: " + eventName));
            panel.add(eventButton, constraints);
        }
    }

    private void addTimePanel(final JPanel panel, final GridBagConstraints constraints) {
        final JPanel timePanel = new JPanel(new GridLayout(GRID_ROWS, 1, 0, CELL_PADDING));
        timePanel.setPreferredSize(new Dimension(TIME_COLUMN_WIDTH, ROW_HEIGHT * GRID_ROWS));
        for (int i = 0; i < GRID_ROWS; i++) {
            final JLabel timeLabel = new JLabel(i + ":00", SwingConstants.CENTER);
            timeLabel.setFont(new Font(FONT, Font.BOLD, LABEL_FONT_SIZE));
            timeLabel.setPreferredSize(new Dimension(TIME_COLUMN_WIDTH, ROW_HEIGHT));
            timePanel.add(timeLabel);
        }
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = GRID_ROWS;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(0, PANEL_PADDING.width, 0, 0);
        panel.add(timePanel, constraints);
    }

    private void addWeekdayPanel(final JPanel panel, final GridBagConstraints constraints) {
        final JPanel weekdayPanel = new JPanel(new GridLayout(1, GRID_COLUMNS, CELL_PADDING, 0));
        weekdayPanel.setPreferredSize(new Dimension(WEEKDAY_COLUMN_WIDTH * GRID_COLUMNS, ROW_HEIGHT));
        final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (final String weekday : weekdays) {
            final JLabel weekdayLabel = new JLabel(weekday, SwingConstants.CENTER);
            weekdayLabel.setFont(new Font(FONT, Font.BOLD, LABEL_FONT_SIZE));
            weekdayPanel.add(weekdayLabel);
        }

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GRID_COLUMNS;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, PANEL_PADDING.width, CELL_PADDING, 0);
        panel.add(weekdayPanel, constraints);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
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

    public void setScheduleController(final ScheduleController controller) {
        this.scheduleController = controller;
    }
}
