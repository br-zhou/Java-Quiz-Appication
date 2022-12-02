package model.questions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceTest {
    private MultipleChoice question1;
    private MultipleChoice question2;
    private MultipleChoice question3;
    private FreeResponse differentQuestionType;

    List<String> question1Answers;
    List<String> question2Answers;
    List<String> question3Answers;

    @BeforeEach
    public void runBefore() {
        question1Answers = new ArrayList<>();
        question1Answers.add("correct");
        question1Answers.add("incorrect");

        question2Answers = new ArrayList<>();
        question2Answers.add("correct");
        question2Answers.add("incorrect 1");
        question2Answers.add("incorrect 2");

        question3Answers = new ArrayList<>();
        question3Answers.add("correct");
        question3Answers.add("incorrect 1");
        question3Answers.add("incorrect 2");
        question3Answers.add("incorrect 3");
        question3Answers.add("incorrect 4");

        question1 = new MultipleChoice("Question 1", question1Answers);
        question2 = new MultipleChoice("Question 2", question2Answers);
        question3 = new MultipleChoice("Question 3", question3Answers);
        differentQuestionType = new FreeResponse("Question 1", question1Answers);
    }

    @Test
    public void testConstructor() {
        assertFalse(question1.isCorrect());
        assertFalse(question2.isCorrect());
        assertFalse(question3.isCorrect());

        assertEquals("Question 1", question1.getPrompt());
        assertEquals("Question 2", question2.getPrompt());
        assertEquals("Question 3", question3.getPrompt());

        assertEquals(question1Answers, question1.getChoices());
        assertEquals(question2Answers, question2.getChoices());
        assertEquals(question3Answers, question3.getChoices());
    }

    @Test
    public void testAttempt() {
        question1.attempt("correct ");
        assertFalse(question1.isCorrect());
        question1.attempt("correct");
        assertTrue(question1.isCorrect());

        question2.attempt("incorrect1");
        assertFalse(question2.isCorrect());
        question2.attempt("correct");
        assertTrue(question2.isCorrect());

        question3.attempt("incorrent");
        assertFalse(question3.isCorrect());
        question3.attempt("correct");
        assertTrue(question3.isCorrect());
    }

    @Test
    public void testToString() {
        String question1String = question1.toString();
        assertEquals(
                question1.getPrompt() +
                        "\n 1) " + question1Answers.get(0) +
                        "\n 2) " + question1Answers.get(1),
                question1String);

        String question3String = question3.toString();
        assertEquals(
                question3.getPrompt() +
                        "\n 1) " + question3Answers.get(0) +
                        "\n 2) " + question3Answers.get(1) +
                        "\n 3) " + question3Answers.get(2) +
                        "\n 4) " + question3Answers.get(3) +
                        "\n 5) " + question3Answers.get(4),
                question3String);
    }

    @Test
    public void testEquals() {
        assertTrue(question1.equals(question1));
        assertFalse(question1.equals(question2));
        assertFalse(question1.equals(null));
        assertFalse(question1.equals(differentQuestionType));


        MultipleChoice question3Copy = new MultipleChoice("Question 3", new ArrayList<>(question3Answers));

        assertTrue(question3.equals(question3Copy));

        question3Copy.setChoices(question2Answers);

        assertFalse(question3.equals(question3Copy));
    }
}
