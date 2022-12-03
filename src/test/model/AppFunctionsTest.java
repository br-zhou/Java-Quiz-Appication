package model;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static model.QuizTemplateMaker.createQuizList1;
import static org.junit.jupiter.api.Assertions.*;

public class AppFunctionsTest {
    AppFunctions invalidAppActions;
    AppFunctions corruptAppActions;
    AppFunctions defaultAppActions;
    AppFunctions actions;
    Quiz newQuiz1;
    Quiz newQuiz2;

    @BeforeEach
    void runBefore() {
        invalidAppActions = new AppFunctions("/c/tmp/home");
        corruptAppActions = new AppFunctions("./data/testCorruptedFile.json");
        defaultAppActions = new AppFunctions("./data/testFile.json");

        newQuiz1 = new Quiz("Name", new ArrayList<>());
        newQuiz2 = new Quiz("Name2", new ArrayList<>());

        actions = new AppFunctions("./irrelevantPath.json");

        for (Quiz quiz : QuizTemplateMaker.createQuizList1()) {
            actions.addQuiz(quiz);
        }

    }

    @Test
    void testConstructor() {
        assertEquals(0, invalidAppActions.getQuizzes().size());
        assertEquals(0, defaultAppActions.getQuizzes().size());
    }

    @Test
    public void testAddQuiz() {
        List<Quiz> testQuestions = QuizTemplateMaker.createQuizList1();
        defaultAppActions.addQuiz(testQuestions.get(0));

        assertEquals(1, defaultAppActions.getQuizzes().size());

        assertEquals(testQuestions.get(0), defaultAppActions.getQuizAtIndex(0));

        defaultAppActions.addQuiz(testQuestions.get(1));

        assertEquals(2, defaultAppActions.getQuizzes().size());
        assertEquals(testQuestions.get(1), defaultAppActions.getQuizAtIndex(1));
    }

    @Test
    public void testDeleteQuiz() {
        List<Quiz> testQuestions = QuizTemplateMaker.createQuizList1();
        defaultAppActions.addQuiz(testQuestions.get(0));
        defaultAppActions.addQuiz(testQuestions.get(1));

        Quiz deletedQuiz = testQuestions.get(0);

        defaultAppActions.deleteQuiz(deletedQuiz);

        assertEquals(1, defaultAppActions.getQuizzes().size());
        assertFalse(deletedQuiz == defaultAppActions.getQuizAtIndex(0));

        defaultAppActions.deleteQuiz(testQuestions.get(1));

        assertEquals(0, defaultAppActions.getQuizzes().size());
    }

    @Test
    public void testUpdateData() {
        AppFunctions file = new AppFunctions("./data/testFile.json");
        List<Quiz> quizzes = createQuizList1();

        try {
            file.setQuizzes(quizzes);
            file.pushDataToStorage();
            file.pullDataFromStorage();
            assertTrue(equalQuizList(quizzes,file.getQuizzes()));
        } catch (Exception e) {
            fail("Error occurred while running testUpdateData");
        }

        try {
            invalidAppActions.pushDataToStorage();
            fail("Error: should not be able to write to invalid file!");
        } catch (WriteErrorException e) {
            // this passes
        }
    }

    @Test
    public void testRetrieveData() {
        AppFunctions file = new AppFunctions("./data/testFile.json");
        List<Quiz> quizzes = createQuizList1();

        try {
            file.setQuizzes(quizzes);
            file.pushDataToStorage();
            file.pullDataFromStorage();
            assertTrue(equalQuizList(quizzes,file.getQuizzes()));
        } catch (Exception e) {
            fail("Error occurred while running testUpdateData");
        }

        try {
            invalidAppActions.pullDataFromStorage();
            fail("Error: should not be able to write to invalid file!");
        } catch (ReadErrorException e) {
            // this passes
        }

        try {
            corruptAppActions.pullDataFromStorage();
            fail("Error: should not be able to read corruted file!");
        } catch (Exception e) {
            // pass
        }

    }

