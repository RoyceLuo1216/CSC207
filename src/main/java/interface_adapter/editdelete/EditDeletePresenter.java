package interface_adapter.editdelete;

import entities.EventEntity.Event;

/**
 * The EditDeletePresenter formats event data for the EditDeleteView.
 */
public class EditDeletePresenter {
    public EditDeleteState presentEvent(Event event) {
        return new EditDeleteState(event.getEventName(), event.getDayStart().toString(), event.getDayEnd().toString());
    }
}
