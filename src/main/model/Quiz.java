package model;

import model.questions.*;
import java.util.List;

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

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /*
     * EFFECTS: does quiz and returns result
     */
    public Result start(InputOutput inputOutput) {
        Result result = new Result(questions.size());
        for (Question question : questions) {
            inputOutput.displayQuestion(question);
            if (question.attempt(inputOutput)) {
                result.incrementScore();
            }
        }

        return result;
    }
}
