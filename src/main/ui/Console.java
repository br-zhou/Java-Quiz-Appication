package ui;

import model.questions.Question;

import java.util.Scanner;

// Gets user input from console
public class Console extends InputOutput {
    private final Scanner input;

    /*
     * EFFECTS: creates a new InputOutput based on the console
     */
    public Console() {
        input = new Scanner(System.in);
    }

    @Override
    public boolean getPermission() {
        System.out.println("(y/n)");
        String command = input.nextLine().toLowerCase();
        return command.length() > 0 && command.charAt(0) == 'y';
    }

    @Override
    public String getString() {
        return input.nextLine();
    }

    @Override
    public String getNonEmptyString() {
        String result = "";

        while (result.length() < 1) {
            result = getString();
        }

        return result;
    }

    @Override
    public int getInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("please input an integer.");
            }
        }
    }

    @Override
    public int getIntWithinRange(int min, int max) {
        int result;

        do {
            System.out.format("Choose a integer between %s and %s (inclusive)\n", min, max);
            result = getInt();
        } while (result < min || result > max);

        return  result;
    }

    @Override
    public void displayQuestion(Question question) {
        System.out.println(question);
    }
}
