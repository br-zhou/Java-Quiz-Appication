package model.questions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    Question testQuestion;
    Question testQuestionCopy;

    @BeforeEach
    void runBefore() {
        testQuestion = new FreeResponse("Prompt", new ArrayList<>());
        testQuestionCopy = new FreeResponse("Prompt", new ArrayList<>());
    }

    @Test
    void testConstructor() {
        assertEquals("Prompt", testQuestion.getPrompt());
    }

    @Test
    void testSetPrompt() {
        testQuestionCopy.setPrompt("Clone");
        assertEquals("Clone", testQuestionCopy.getPrompt());
    }
}
