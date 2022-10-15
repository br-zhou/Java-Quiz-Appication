package model;

import java.util.List;

public abstract class Input {

    // EFFECTS: returns true if user gives permission
    //          false otherwise
    public abstract boolean getPermission();

    // EFFECTS: returns a string input from user
    public abstract String getString();

    // EFFECTS: returns an integer
    public abstract int getInt();

    // EFFECTS: returns int within range of min and max, depending on user input.
    public abstract int getIntWithinRange(int min, int max);

    // REQUIRES: list cannot be empty
    public <T> T getItemFromList(List<T> list) {
        int index = getIntWithinRange(1, list.size()) - 1;
        return list.get(index);
    }
}
