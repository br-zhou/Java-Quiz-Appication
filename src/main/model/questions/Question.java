package model.questions;

import model.Input;
import ui.ConsoleInput;

import java.util.List;

public abstract class Question {
    private final String prompt;

    Question(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract boolean attempt(Input input);

    @Override
    public String toString() {
        return prompt;
    }
}
