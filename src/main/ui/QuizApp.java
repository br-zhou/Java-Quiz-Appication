package ui;

import model.InputOutput;
import model.Quiz;
import model.Result;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;

import java.util.ArrayList;
import java.util.List;

// Quiz Application
public class QuizApp {
    private final List<Quiz> quizzes;
    private final InputOutput console;

    /*
     * EFFECTS: creates and runs the Quiz application
     */
    public QuizApp() {
        quizzes = new ArrayList<>();
        console = new Console();

        System.out.println("Welcome! This is a quiz app.");

        runApp();

        System.out.println("Bye! (and good luck on your tests!)");
    }


    /*
     * MODIFIES: this
     * EFFECTS: processes user input
     */
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
            question.attempt(console);
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

}
