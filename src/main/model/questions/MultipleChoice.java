package model.questions;

import ui.InputOutput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

// represents a multiple choice question, with choices and a correct answer
public class MultipleChoice extends Question {
    private final List<String> choices;
    private final String correctChoice;

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
    public void attempt(InputOutput inputOutput) {
        String choice = inputOutput.getItemFromList(choices);

        correct = choice.equals(correctChoice);
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
}