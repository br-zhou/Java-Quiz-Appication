package model.questions;

import ui.ConsoleInput;

import java.util.ArrayList;

public class MultipleChoice extends Question {
    private ArrayList<String> choices;
    private final String answer;

    // REQUIRES: 2 < choices size < 5, with the correct choice being the first element
    // EFFECTS: creates a new question
    public MultipleChoice(String prompt, ArrayList<String> choices) {
        super(prompt);
        this.choices = choices;
        answer = choices.get(0);
    }

    private String getCorrectAnswer() {
        return answer;
    }

    @Override
    public boolean attempt(ConsoleInput input) {
        System.out.println(this);

        String choice = input.getItemFromArrayList(choices);

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