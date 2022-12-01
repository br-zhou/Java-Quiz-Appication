package model.questions;

import ui.InputOutput;
import org.json.JSONObject;
import persistance.Writable;

import java.util.List;

// represents a question, with a prompt and a variable holding whether the question was answered correctly
public abstract class Question implements Writable {
    private String prompt;
    private final String type;
    protected boolean correct;

    /*
     * REQUIRES: prompt cannot be an empty string
     * EFFECTS: creates a new question with the given prompt
     */
    public Question(String prompt, String type) {
        this.prompt = prompt;
        this.type = type;
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
        result.put("type", type);
        return result;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getType() {
        return type;
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

    /*
     * MODIFIES: this
     */
    public void setPrompt(String str) {
        this.prompt = str;
    }
}
