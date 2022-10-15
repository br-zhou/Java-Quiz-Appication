package model.questions;

import model.Input;
import java.util.List;

public class FreeResponse extends Question {
    private List<String> keywords;

    // REQUIRES: 2 < keywords size < 5, with the correct choice being the first element
    // EFFECTS: creates a new question
    public FreeResponse(String prompt, List<String> keywords) {
        super(prompt);
        this.keywords = keywords;
    }

    /*
     * EFFECTS: returns true if input contains specific keywords TODO ass description
     */
    @Override
    public boolean attempt(Input input) {
        System.out.println(this);

        String response = input.getString();

        for (String keyword : keywords) {
            if (!response.contains(keyword)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append("\nPlease type your answer below.");
        return result.toString();
    }
}