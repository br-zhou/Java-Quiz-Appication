package model;

import model.questions.*;
import java.util.List;

public class Quiz {
    private final String name;
    private final List<Question> questions;

    // Requires: name must not be empty
    public Quiz(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public Result start(Input input) {
        Result result = new Result(questions.size());
        for (Question question : questions) {
            input.displayQuestion(question.toString());
            if (question.attempt(input)) {
                result.incrementScore();
            }
        }

        return result;
    }
}
