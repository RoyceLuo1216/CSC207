//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    ScheduleView schedulePanel;

    public static void main(String[] args) {
        ScheduleView schedulePanel = new ScheduleView();
        schedulePanel.setVisible();
        schedulePanel.displaySchedule();
    }
}