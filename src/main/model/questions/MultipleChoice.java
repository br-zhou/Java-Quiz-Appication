package model.questions;

import java.util.ArrayList;

public class MultipleChoice extends Question {
    private String answer;
    private ArrayList<String> choices;

    MultipleChoice(String prompt, ArrayList<String> choices, String answer) {
        super(prompt);
        this.choices = choices;
        this.answer = answer;
    }

    @Override
    public void answerQuestion() {
        answeredCorrectly = false;
    }

    @Override
    public String toString() {
        String result = super.toString();
        for (int i = 0; i < choices.size(); i++) {
            result += String.format("\n %s) %s", i, choices.get(i));
        }
        return result;
    }
}