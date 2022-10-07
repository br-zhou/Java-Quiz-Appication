package ui;

import model.Quiz;
import model.questions.MultipleChoice;
import model.questions.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private List<Quiz> quizzes;
    private Scanner input;

    // EFFECTS: runs the quiz console application
    ConsoleApp() {
        quizzes = new ArrayList<Quiz>();
        input = new Scanner(System.in);

        System.out.println("Welcome! This is a quiz app.");

        runApp();

        System.out.println("Bye! (and good luck on your tests!)");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    void runApp() {
        String command = null;
        boolean exitProgram = false;

        System.out.println("What would you like to do?");

        while (!exitProgram) {
            printMenuOptions();
            command = input.nextLine().toLowerCase();

            switch (command) {
                case "n":
                    newQuiz();
                    break;
                case "t":
                    takeQuiz();
                    break;
                case "q":
                    exitProgram = true;
                    break;
                default:
                    warnInvalidOption(command);
            }
        }
    }

    // EFFECTS: prints out menu options
    void printMenuOptions() {
        System.out.println("select from:");
        System.out.println("    n -> new quiz");
        System.out.println("    t -> take quiz");
        System.out.println("    q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: creates new quiz and adds it to test repository
    void newQuiz() {
        System.out.println("What would you like to name your quiz?");
        String quizName = input.nextLine();

        ArrayList<Question> questions = getQuestions();

        Quiz quiz = new Quiz(quizName, questions);

        quizzes.add(quiz);
        System.out.println("New quiz made!");
    }

    // EFFECTS: conducts a test and gives user results
    void takeQuiz() {
        // stub;
    }

    ArrayList<Question> getQuestions() {
        ArrayList<Question> result = new ArrayList<>();

        do {
            System.out.println("What is your question's prompt?");

            String prompt = input.nextLine();
            ArrayList<String> choices = getQuestionChoices();

            Question question = new MultipleChoice(prompt, choices, choices.get(0));

            result.add(question);
        } while (getPermission("Would you like to add another question?"));

        return result;
    }

    // EFFECTS: returns int within range of min and max, depending on user input.
    int getIntWithinRange(int min, int max) {
        int result;

        do {
            System.out.println(String.format("Choose a integer between %s and %s (inclusive)", min, max));
            result = input.nextInt();;
        } while (result < min || result > max);

        return  result;
    }

    ArrayList<String> getQuestionChoices() {
        ArrayList<String> result = new ArrayList<>();
        int amountOfChoices = getIntWithinRange(2, 5);

        System.out.println("Type the CORRECT answer");
        result.add(input.nextLine());

        for (int i = 0; i < amountOfChoices - 1; i++) {
            System.out.println("Type in trick answers");
            result.add(input.nextLine());
        }

        return result;
    }

    void warnInvalidOption(String option) {
        System.out.println(String.format("'%s' is not a valid option.", option));
    }

    // EFFECTS: returns true or false depending on user input
    boolean getPermission(String prompt) {
        System.out.println(prompt + " (y/n)");
        String command = input.nextLine().toLowerCase();
        return command.length() > 0 && command.charAt(0) == 'y';
    }
}
