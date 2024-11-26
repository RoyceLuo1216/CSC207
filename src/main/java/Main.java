import adapter.CohereClient;
import view.ChatbotView;

public class Main {
    public static void main(String[] args) {
        // ChatbotView c = new ChatbotView();
        CohereClient c = new CohereClient();

        String response = c.getTimeForEventConflictWithCohere("I want an event on wednesday 6pm to 9pm.");
        System.out.println("cohere response: " + response);
    }
}