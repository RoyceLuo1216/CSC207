import adapter.CohereClient;
import usecase.chatbot_event_conflict.EventConflictInteractor;
import view.ChatbotView;
import view.EventConflictController;

import java.time.LocalDateTime;

public class Main {
    // TODO: move this into main / app builder from chatbot view
//    final EventConflictOutputBoundary eventConflictOutputBoundary = new EventConflictPresenter(viewManagerModel,
//            signupViewModel, loginViewModel);
//    final EventConflictInputBoundary eventConflictInteractor = new EventConflictInteractor(eventConflictOutputBoundary);
//
//    final EventConflictController controller = new EventConflictController(eventConflictInteractor);
//    eventConflictController = new EventConflictController(controller);
    // TODO: end
    public void eventConflictInteractorTest() {
        LocalDateTime start = LocalDateTime.of(2024, 8, 28, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 8, 28, 21, 0);

        EventConflictInteractor i = new EventConflictInteractor();
        i.getTasksDuring(start, end);
    }

    public static void main(String[] args) {
        ChatbotView c = new ChatbotView();
//        CohereClient c = new CohereClient();
//
//        String response = c.getTimeForEventConflictWithCohere("I want an event on wednesday 6pm to 9pm.");
//        System.out.println("cohere response: " + response);

        // Main m = new Main();
        // m.eventConflictInteractorTest();

    }
}