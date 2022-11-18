package model;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.Quiz;
import org.json.JSONObject;
import persistance.DataHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppLogic {
    private List<Quiz> quizzes;
    private DataHandler dataHandler;

    public AppLogic(String filePath) {
        quizzes = new ArrayList<>();
        dataHandler = new DataHandler(filePath);
    }

    // EFFECTS: adds quiz to list of quizzes
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    // EFFECTS: returns true and deletes quizzes from list collection if it exists
    //          returns false is quiz does not exist
    public boolean deleteQuiz(Quiz quiz) {
        return quizzes.remove(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public Quiz getQuiz(int index) {
        return quizzes.get(index);
    }

    /*
     * MODIFIES: the file of given path
     * EFFECTS: updates file at destination with current quizzes data
     *          throws WriteErrorException if unable to update data
     */
    public void updateData(List<Quiz> quizzes) throws WriteErrorException {
        dataHandler.updateData(quizzes);
    }

    /*
     * EFFECTS: returns quizzes data from storage,
     *          returns empty list if file doesn't exist
     *          throws ReadErrorException if unable to read file
     *          throws CorruptDataException if file is formatted incorrectly
     */
    public List<Quiz> retrieveData() throws ReadErrorException {
        return dataHandler.retrieveData();
    }

}
