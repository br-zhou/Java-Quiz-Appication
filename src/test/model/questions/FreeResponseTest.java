package model.questions;

import model.InputOutput;
import model.InputOutputForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FreeResponseTest {
    private FreeResponse question1;
    private FreeResponse question2;
    List<String> question1Keywords;
    List<String> question2Keywords;
    private InputOutput correctInputForQ1;
    private InputOutput correctInputForQ2;
    private InputOutput incorrectInput;

    @BeforeEach
    public void runBefore() {
        question1Keywords = new ArrayList<>();
        question1Keywords.add("word");

        question2Keywords = new ArrayList<>();
        question2Keywords.add("word1");
        question2Keywords.add("word2");
        question2Keywords.add("word3");

        question1 = new FreeResponse("Question 1", question1Keywords);
        question2 = new FreeResponse("Question 2", question2Keywords);

        correctInputForQ1 = new InputOutputForTests() {
            @Override
            public String getString() {
                return "this sentence contains words.";
            }
        };

        correctInputForQ2 = new InputOutputForTests() {
            @Override
            public String getString() {
                return "word1word2word3";
            }
        };

        incorrectInput = new InputOutputForTests() {
            @Override
            public String getNonEmptyString() {
                return "abc";
            }
        };
    }

    @Test
    public void testConstructor() {
        assertFalse(question1.isCorrect());
        assertFalse(question2.isCorrect());

        assertEquals("Question 1", question1.getPrompt());
        assertEquals("Question 2", question2.getPrompt());

        assertEquals(question1Keywords, question1.getKeywords());
        assertEquals(question2Keywords, question2.getKeywords());
    }

    @Test
    public void testAttempt() {
        question1.attempt(incorrectInput);
        assertFalse(question1.isCorrect());
        question1.attempt(correctInputForQ1);
        assertTrue(question1.isCorrect());

        question2.attempt(incorrectInput);
        assertFalse(question2.isCorrect());
        question2.attempt(correctInputForQ2);
        assertTrue(question2.isCorrect());
    }

    @Test
    public void testToString() {
        assertEquals(question1.getPrompt() + "\nPlease type your answer below.", question1.toString());
        assertEquals(question2.getPrompt() + "\nPlease type your answer below.", question2.toString());
    }
}
