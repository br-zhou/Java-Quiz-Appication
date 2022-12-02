package model.questions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

// Represents a free response question, with the required keywords to make response correct
public class FreeResponse extends Question {
    private List<String> keywords;

    /*
     * REQUIRES: 1 <= keywords size <= 10
     * EFFECTS: question prompt is set to prompt; required keywords is set to keywords
     */
    public FreeResponse(String prompt, List<String> keywords) {
        super(prompt, "FreeResponse");
        this.keywords = keywords;
    }

    /*
     * EFFECTS: returns true if input contains all required keywords
     */
    @Override
    public boolean attempt(String input) { // todo io.getString
        correct = true;
        for (String keyword : keywords) {
            if (!input.contains(keyword)) {
                correct = false;
                break;
            }
        }

        return correct;
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

    /*
     * EFFECTS: Returns keywords list in JsonArray format
     */
    private JSONArray keywordsToJson() {
        JSONArray result = new JSONArray();
        for (String keyword : keywords) {
            result.put(keyword);
        }
        return result;
    }

    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    public void setKeywords(List<String> newKeywords) {
        keywords = newKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        FreeResponse freeResponse = (FreeResponse) o;

        if (!this.getPrompt().equals(freeResponse.getPrompt())) {
            return false;
        }

        if (!keywords.equals(freeResponse.getKeywords())) {
            return false;
        }

        return true;
    }
}