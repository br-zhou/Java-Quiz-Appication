package model.questions;

import model.InputOutput;

import java.util.Collections;
import java.util.List;

public class MultipleChoice extends Question {
    private final List<String> choices;
    private final String correctChoice;

    // REQUIRES: 2 < choices size < 5, with the correct choice being the first element
    // EFFECTS: creates a new question, TODO more description. what abt prompt, choices, correct answer?
    public MultipleChoice(String prompt, List<String> choices) {
        super(prompt);
        this.choices = choices;
        this.correctChoice = choices.get(0);
    }

    @Override
    public void attempt(InputOutput inputOutput) {
        String choice = inputOutput.getItemFromList(choices);

        correct = choice.equals(correctChoice);
    }

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
}