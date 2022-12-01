package model.questions;

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
        question1.attempt("incorrectInput");
        assertFalse(question1.isCorrect());
        question1.attempt("word");
        assertTrue(question1.isCorrect());

        question2.attempt("word1word2word 3");
        assertFalse(question2.isCorrect());
        question2.attempt("222word12word3word200");
        assertTrue(question2.isCorrect());
    }

    @Test
    public void testToString() {
        assertEquals(question1.getPrompt() + "\nPlease type your answer below.", question1.toString());
        assertEquals(question2.getPrompt() + "\nPlease type your answer below.", question2.toString());
    }
}
