package model.questions;

import model.InputOutput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Represents a free response question, with the required keywords to make response correct
public class FreeResponse extends Question {
    private final List<String> keywords;

    /*
     * REQUIRES: 1 <= keywords size <= 10
     * EFFECTS: question prompt is set to prompt; required keywords is set to keywords
     */
    public FreeResponse(String prompt, List<String> keywords) {
        super(prompt);
        this.keywords = keywords;
    }

    /*
     * EFFECTS: returns true if input contains all required keywords
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

    @Override
    public JSONObject toJson() {
        JSONObject result = super.toJson();

        result.put("keywords", keywordsToJson());

        return result;
    }

    private JSONArray keywordsToJson() {
        JSONArray result = new JSONArray();
        for (String keyword : keywords) {
            result.put(keyword);
        }
        return result;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}