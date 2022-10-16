package model;

import model.questions.Question;

import java.util.List;

public abstract class InputOutput {

    /*
     * EFFECTS: returns true or false depending on user input
     */
    public abstract boolean getPermission();

    /*
     * EFFECTS: returns a string depending on user input
     */
    public abstract String getString();

    /*
     * EFFECTS: returns a string depending on user input; string will not be empty
     */
    public abstract String getNonEmptyString();

    /*
     * EFFECTS: returns an integer depending on user input
     */
    public abstract int getInt();

    /*
     * EFFECTS: returns an integer within the two ranges, depending on user input
     */
    public abstract int getIntWithinRange(int min, int max);

    /*
     * EFFECTS: outputs question to user
     */
    public abstract void displayQuestion(Question question);

    /*
     * REQUIRES: list cannot be empty
     * EFFECTS: returns an element from list depending on user input
     */
    public <T> T getItemFromList(List<T> list) {
        int index = getIntWithinRange(1, list.size()) - 1;
        return list.get(index);
    }
}
