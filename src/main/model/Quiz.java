package model;

import model.questions.*;
import java.util.List;

// represents a quiz with a quiz name and a list of questions
public class Quiz {
    private final String name;
    private final List<Question> questions;

    /*
     * REQUIRES: questions list cannot be empty
     * EFFECTS: creates a new quiz with the given name and questions
     */
    public Quiz(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    /*
     * EFFECTS: returns the amount of questions answered correctly
     */
    public Result getResult() {
        Result result = new Result(questions.size());

        for (Question question : questions) {
            if (question.isCorrect()) {
                result.incrementScore();
            }
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
