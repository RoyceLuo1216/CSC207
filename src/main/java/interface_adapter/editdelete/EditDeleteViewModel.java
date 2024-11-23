package interface_adapter.editdelete;

import java.time.LocalDateTime;

/**
 * The EditDeleteViewModel handles logic for the EditDeleteView.
 */
public class EditDeleteViewModel {
    private final EditDeleteController controller;
    private final EditDeletePresenter presenter;

    public EditDeleteViewModel(EditDeleteController controller, EditDeletePresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    public EditDeleteState getEditDeleteState() {
        return presenter.presentEvent(controller.getCurrentEvent());
    }

    public boolean updateEvent(String name, String startTime, String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return controller.updateEvent(name, start, end);
    }

    public boolean deleteEvent() {
        return controller.deleteEvent();
    }
}
