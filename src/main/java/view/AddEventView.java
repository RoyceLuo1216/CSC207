package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.addEvent.AddEventController;
import interface_adapter.addEvent.AddEventState;
import interface_adapter.addEvent.AddEventViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;

import javax.swing.*;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class AddEventView extends JPanel {
    // Initialise the controller
    private AddEventController addEventController;

    private final AddEventViewModel addEventViewModel;

    private static final int DIMENSION_500 = 500;

    private final String viewName = "add";

    // Setup Components
    private final String[] eventTypes = {"Fixed", "Flexible", "Repeat"};
    private final String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] times = {"12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM",
            "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM",
            "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM",
            "9:00 PM", "10:00 PM", "11:00 PM"};
    private final String[] priorities = {"1", "2", "3", "4", "5"};

    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);

    private final JLabel saveLabel = new JLabel();
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    // Data

    public AddEventView(AddEventViewModel addEventViewModel) {
        this.addEventViewModel = addEventViewModel;

        final JLabel title = new JLabel("Create Event Page");

        // Create the fixed frame (main)
//        final JFrame eventFrame = new JFrame("Create Event Page");
//        eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        eventFrame.setSize(DIMENSION_500, DIMENSION_500);
//        eventFrame.setLayout(new BoxLayout(eventFrame.getContentPane(), BoxLayout.Y_AXIS));
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // LAYOUT
        final JPanel eventNamePanel = createPanel("Event Name:", eventNameField);
        final JPanel eventTypePanel = createPanel("Event Type:", eventTypeComboBox);
        final JPanel dayStartPanel = createPanel("Day Start:", dayStartComboBox);
        final JPanel dayEndPanel = createPanel("Day End:", dayEndComboBox);
        final JPanel timeStartPanel = createPanel("Time Start:", timeStartComboBox);
        final JPanel timeEndPanel = createPanel("Time End:", timeEndComboBox);

        // Save Button
        final JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.add(saveLabel);

        // Cancel Button
        final JPanel cancelPanel = new JPanel();
        savePanel.add(cancelButton);

        // Add panels to frame
        mainPanel.add(eventNamePanel);
        mainPanel.add(eventTypePanel);
        mainPanel.add(dayStartPanel);
        mainPanel.add(dayEndPanel);
        mainPanel.add(timeStartPanel);
        mainPanel.add(timeEndPanel);
        mainPanel.add(savePanel);
        mainPanel.add(cancelPanel);

        // Display the frame
        // eventFrame.setVisible(true);
        this.add(mainPanel);

        // ActionListener for save button
        saveButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(saveButton)) {
                        final AddEventState currentState = addEventViewModel.getState();

                        addEventController.execute(currentState.getEventName(), currentState.getDayStart(),
                                currentState.getDayEnd(), currentState.getTimeStart(), currentState.getTimeEnd());
                    }
                }
        );

        // ActionListener for cancel button
        cancelButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(cancelButton)) {
                        addEventController.backToScheduleView();
                    }
                }
        );
    }

    private JPanel createPanel(String label, JComponent component) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    public String getViewName() {
        return viewName;
    }

    public void setAddEventController(AddEventController addEventController) {
        this.addEventController = addEventController;
    }

}
