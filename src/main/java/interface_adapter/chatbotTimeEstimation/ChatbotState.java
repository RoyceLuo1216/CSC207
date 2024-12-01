package interface_adapter.chatbotTimeEstimation;

/**
 * The state for the Chatbot View Model.
 */
public class ChatbotState {
    private String question = "";
    private String questionError;
    private String response = "";
    private String responseError;

    public String getQuestion() {
        return question;
    }

    public String getResponse() { return response; }

    public String getQuestionError() {
        return questionError;
    }

    public String getResponseError() { return responseError; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setResponse(String response) { this.response = response; }

    public void setQuestionError(String questionError) {
        this.questionError = questionError;
    }

    public void setResponseError(String responseError) { this.responseError = responseError; }

    @Override
    public String toString() {
        return "ChatbotState{"
                + "question='" + question + '\''
                + ", response='" + response + '\''
                + '}';
    }
}

