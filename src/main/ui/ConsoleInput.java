package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInput {
    private final Scanner input;

    ConsoleInput() {
        input = new Scanner(System.in);
    }

    // EFFECTS: returns true or false depending on user input
    public boolean getPermission(String prompt) {
        System.out.println(prompt + " (y/n)");
        String command = input.nextLine().toLowerCase();
        return command.length() > 0 && command.charAt(0) == 'y';
    }

    public String nextLine() {
        return input.nextLine();
    }

    public int nextInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("please input an integer.");
            }
        }
    }

    // EFFECTS: returns int within range of min and max, depending on user input.
    int getIntWithinRange(int min, int max) {
        int result;

        do {
            System.out.println(String.format("Choose a integer between %s and %s (inclusive)", min, max));
            result = nextInt();
        } while (result < min || result > max);

        return  result;
    }

    // REQUIRES: list cannot be empty
    public <T> T getItemFromArrayList(ArrayList<T> list) {
        int index = getIntWithinRange(1, list.size()) - 1;
        return list.get(index);
    }
}
