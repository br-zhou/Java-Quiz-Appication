package model.questions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

// represents a multiple choice question, with choices and a correct answer
public class MultipleChoice extends Question {
    private List<String> choices;
    private String correctChoice;

    /*
     * REQUIRES: 2 <= choices size <= 5, with the correct choice being the first element
     * EFFECTS: creates a new multiple choice question with prompt and choices set to given arguments
     */
    public MultipleChoice(String prompt, List<String> choices) {
        super(prompt, "MultipleChoice");
        this.choices = choices;
        this.correctChoice = choices.get(0);
    }

    /*
     * EFFECTS: sets isCorrect to true if user inputs correct choice
     */
    @Override
    public boolean attempt(String input) { // todo inputOutput.getItemFromList(choices);
        correct = input.equals(correctChoice);
        return correct;
    }

    /*
     * EFFECTS: returns choices alongside question prompt
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());

        Collections.shuffle(choices);

        for (int i = 0; i < choices.size(); i++) {
            result.append(String.format("\n %s) %s", i + 1, choices.get(i)));
        }

        return result.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = super.toJson();
        result.put("correctChoice", correctChoice);
        result.put("choices", choicesToJson());

        return result;
    }

    /*
     * EFFECTS: returns choices list in JsonArray format
     */
    private JSONArray choicesToJson() {
        JSONArray result = new JSONArray();
        for (String choice : choices) {
            result.put(choice);
        }
        return result;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
        this.correctChoice = choices.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        MultipleChoice multipleChoice = (MultipleChoice) o;

        if (!this.getPrompt().equals(multipleChoice.getPrompt())
                || !this.getType().equals(multipleChoice.getType())
        ) {
            return false;
        }

        if (!choices.equals(multipleChoice.choices)) {
            return false;
        }

        return true;
    }
}