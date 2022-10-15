package model.questions;

import model.InputOutput;

public abstract class Question {
    private final String prompt;
    protected boolean correct;

    public Question(String prompt) {
        this.prompt = prompt;
        this.correct = false;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setCorrect(boolean value) {
        this.correct = value;
    }

    public abstract void attempt(InputOutput inputOutput);

    @Override
    public String toString() {
        return prompt;
    }

    public boolean isCorrect() {
        return correct;
    }
}
