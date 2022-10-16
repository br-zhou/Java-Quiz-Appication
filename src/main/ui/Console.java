package ui;

import model.InputOutput;
import model.questions.Question;

import java.util.Scanner;

public class Console extends InputOutput {
    private final Scanner input;

    public Console() {
        input = new Scanner(System.in);
    }

    // EFFECTS: returns true or false depending on user input
    public boolean getPermission() {
        System.out.println("(y/n)");
        String command = input.nextLine().toLowerCase();
        return command.length() > 0 && command.charAt(0) == 'y';
    }

    public String getString() {
        return input.nextLine();
    }

    public String getNonEmptyString() {
        String result = "";

        while (result.length() < 1) {
            result = getString();
        }

        return result;
    }

    public int getInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("please input an integer.");
            }
        }
    }

    // EFFECTS: returns int within range of min and max, depending on user input.
    public int getIntWithinRange(int min, int max) {
        int result;

        do {
            System.out.format("Choose a integer between %s and %s (inclusive)\n", min, max);
            result = getInt();
        } while (result < min || result > max);

        return  result;
    }

    public void displayQuestion(Question question) {
        System.out.println(question);
    }
}
