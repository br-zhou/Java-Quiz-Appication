package ui;

import model.Quiz;
import model.Result;
import model.questions.MultipleChoice;
import model.questions.Question;

import java.util.ArrayList;

public class ConsoleApp {
    private ArrayList<Quiz> quizzes;
    private ConsoleInput input;

    // EFFECTS: runs the quiz console application
    ConsoleApp() {
        quizzes = new ArrayList<>();
        input = new ConsoleInput();

        System.out.println("Welcome! This is a quiz app.");

        runApp();

        System.out.println("Bye! (and good luck on your tests!)");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    void runApp() {
        String command;
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
        System.out.println("New quiz made!\n");
    }

    ArrayList<Question> getQuestions() {
        ArrayList<Question> result = new ArrayList<>();

        do {
            System.out.println("What is your question's prompt?");
            String prompt = input.nextLine();

            ArrayList<String> choices = getQuestionChoices();

            Question question = new MultipleChoice(prompt, choices);

            result.add(question);
        } while (input.getPermission("Would you like to add another question?"));

        return result;
    }

    ArrayList<String> getQuestionChoices() {
        ArrayList<String> result = new ArrayList<>();
        System.out.println("How many possible choices would you like for this question?");
        int amountOfChoices = input.getIntWithinRange(2, 5);

        System.out.println("Type the CORRECT answer");
        String ans = input.nextLine();
        result.add(ans);

        for (int i = 0; i < amountOfChoices - 1; i++) {
            System.out.println("Type in a trick answer");
            result.add(input.nextLine());
        }

        System.out.println(result);

        return result;
    }

    void warnInvalidOption(String option) {
        System.out.println(String.format("'%s' is not a valid option.", option));
    }

    // EFFECTS: selects a quiz to take, depending on user input
    void takeQuiz() {
        if (quizzes.size() == 0) {
            System.out.println("You have no quizzes!");
            return;
        }

        System.out.println("Which quiz would you like to take?");
        listQuizzes();

        Quiz selectedQuiz = input.getItemFromArrayList(quizzes);

        Result result =  selectedQuiz.start(input);

        System.out.println(result);
        System.out.println();
    }

    void listQuizzes() {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
            System.out.println((i + 1) + ") " + quiz.getName());
        }
    }

}
