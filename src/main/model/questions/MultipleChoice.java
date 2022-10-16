package model.questions;

import model.InputOutput;

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
        super(prompt);
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

    public List<String> getChoices() {
        return choices;
    }
}