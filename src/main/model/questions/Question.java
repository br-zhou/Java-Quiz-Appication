package model.questions;

import model.InputOutput;
import org.json.JSONObject;
import persistance.Writable;

// represents a question, with a prompt and a variable holding whether the question was answered correctly
public abstract class Question implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("prompt", prompt);
        result.put("correct", false);
        return result;
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
