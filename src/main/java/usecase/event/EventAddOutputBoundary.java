package usecase.event;

/**
 * The output boundary for the Edit use case.
 */
<<<<<<<< HEAD:src/main/java/usecase/event/AddEventOutputBoundary.java
public interface AddEventOutputBoundary {
========
public interface EventAddOutputBoundary {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddOutputBoundary.java
    /**
     * Prepares the success view for the Edit use case.
     * @param outputData the output data
     */
<<<<<<<< HEAD:src/main/java/usecase/event/AddEventOutputBoundary.java
    void prepareSuccessView(AddEventOutputData outputData);
========
    void prepareSuccessView(EventAddOutputData outputData);
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddOutputBoundary.java

    /**
     * Prepares the failure view for the Edit use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Returns the back to main schedule view.
     */
    void backToMainView();
}
