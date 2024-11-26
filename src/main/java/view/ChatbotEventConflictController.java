package view;

/**
 * The Controller for the Chatbot Event Conflict Use Case.
 */
public class ChatbotEventConflictController {
    private String question;

    /**
     * Method that takes user inputted question and assigns each to a corresponding instance variable.
     * @param question  user asked
     */
    public void askQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    /**
     * Prints the user inputted question.
     */
    public void printQuestion(){
        System.out.println("Question: " + getQuestion());
    }
}
