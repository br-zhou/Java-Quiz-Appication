package model;

import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import java.util.ArrayList;
import java.util.List;

public class QuizTemplateMaker {
    public static final String TEST_QUESTIONS_PROMPT = "this is a question prompt";

    // EFFECTS: returns a new list of Quizzes
    public static List<Quiz> createQuizList1() {
        List<Quiz> result = new ArrayList<>();

        result.add(new Quiz("Quiz 1", createQuestionList1()));
        result.add(new Quiz("Quiz 2", createQuestionList2()));

        return result;
    }

    // EFFECTS: returns a new list of Quizzes
    public static List<Quiz> createQuizList2() {
        List<Quiz> result = new ArrayList<>();

        result.add(new Quiz("Quiz 1", createQuestionList1()));
        result.add(new Quiz("Quiz 2", createQuestionList2()));

        return result;
    }

    // EFFECTS: returns a new list of Questions
    public static List<Question> createQuestionList1() {
        List<Question> result = new ArrayList<>();

        MultipleChoice multipleChoice1 = new MultipleChoice(TEST_QUESTIONS_PROMPT, createMultipleChoiceAnswers1());
        MultipleChoice multipleChoice2 = new MultipleChoice(TEST_QUESTIONS_PROMPT, createMultipleChoiceAnswers2());
        FreeResponse freeResponse1 = new FreeResponse(TEST_QUESTIONS_PROMPT, createKeywords1());
        FreeResponse freeResponse2 = new FreeResponse(TEST_QUESTIONS_PROMPT, createKeywords2());

        result.add(multipleChoice1);
        result.add(freeResponse1);
        result.add(multipleChoice2);
        result.add(freeResponse2);

        return result;
    }

    // EFFECTS: returns a new list of Questions
    public static List<Question> createQuestionList2() {
        List<Question> result = new ArrayList<>();

        MultipleChoice multipleChoice2 = new MultipleChoice(TEST_QUESTIONS_PROMPT, createMultipleChoiceAnswers2());
        MultipleChoice multipleChoice1 = new MultipleChoice(TEST_QUESTIONS_PROMPT, createMultipleChoiceAnswers1());
        FreeResponse freeResponse2 = new FreeResponse(TEST_QUESTIONS_PROMPT, createKeywords2());
        FreeResponse freeResponse1 = new FreeResponse(TEST_QUESTIONS_PROMPT, createKeywords1());

        result.add(multipleChoice1);
        result.add(freeResponse1);
        result.add(multipleChoice2);
        result.add(freeResponse2);

        return result;
    }

    // EFFECTS: returns a new list of Strings
    public static List<String> createMultipleChoiceAnswers1() {
        List<String> result = new ArrayList<>();
        result.add("true");
        result.add("false");
        return result;
    }

    // EFFECTS: returns a new list of Strings
    public static List<String> createMultipleChoiceAnswers2() {
        List<String> result = new ArrayList<>();
        result.add("correct answer");
        result.add("incorrect answer #1");
        result.add("incorrect answer #2");
        result.add("incorrect answer #3");

        return result;
    }

    // EFFECTS: returns a new list of Strings
    public static List<String> createKeywords1() {
        List<String> result = new ArrayList<>();
        result.add("keyword");
        return result;
    }

    // EFFECTS: returns a new list of Strings
    public static List<String> createKeywords2() {
        List<String> result = new ArrayList<>();
        result.add("apple");
        result.add("peach");
        result.add("banana");
        return result;
    }
}








