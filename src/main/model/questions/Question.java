package model.questions;

import model.InputOutput;

public abstract class Question {
    private final String prompt;

    public Question(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract boolean attempt(InputOutput inputOutput);

    @Override
    public String toString() {
        return prompt;
    }
}
