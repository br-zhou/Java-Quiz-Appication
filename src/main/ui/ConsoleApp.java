package ui;

import model.Quiz;
import model.Result;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class ConsoleApp {
    private List<Quiz> quizzes;
    private ConsoleInput input;

    // EFFECTS: runs the quiz console application
    public ConsoleApp() {
        quizzes = new ArrayList<>();
        input = new ConsoleInput();

        System.out.println("Welcome! This is a quiz app.");

        runApp();

        System.out.println("Bye! (and good luck on your tests!)");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        String command;
        boolean exitProgram = false;

        System.out.println("What would you like to do?");

        while (!exitProgram) {
            printMenuOptions();
            command = input.getString().toLowerCase();

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
    private void printMenuOptions() {
        System.out.println("select from:");
        System.out.println("    n -> new quiz");
        System.out.println("    t -> take quiz");
        System.out.println("    q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: creates new quiz and adds it to test repository
    private void newQuiz() {
        System.out.println("What would you like to name your quiz?");
        String quizName = input.getString();

        Quiz quiz = new Quiz(quizName, generateQuestions());

        quizzes.add(quiz);
        System.out.println("New quiz made!\n");
    }

    private List<Question> generateQuestions() {
        List<String> questionTypes = new ArrayList<>();
        List<Question> result = new ArrayList<>();

        questionTypes.add("Multiple Choice");
        questionTypes.add("Free Response");

        do {
            System.out.println("What type of question do you want?");

            for (int i = 0; i < questionTypes.size(); i++) {
                System.out.println(String.format("%s) %s", i + 1, questionTypes.get(i)));
            }

            String choice = input.getItemFromList(questionTypes);

            switch (choice) {
                case "Multiple Choice": // TODO is this code duplication? from above?
                    result.add(newMultipleChoiceQuestion());
                    break;
                case "Free Response":
                    result.add(newFreeResponseQuestion());
                    break;
                default:
                    System.out.println("ERROR: invalid question type!");
            }

            System.out.println("Would you like to add another question?");
        } while (input.getPermission());

        return result;
    }

    private Question newFreeResponseQuestion() {
        return new FreeResponse(getQuestionPrompt(), getFreeResponseKeywords());
    }

    private Question newMultipleChoiceQuestion() {
        return new MultipleChoice(getQuestionPrompt(), getQuestionChoices());
    }

    private String getQuestionPrompt() {
        System.out.println("What is your question's prompt?");
        return input.getString();
    }

    private List<String> getFreeResponseKeywords() {
        List<String> result = new ArrayList<>();
        System.out.println("How many keywords does this question have?");
        int numOfKeywords = input.getIntWithinRange(1, 10);

        for (int i = 0; i < numOfKeywords; i++) {
            System.out.println(String.format("Type in keyword #%s", i + 1));
            result.add(input.getString());
        }

        System.out.println(result);

        return result;
    }


    private List<String> getQuestionChoices() {
        List<String> result = new ArrayList<>();
        System.out.println("How many possible choices would you like for this question?");
        int amountOfChoices = input.getIntWithinRange(2, 5);

        System.out.println("Type the CORRECT answer");
        String ans = input.getString();
        result.add(ans);

        for (int i = 0; i < amountOfChoices - 1; i++) {
            System.out.println("Type in a trick answer");
            result.add(input.getString());
        }

        System.out.println(result);

        return result;
    }

    private void warnInvalidOption(String option) {
        System.out.println(String.format("'%s' is not a valid option.", option));
    }

    // EFFECTS: selects a quiz to take, depending on user input
    private void takeQuiz() {
        if (quizzes.size() == 0) {
            System.out.println("You have no quizzes!");
            return;
        }

        System.out.println("Which quiz would you like to take?");
        listQuizzes();

        Quiz selectedQuiz = input.getItemFromList(quizzes);

        Result result =  selectedQuiz.start(input);

        System.out.println(result);
        System.out.println();
    }

    private void listQuizzes() {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
            System.out.println((i + 1) + ") " + quiz.getName());
        }
    }

}