    @Test
    void testSetTargetQuiz() {
        defaultAppActions.addQuiz(newQuiz1);
        defaultAppActions.setTargetQuiz(newQuiz1);
        assertEquals(newQuiz1, defaultAppActions.getTargetQuiz());

        defaultAppActions.addQuiz(newQuiz2);
        defaultAppActions.setTargetQuiz(newQuiz2);
        assertEquals(newQuiz2, defaultAppActions.getTargetQuiz());
    }

    @Test
    void testAddQuestionToTarget() {
        actions.setTargetQuiz(0);
        final int NUM_OF_QUESTIONS = actions.getTargetQuestions().size();
        actions.addQuestionToTarget(new MultipleChoice("HI",QuizTemplateMaker.createMultipleChoiceAnswers2()));
        assertEquals(NUM_OF_QUESTIONS + 1, actions.getTargetQuestions().size());

        actions.setTargetQuestion(0);
        Question targetQuestion = actions.getTargetQuestion();
        actions.deleteTargetQuestion();
        assertFalse(actions.getTargetQuestions().contains(targetQuestion));
        assertEquals(NUM_OF_QUESTIONS, actions.getTargetQuestions().size());
    }

    @Test
    void testSetTargetQuestion() {
        Question newQuestion = QuizTemplateMaker.createQuestionList1().get(0);
        actions.setTargetQuiz(0);
        actions.addQuestionToTarget(newQuestion);
        actions.setTargetQuestion(newQuestion);
        assertEquals(newQuestion, actions.getTargetQuestion());
    }

    @Test
    void testGetIterator() {
        Iterator<Event> eventIterator = actions.getEventIterator();
        int iteratorSize = 0;
        while (eventIterator.hasNext()) {
            iteratorSize++;
            eventIterator.next();
        }

        assertEquals(6, iteratorSize);
    }

    // HELPERS
    // returns whether Quiz List a has the same values as Quiz list b
    private boolean equalQuizList(List<Quiz> a, List<Quiz> b) {
        if (a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            if (!equalQuizzes(a.get(i), b.get(i))) {
                return false;
            }
        }

        return true;
    }

    // returns whether Quiz a has the same values as Quiz b
    private boolean equalQuizzes(Quiz a, Quiz b) {
        if (a.getQuestions().size() != b.getQuestions().size()) {
            return false;
        }

        if (!a.getName().equals(b.getName())) {
            return false;
        }

        for (int i = 0; i < a.getQuestions().size(); i++) {
            if(!equalQuestions(a.getQuestions().get(i), b.getQuestions().get(i))) {
                return false;
            }
        }

        return true;
    }

    // returns whether Question a has the same values as Question b
    private boolean equalQuestions(Question a, Question b) {
        if (!a.getPrompt().equals(b.getPrompt())) {
            return false;
        }

        if (!a.getType().equals(b.getType())) {
            return false;
        }

        switch (a.getType()) {
            case "MultipleChoice":
                return equalMultipleChoiceQuestions((MultipleChoice) a, (MultipleChoice) b);
            case "FreeResponse":
                return equalFreeResponseQuestions((FreeResponse) a, (FreeResponse) b);
            default:
                return false;
        }
    }

    // returns whether MultipleChoice Question a has the same values as Question b
    private boolean equalMultipleChoiceQuestions(MultipleChoice a, MultipleChoice b) {
        return equalArrayList(a.getChoices(), b.getChoices());
    }

    // returns whether FreeResponse Question a has the same values as Question b
    private boolean equalFreeResponseQuestions(FreeResponse a, FreeResponse b) {
        return equalArrayList(a.getKeywords(), b.getKeywords());
    }

    // returns whether String List a has the same values as String List b
    private boolean equalArrayList(List<String> a, List<String>  b) {
        if (a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            if (!Objects.equals(a.get(i), b.get(i))) {
                return false;
            }
        }

        return true;
    }

}
