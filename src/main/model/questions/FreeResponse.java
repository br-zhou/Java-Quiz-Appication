package model.questions;

import model.InputOutput;
import java.util.List;

public class FreeResponse extends Question {
    private final List<String> keywords;

    /*
     * REQUIRES: 2 <= keywords size <= 5
     * EFFECTS: question prompt is set to prompt; required keywords is set to keywords
     */
    public FreeResponse(String prompt, List<String> keywords) {
        super(prompt);
        this.keywords = keywords;
    }

    /*
     * EFFECTS: returns true if input contains required keywords
     */
    @Override
    public void attempt(InputOutput inputOutput) {
        String response = inputOutput.getString();

        correct = true;
        for (String keyword : keywords) {
            if (!response.contains(keyword)) {
                correct = false;
                break;
            }
        }
    }

    @Override
    public String toString() {
        String result = super.toString();
        result += "\nPlease type your answer below.";
        return result;
    }
}