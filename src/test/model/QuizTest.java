package model;

import model.questions.MultipleChoice;
import model.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    private Quiz quiz1;
    private Quiz quiz2;
    private List<Question> quiz1Questions;
    private List<Question> quiz2Questions;

    @BeforeEach
    public void runBefore() {
        List<String> questionChoices = new ArrayList<>();
        questionChoices.add("true");
        questionChoices.add("false");

        quiz1Questions = new ArrayList<>();
        quiz1Questions.add(new MultipleChoice("question 1", questionChoices));

        quiz1 = new Quiz("Quiz 1", quiz1Questions);

        quiz2Questions = new ArrayList<>();
        quiz2Questions.add(new MultipleChoice("question 1", questionChoices));
        quiz2Questions.add(new MultipleChoice("question 2", questionChoices));
        quiz2Questions.add(new MultipleChoice("question 3", questionChoices));

        quiz2 = new Quiz("Quiz 2", quiz2Questions);
    }

    @Test
    public void testConstructor() {
        assertEquals("Quiz 1", quiz1.getName());
        assertEquals("Quiz 2", quiz2.getName());

        assertEquals(1, quiz1.getQuestions().size());
        assertEquals(3, quiz2.getQuestions().size());

        assertEquals(quiz1Questions, quiz1.getQuestions());
        assertEquals(quiz2Questions, quiz2.getQuestions());
    }

    @Test
    public void testGetResults() {
        assertEquals(0, quiz1.getResult().getScore());
        assertEquals(0, quiz2.getResult().getScore());

        quiz1Questions.get(0).setCorrect(true);
        assertEquals(1, quiz1.getResult().getScore());

        quiz2Questions.get(1).setCorrect(true);
        assertEquals(1, quiz2.getResult().getScore());

        quiz2Questions.get(2).setCorrect(true);
        assertEquals(2, quiz2.getResult().getScore());

        quiz2Questions.get(0).setCorrect(true);
        assertEquals(3, quiz2.getResult().getScore());
    }

    @Test
    public void testAddQestion() {
    List<Question> testQuestions = QuizTemplateMaker.createQuestionList1();
        quiz1.addQuestion(testQuestions.get(0));

        assertEquals(2, quiz1.getQuestions().size());

        assertEquals(testQuestions.get(0), quiz1.getQuestions().get(1));

        quiz1.addQuestion(testQuestions.get(1));

        assertEquals(3, quiz1.getQuestions().size());
        assertEquals(testQuestions.get(1), quiz1.getQuestions().get(2));
    }

    @Test
    public void testDeleteQuestion() {
        Question deleteQuestion = quiz2Questions.get(0);
        quiz2.deleteQuestion(deleteQuestion);

        assertEquals(2, quiz2.getQuestions().size());
        assertFalse(quiz2.getQuestions().get(0) == deleteQuestion);
        assertFalse(quiz2.getQuestions().get(1) == deleteQuestion);

        Question deleteQuestion2 = quiz2Questions.get(0);
        quiz2.deleteQuestion(deleteQuestion2);

        assertEquals(1, quiz2.getQuestions().size());
        assertFalse(quiz2.getQuestions().get(0) == deleteQuestion2);
    }
}
