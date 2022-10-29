package persistance;

import model.Quiz;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DataHandlerTest {
    static final String TEST_EMPTY_FILE_PATH = "./data/testEmptyFile.json";
    static final String TEST_FILE_PATH = "./data/file.json";
    static final String INVALID_FILE_PATH = ".//\\&:file.json\"";
    static final String TEST_QUESTIONS_PROMPT = "this is a question prompt";
    static final String QUIZ_1_NAME = "Quiz 1";
    static final String QUIZ_2_NAME = "Quiz 2";

    DataHandler emptyFile;
    DataHandler invalidFile;
    DataHandler file1;

    @BeforeEach
    public void runBefore() {
        emptyFile = new DataHandler(TEST_EMPTY_FILE_PATH);
        invalidFile = new DataHandler(INVALID_FILE_PATH);
        file1 = new DataHandler(TEST_FILE_PATH);
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
        List<Quiz> quizzes = createQuizList();
        JSONObject rawQuizzesJson = convertQuizzesToJson(quizzes);
        JSONObject importedQuizzesJson;
        try {
            file1.updateData(quizzes);
            importedQuizzesJson = convertQuizzesToJson(file1.retrieveData());
            assertEquals(rawQuizzesJson.toString(), importedQuizzesJson.toString());
        } catch (Exception e) {
            fail("Error occurred while running testUpdateData");
        }
    }


    private List<Quiz> createQuizList() {
        List<Quiz> result = new ArrayList<>();

        result.add(new Quiz(QUIZ_1_NAME, createQuestionList1()));
        result.add(new Quiz(QUIZ_2_NAME, createQuestionList2()));

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

    private JSONObject convertQuizzesToJson(List<Quiz> quizzes) {
        JSONObject result = new JSONObject();
        JSONArray quizzesJson = new JSONArray();

        for (Quiz quiz : quizzes) {
            quizzesJson.put(quiz.toJson());
        }

        result.put("quizzes", quizzesJson);

        return result;
    }

}
