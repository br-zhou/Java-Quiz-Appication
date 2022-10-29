package persistance;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.Quiz;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DataHandlerTest {
    static final String TEST_EMPTY_FILE_PATH = "./data/testEmptyFile.json";
    static final String TEST_QUESTIONS_PROMPT = "this is a question prompt";
    static final String QUIZ_1_NAME = "Quiz 1";
    static final String QUIZ_2_NAME = "Quiz 2";

    DataHandler emptyFile;
    DataHandler invalidFile;
    DataHandler corruptFile;

    @BeforeEach
    public void runBefore() {
        emptyFile = new DataHandler(TEST_EMPTY_FILE_PATH);
        invalidFile = new DataHandler("/c/tmp/home");
        corruptFile = new DataHandler("./data/testCorruptedFile.json");
    }

    @Test
    public void testEnsureFileExists() {
        try {
            emptyFile.deleteFile();
        } catch (IOException e) {
            fail("could not delete file during testEnsureFileExists");
        }

        try {
            emptyFile.ensureFileExists();
        } catch (IOException e) {
            fail("could not make file during testEnsureFileExists");
        }

        File emptyFile = new File(TEST_EMPTY_FILE_PATH);

        assertTrue(emptyFile.exists());
    }

    @Test
    public void testDeleteFile() {
        try {
            emptyFile.ensureFileExists();
        } catch (IOException e) {
            fail("could not make file during testDeleteFile");
        }

        try {
            emptyFile.deleteFile();
        } catch (IOException e) {
            fail("could not delete file during testDeleteFile");
        }

        File emptyFile = new File(TEST_EMPTY_FILE_PATH);

        assertFalse(emptyFile.exists());
    }

    @Test
    public void testUpdateData() {
        DataHandler file = new DataHandler("./data/file.json");

        List<Quiz> quizzes = createQuizList1();
        JSONObject importedQuizzesJson;
        try {
            file.updateData(quizzes);
            assertTrue(equalQuizList(quizzes, file.retrieveData()));
        } catch (Exception e) {
            fail("Error occurred while running testUpdateData");
        }

        try {
            invalidFile.updateData(quizzes);
            fail("Error: should not be able to write to invalid file!");
        } catch (WriteErrorException e) {
            // this passes
        }

    }

    @Test
    public void testRetrieveData() {
        DataHandler storedData = new DataHandler("./data/testRetrieveData.json");

        try {
            List<Quiz> storeQuizzes = storedData.retrieveData();



        } catch (Exception e) {
            fail("Error occurred while running testUpdateData");
        }

        try {
            invalidFile.retrieveData();
            fail("Error: should not be able to write to invalid file!");
        } catch (ReadErrorException e) {
            // this passes
        }

        try {
            corruptFile.retrieveData();
            fail("Error: should not be able to read corruted file!");
        } catch (Exception e) {
            // pass
        }

    }

    private List<Quiz> createQuizList1() {
        List<Quiz> result = new ArrayList<>();

        result.add(new Quiz(QUIZ_1_NAME, createQuestionList1()));
        result.add(new Quiz(QUIZ_2_NAME, createQuestionList2()));

        return result;
    }

    private List<Quiz> createQuizList2() {
        List<Quiz> result = new ArrayList<>();

        result.add(new Quiz(QUIZ_2_NAME, createQuestionList2()));
        result.add(new Quiz(QUIZ_1_NAME, createQuestionList1()));
        result.add(new Quiz(QUIZ_1_NAME, createQuestionList1()));
        result.add(new Quiz(QUIZ_1_NAME, createQuestionList1()));

        return result;
    }

    private List<Question> createQuestionList1() {
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

    private List<Question> createQuestionList2() {
        List<Question> result = new ArrayList<>();

        MultipleChoice multipleChoice2 = new MultipleChoice(TEST_QUESTIONS_PROMPT, createMultipleChoiceAnswers2());
        FreeResponse freeResponse2 = new FreeResponse(TEST_QUESTIONS_PROMPT, createKeywords2());

        result.add(multipleChoice2);
        result.add(freeResponse2);

        return result;
    }

    private List<String> createMultipleChoiceAnswers1() {
        List<String> result = new ArrayList<>();
        result.add("true");
        result.add("false");
        return result;
    }

    private List<String> createMultipleChoiceAnswers2() {
        List<String> result = new ArrayList<>();
        result.add("correct answer");
        result.add("incorrect answer #1");
        result.add("incorrect answer #2");
        result.add("incorrect answer #3");

        return result;
    }

    private List<String> createKeywords1() {
        List<String> result = new ArrayList<>();
        result.add("keyword");
        return result;
    }

    private List<String> createKeywords2() {
        List<String> result = new ArrayList<>();
        result.add("apple");
        result.add("peach");
        result.add("banana");
        return result;
    }

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

    private boolean equalMultipleChoiceQuestions(MultipleChoice a, MultipleChoice b) {
        return equalArrayList(a.getChoices(), b.getChoices());
    }

    private boolean equalFreeResponseQuestions(FreeResponse a, FreeResponse b) {
        return equalArrayList(a.getKeywords(), b.getKeywords());
    }

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
