package usecase.add;

/**
 * OutputData for add event.
 */
public class AddEventOutputData {
    private final String message;

    public AddEventOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
