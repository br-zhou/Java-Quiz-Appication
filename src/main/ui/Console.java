package ui;

import model.questions.Question;

import java.util.List;
import java.util.Scanner;

// Gets user input from console
public class Console {
    private final Scanner input;

    /*
     * EFFECTS: creates a new InputOutput based on the console
     */
    public Console() {
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: returns true or false depending on user input
     */
    public boolean getPermission() {
        System.out.println("(y/n)");
        String command = input.nextLine().toLowerCase();
        return command.length() > 0 && command.charAt(0) == 'y';
    }

    /*
     * EFFECTS: returns a string depending on user input
     */
    public String getString() {
        return input.nextLine();
    }

    /*
     * EFFECTS: returns a string depending on user input; string will not be empty
     */
    public String getNonEmptyString() {
        String result = "";

        while (result.length() < 1) {
            result = getString();
        }

        return result;
    }

    /*
     * EFFECTS: returns an integer depending on user input
     */
    public int getInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("please input an integer.");
            }
        }
    }

    /*
     * EFFECTS: returns an integer within the two ranges, depending on user input
     */
    public int getIntWithinRange(int min, int max) {
        int result;

        do {
            System.out.format("Choose a integer between %s and %s (inclusive)\n", min, max);
            result = getInt();
        } while (result < min || result > max);

        return  result;
    }

    /*
     * EFFECTS: outputs question to user
     */
    public void displayQuestion(Question question) {
        System.out.println(question);
    }

    /*
     * REQUIRES: list cannot be empty
     * EFFECTS: returns an element from list depending on user input
     */
    public <T> T getItemFromList(List<T> list) {
        int index = getIntWithinRange(1, list.size()) - 1;
        return list.get(index);
    }
}
