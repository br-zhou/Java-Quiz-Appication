package model;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import persistance.DataHandler;

import java.util.ArrayList;
import java.util.List;

// Functions that UI will call upon to control quizzes
public class AppFunctions {
    private EventLog log;
    private List<Quiz> quizzes;
    private final DataHandler dataHandler;

    // REQUIRES: filePath must be a valid path relative to root directory
    // EFFECTS: Initializes app functions at file path
    public AppFunctions(String filePath) {
        quizzes = new ArrayList<>();
        dataHandler = new DataHandler(filePath);
        log = EventLog.getInstance();
    }

    // EFFECTS: adds quiz to list of quizzes
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        logEvent("New Quiz Added!");
    }

    // EFFECTS: returns true and deletes quizzes from list collection if it exists
    //          returns false is quiz does not exist
    public boolean deleteQuiz(Quiz quiz) {
        logEvent("Quiz Deleted!");
        return quizzes.remove(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Quiz getQuizAtIndex(int index) {
        return quizzes.get(index);
    }

    /*
     * MODIFIES: the file of given path
     * EFFECTS: updates file at destination with current quizzes data
     *          throws WriteErrorException if unable to update data
     */
    public void pushDataToStorage() throws WriteErrorException {
        dataHandler.updateData(quizzes);
    }

    /*
     * EFFECTS: returns quizzes data from storage,
     *          returns empty list if file doesn't exist
     *          throws ReadErrorException if unable to read file
     *          throws CorruptDataException if file is formatted incorrectly
     */
    public void pullDataFromStorage() throws ReadErrorException {
        quizzes = dataHandler.retrieveData();
    }

    /*
     * EFFECTS: Prints every event in log to console
     */
    public void printLog() {
        System.out.println("Event Log: ");
        for (Event event : log) {
            System.out.println(event);
        }
    }

    /*
     * EFFECTS: adds new event to log with description of given message
     */
    private void logEvent(String message) {
        log.logEvent(new Event(message));
    }

}
