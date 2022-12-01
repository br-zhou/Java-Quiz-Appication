package model;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import persistance.DataHandler;

import java.util.ArrayList;
import java.util.List;

// Functions that UI will call upon to control quizzes
public class AppFunctions {
    private EventLog log;
    private List<Quiz> quizzes;
    private final DataHandler dataHandler;
    private Quiz targetQuiz;
    private Question targetQuestion;


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

    // todo add documentation for these methods
    public Quiz getTargetQuiz() {
        return targetQuiz;
    }

    public void setTargetQuiz(Quiz quiz) {
        logEvent("Select Quiz");
        this.targetQuiz = quiz;
    }

    public void setTargetQuiz(int index) {
        logEvent("Select Quiz");
        this.targetQuiz = quizzes.get(index);
    }


    public void addQuestionToTarget(Question question) {
        logEvent("Add Question");
        targetQuiz.addQuestion(question);
    }

    public void deleteQuestion(Question question) {
        logEvent("Delete Question");
        targetQuiz.deleteQuestion(question);
    }

    public void deleteTargetQuestion() {
        deleteQuestion(targetQuestion);
        targetQuestion = null;
    }

    public void updateQuestion(Question question, String prompt, List<String> answers) {
        logEvent("Updated Question");
        question.setPrompt(prompt);

        if (FreeResponse.class.equals(question.getClass())) {
            FreeResponse freeResponse = (FreeResponse) question;
            freeResponse.setKeywords(answers);

        } else if (MultipleChoice.class.equals(question.getClass())) {
            // pass
        }
    }

    public void setTargetQuestion(int index) {
        targetQuestion = targetQuiz.getQuestions().get(index);
    }

    public void setTargetQuestion(Question question) {
        targetQuestion = question;
    }

    public List<Question> getTargetQuestions() {
        return targetQuiz.getQuestions();
    }


    public Question getTargetQuestion() {
        return targetQuestion;
    }
    // todo //

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
