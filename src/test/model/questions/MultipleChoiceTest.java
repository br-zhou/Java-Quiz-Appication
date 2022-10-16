package model.questions;

import model.InputOutput;
import model.InputOutputForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceTest {
    private MultipleChoice question1;
    private MultipleChoice question2;
    private MultipleChoice question3;
    List<String> question1Answers;
    List<String> question2Answers;
    List<String> question3Answers;
    private InputOutput correctInput;
    private InputOutput incorrectInput;

    @BeforeEach
    public void runBefore() {
        question1Answers = List.of("correct", "incorrect");
        question2Answers = List.of(
                "correct",
                "incorrect 1",
                "incorrect 2"
        );
        question3Answers = List.of(
                "correct",
                "incorrect 1",
                "incorrect 2",
                "incorrect 3",
                "incorrect 4"
        );

        question1 = new MultipleChoice("Question 1", question1Answers);
        question2 = new MultipleChoice("Question 2", question2Answers);
        question3 = new MultipleChoice("Question 3", question3Answers);

        correctInput = new InputOutputForTests() {
            @Override
            public int getIntWithinRange(int min, int max) {
                return min;
            }
        };

        incorrectInput = new InputOutputForTests() {
            @Override
            public int getIntWithinRange(int min, int max) {
                return max;
            }
        };
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
        question1.attempt(incorrectInput);
        assertFalse(question1.isCorrect());
        question1.attempt(correctInput);
        assertTrue(question1.isCorrect());

        question2.attempt(incorrectInput);
        assertFalse(question2.isCorrect());
        question2.attempt(correctInput);
        assertTrue(question2.isCorrect());

        question3.attempt(incorrectInput);
        assertFalse(question3.isCorrect());
        question3.attempt(correctInput);
        assertTrue(question3.isCorrect());
    }
}
