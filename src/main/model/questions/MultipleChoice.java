package model.questions;

import model.Input;
import java.util.List;

public class MultipleChoice extends Question {
    private List<String> choices;

    // REQUIRES: 2 < choices size < 5, with the correct choice being the first element
    // EFFECTS: creates a new question, TODO more description. what abt prompt, choices, correct answer?
    public MultipleChoice(String prompt, List<String> choices) {
        super(prompt);
        this.choices = choices;
    }

    private String getCorrectAnswer() {
        return choices.get(0);
    }

    @Override
    public boolean attempt(Input input) {
        System.out.println(this);

        String choice = input.getItemFromList(choices);

        return choice.equals(getCorrectAnswer());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        for (int i = 0; i < choices.size(); i++) {
            result.append(String.format("\n %s) %s", i + 1, choices.get(i)));
        }
        return result.toString();
    }
}