package model;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import persistance.DataHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    // EFFECTS: returns target quiz;
    public Quiz getTargetQuiz() {
        return targetQuiz;
    }

    // REQUIRES: quizzes must contain given quiz
    // MODIFIES: this
    // EFFECTS: sets targeted quiz to given quiz
    public void setTargetQuiz(Quiz quiz) {
        this.targetQuiz = quiz;
    }

    // REQUIRES: 0 <= index < quizzes.size()
    // MODIFIES: this
    // EFFECTS: sets targeted quiz to quiz at given index
    public void setTargetQuiz(int index) {
        setTargetQuiz(quizzes.get(index));
    }

    // REQUIRES: question must not already be in target
    // MODIFIES: this
    // EFFECTS: adds question to list of questions in target quiz
    public void addQuestionToTarget(Question question) {
        logEvent("Added new question");
        targetQuiz.addQuestion(question);
    }

    // REQUIRES: targetQuiz must contain given quiz
    // MODIFIES: this
    // EFFECTS: deletes question from target quiz
    public void deleteQuestion(Question question) {
        logEvent("Deleted a question");
        targetQuiz.deleteQuestion(question);
    }

    // REQUIRES: targetQuiz's questions must contain targetQuestion
    // MODIFIES: this
    // EFFECTS: deletes question from targetQuiz
    public void deleteTargetQuestion() {
        deleteQuestion(targetQuestion);
        targetQuestion = null;
    }

    // REQUIRES: 0 <= index < targetQuiz.getQuestions.size()
    // MODIFIES: this
    // EFFECTS: sets targeted question to question at given index of targetQuiz's questions
    public void setTargetQuestion(int index) {
        targetQuestion = targetQuiz.getQuestions().get(index);
    }

    // REQUIRES: targetQuiz's questions must contain targetQuestion
    // MODIFIES: this
    // EFFECTS: sets targetQuestion to given question
    public void setTargetQuestion(Question question) {
        targetQuestion = question;
    }

    // REQUIRES: targetQuiz's questions must contain targetQuestion
    // MODIFIES: this
    // EFFECTS: deletes question from targetQuiz
    public List<Question> getTargetQuestions() {
        return targetQuiz.getQuestions();
    }


    // EFFECTS: returns targeted question
    public Question getTargetQuestion() {
        return targetQuestion;
    }

    // EFFECTS: returns event iterator
    public Iterator<Event> getEventIterator() {
        return log.iterator();
    }

    /*
     * EFFECTS: adds new event to log with description of given message
     */
    private void logEvent(String message) {
        log.logEvent(new Event(message));
    }

}
