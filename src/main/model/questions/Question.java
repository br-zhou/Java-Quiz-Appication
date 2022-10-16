package model.questions;

import model.InputOutput;

// represents a question, with a prompt and a variable holding whether the question was answered correctly
public abstract class Question {
    private final String prompt;
    protected boolean correct;

    /*
     * REQUIRES: prompt cannot be an empty string
     * EFFECTS: creates a new question with the given prompt
     */
    public Question(String prompt) {
        this.prompt = prompt;
        this.correct = false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if user input is correct, 'isCorrect' method now output true.
     *          Otherwise, 'isCorrect' method outputs false
     */
    public abstract void attempt(InputOutput inputOutput);

    /*
     * EFFECTS: returns the question prompt and all relevant information
     */
    @Override
    public String toString() {
        return prompt;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getPrompt() {
        return prompt;
    }

    /*
     * MODIFIES: this
     */
    public void setCorrect(boolean value) {
        this.correct = value;
    }
}
