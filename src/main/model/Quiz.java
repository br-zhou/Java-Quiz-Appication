package model;

import model.questions.*;
import ui.ConsoleInput;

import java.util.ArrayList;

public class Quiz {
    String name;
    ArrayList<Question> questions;

    // Requires: name must not be empty
    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public Result start(ConsoleInput input) {
        Result result = new Result(questions.size());
        for (Question question : questions) {
            if (question.attempt(input)) {
                result.incrementScore();
            }
        }

        return result;
    }
}
