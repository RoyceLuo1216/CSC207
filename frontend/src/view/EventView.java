package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class EventView extends JPanel {
    // Data
    private final EventViewData eventData = new EventViewData();

    // Constants
    private final String[] eventTypes = {"Fixed", "Flexible", "Repeat"};
    private final String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] times = {"12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM",
                                    "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM",
                                    "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM",
                                    "9:00 PM", "10:00 PM", "11:00 PM"};
    private final String[] priorities = {"1", "2", "3", "4", "5"};

    // Fillable Components
    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);
    private final JComboBox eventPriorityComboBox = new JComboBox<>(priorities);
    private final JLabel saveLabel = new JLabel();
    private final JButton saveButton = new JButton("Save");

    // Data

    public EventView() {
        // LAYOUT
        // Create the fixed frame (main)
        final JFrame fixedFrame = new JFrame("Create Event Page");
        fixedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fixedFrame.setSize(500, 500);

        // Set BoxLayout for vertical stacking
        fixedFrame.setLayout(new BoxLayout(fixedFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Event Name
        final JPanel eventNamePanel = new JPanel();
        eventNamePanel.add(new JLabel("Event Name:"));
        eventNamePanel.add(eventNameField);

        // Event Type
        final JPanel eventTypePanel = new JPanel();
        eventTypePanel.add(new JLabel("Event Type:"));
        eventTypePanel.add(eventTypeComboBox);

        // Day Start
        final JPanel dayStartPanel = new JPanel();
        dayStartPanel.add(new JLabel("Day Start:"));
        dayStartPanel.add(dayStartComboBox);

        // Day End
        final JPanel dayEndPanel = new JPanel();
        dayEndPanel.add(new JLabel("Day End:"));
        dayEndPanel.add(dayEndComboBox);

        // Time Start
        final JPanel timeStartPanel = new JPanel();
        timeStartPanel.add(new JLabel("Time Start:"));
        timeStartPanel.add(timeStartComboBox);

        // Time End
        final JPanel timeEndPanel = new JPanel();
        timeEndPanel.add(new JLabel("Time End:"));
        timeEndPanel.add(timeEndComboBox);

        // Event Priority
        final JPanel eventPriorityPanel = new JPanel();
        eventPriorityPanel.add(new JLabel("Event Priority:"));
        eventPriorityPanel.add(eventPriorityComboBox);

        // Save Button
        final JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.add(saveLabel);

        // Add panels to frame
        fixedFrame.add(eventNamePanel);
        fixedFrame.add(eventTypePanel);
        fixedFrame.add(dayStartPanel);
        fixedFrame.add(dayEndPanel);
        fixedFrame.add(timeStartPanel);
        fixedFrame.add(timeEndPanel);
        fixedFrame.add(eventPriorityPanel);
        fixedFrame.add(savePanel);

        // Display the frame
        fixedFrame.setVisible(true);

        // LISTENERS
        // DocumentListener for eventNameField
        eventNameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                eventData.setEventName(eventNameField.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // ActionListener for save button
        saveButton.addActionListener(
            // This creates an anonymous subclass of ActionListener and instantiates it.
            evt -> {
                if (evt.getSource().equals(saveButton)) {

                    eventData.setEventName(eventNameField.getText());
                    eventData.setEventType((String) eventTypeComboBox.getSelectedItem());
                    eventData.setDayStart((String) dayStartComboBox.getSelectedItem());
                    eventData.setDayEnd((String) dayEndComboBox.getSelectedItem());
                    eventData.setTimeStart((String) timeStartComboBox.getSelectedItem());
                    eventData.setTimeEnd((String) timeEndComboBox.getSelectedItem());
                    eventData.setEventPriority((String) eventPriorityComboBox.getSelectedItem());

                    eventData.getAll();
                    saveLabel.setText("Saved!");
                }
            }
        );
    }
}
