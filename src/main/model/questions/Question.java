package model.questions;

import ui.ConsoleInput;

public abstract class Question {
    private final String prompt;

    Question(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract boolean attempt(ConsoleInput input);

    @Override
    public String toString() {
        return prompt;
    }
}
