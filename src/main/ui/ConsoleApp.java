package ui;

import exceptions.WriteErrorException;
import model.Quiz;
import model.Result;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import persistance.DataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Quiz Application
public class ConsoleApp {
    private static final String FILE_PATH = "./data/data.json";
    private final Console console;
    private final DataHandler storage;
    private List<Quiz> quizzes;

    /*
     * EFFECTS: starts application
     */
    public static void main(String[] args) {
        new ConsoleApp();
    }

    /*
     * EFFECTS: creates and runs the Quiz application
     */
    public ConsoleApp() {
        quizzes = new ArrayList<>();
        console = new Console();
        storage = new DataHandler(FILE_PATH);

        try {
            storage.ensureFileExists();
        } catch (IOException e) {
            System.out.println("Error connecting to storage... Loading and saving data may not work.");
        }

        System.out.println("Welcome! This is a quiz app.");

        runApp();

        System.out.println("Bye! (and good luck on your tests!)");
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input
     */

    @SuppressWarnings("methodlength")
    private void runApp() {
        boolean exitProgram = false;

        System.out.println("What would you like to do?");

        while (!exitProgram) {
            printMenuOptions();
            String command = console.getString().toLowerCase();

            switch (command) {
                case "n":
                    newQuiz();
                    break;
                case "t":
                    takeQuiz();
                    break;
                case "l":
                    loadData();
                    break;
                case "s":
                    saveData();
                    break;
                case "q":
                    exitProgram = true;
                    break;
                default:
                    warnInvalidOption(command);
            }
        }
    }

    /*
     * EFFECTS: prints out menu options
     */
    private void printMenuOptions() {
        System.out.println("select from:");
        System.out.println("    n -> new quiz");
        System.out.println("    t -> take quiz");
        System.out.println("    l -> load data");
        System.out.println("    s -> save data");
        System.out.println("    q -> quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates new quiz and adds it to test repository
     */
    private void newQuiz() {
        System.out.println("What would you like to name your quiz?");
        String quizName = console.getString();

        Quiz quiz = new Quiz(quizName, generateQuestions());

        quizzes.add(quiz);
        System.out.println("New quiz made!\n");
    }

    /*
     * EFFECTS: returns a set of questions based on user input
     */
    private List<Question> generateQuestions() {
        List<Question> result = new ArrayList<>();

        do {
            result.add(createQuestion());

            System.out.println("Would you like to add another question?");
        } while (console.getPermission());

        return result;
    }

    /*
     * EFFECTS: creates and returns a new question based on user input
     */
    private Question createQuestion() {
        final String MULTIPLE_CHOICE_STR = "Multiple Choice";
        final String FREE_RESPONSE_STR = "Free Response";

        List<String> questionTypes = new ArrayList<>();
        Question result;

        questionTypes.add(MULTIPLE_CHOICE_STR);
        questionTypes.add(FREE_RESPONSE_STR);

        System.out.println("What type of question do you want?");

        for (int i = 0; i < questionTypes.size(); i++) {
            System.out.format("%s) %s\n", i + 1, questionTypes.get(i));
        }

        String choice = console.getItemFromList(questionTypes);

        switch (choice) {
            case MULTIPLE_CHOICE_STR:
                result = new MultipleChoice(getQuestionPrompt(), getQuestionChoices());
                break;
            case FREE_RESPONSE_STR:
                result = new FreeResponse(getQuestionPrompt(), getFreeResponseKeywords());
                break;
            default:
                result = null;
                System.out.println("ERROR: invalid question type!");
        }

        return result;
    }

    /*
     * EFFECTS: returns string depending on user input
     */
    private String getQuestionPrompt() {
        System.out.println("What is your question's prompt?");
        return console.getNonEmptyString();
    }

    /*
     * EFFECTS: returns list of strings depending on user input
     */
    private List<String> getFreeResponseKeywords() {
        List<String> result = new ArrayList<>();
        System.out.println("How many keywords does this question have?");
        int numOfKeywords = console.getIntWithinRange(1, 10);

        for (int i = 0; i < numOfKeywords; i++) {
            System.out.format("Type in keyword #%s\n", i + 1);
            result.add(console.getNonEmptyString());
        }

        System.out.println(result);

        return result;
    }

    /*
     * EFFECTS: returns list of strings, depending on user input
     */
    private List<String> getQuestionChoices() {
        List<String> result = new ArrayList<>();
        System.out.println("How many possible choices would you like for this question?");
        int amountOfChoices = console.getIntWithinRange(2, 5);

        System.out.println("Type the CORRECT answer");
        String ans = console.getNonEmptyString();
        result.add(ans);

        for (int i = 0; i < amountOfChoices - 1; i++) {
            System.out.println("Type in a trick answer");
            result.add(console.getNonEmptyString());
        }

        System.out.println(result);

        return result;
    }

    /*
     * EFFECTS: warns user that option is not valid
     */
    private void warnInvalidOption(String option) {
        System.out.format("'%s' is not a valid option.\n", option);
    }

    /*
     * EFFECTS:  selects a quiz to take, depending on user input
     */
    private void takeQuiz() {
        if (quizzes.size() == 0) {
            System.out.println("You have no quizzes!");
            return;
        }

        System.out.println("Which quiz would you like to take?");
        listQuizzes();

        Quiz selectedQuiz = console.getItemFromList(quizzes);

        Result result =  startQuiz(selectedQuiz);

        System.out.println("Test results: " + result);
        System.out.println();
    }

    /*
     * EFFECTS: does quiz and returns result
     */
    public Result startQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestions()) {
            console.displayQuestion(question);

            if (question.getClass().equals(FreeResponse.class)) {
                question.attempt(console.getNonEmptyString());
            } else if (question.getClass().equals(MultipleChoice.class)) {
                MultipleChoice multiChoiceQuestion = (MultipleChoice) question;
                String choiceStr = console.getItemFromList(multiChoiceQuestion.getChoices());
                multiChoiceQuestion.attempt(choiceStr);
            }
        }

        return quiz.getResult();
    }

    /*
     * EFFECTS:  prints all quizzes to console
     */
    private void listQuizzes() {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
            System.out.println((i + 1) + ") " + quiz.getName());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads quizzes from storage
     *          prints warning if unable to load resource
     */
    public void loadData() {
        try {
            quizzes = storage.retrieveData();
            System.out.println("data retrieved from " + FILE_PATH);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: saves quizzes to storage
     *          prints warning if unable to save resource
     */
    public void saveData() {
        try {
            storage.updateData(quizzes);
            System.out.println("data saved to " + FILE_PATH);
        } catch (WriteErrorException e) {
            System.out.println("An error occurred while trying to save data.");
        }
    }

}
