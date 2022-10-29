package persistance;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.Quiz;
import model.questions.FreeResponse;
import model.questions.MultipleChoice;
import model.questions.Question;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Retrieves data from storage, and updates data to storage
// Referenced this demo when writing this class: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class DataHandler {
    String filePath;

    /*
     * REQUIRES: given path must be relative to project root folder
     * EFFECTS: creates a writer object that reads and writes to given file
     */
    public DataHandler(String filePath) {
        this.filePath = filePath;
    }


    /*
     * MODIFIES: the file of given path
     * EFFECTS: updates file at destination with current quizzes data
     *          throws WriteErrorException if unable to update data
     */
    public void updateData(List<Quiz> quizzes) throws WriteErrorException {
        final int TAB_SIZE = 4;
        try {
            JSONObject jsonData = quizzesToJson(quizzes);
            writeFile(filePath, jsonData.toString(TAB_SIZE));
        } catch (FileNotFoundException e) {
            throw new WriteErrorException();
        }

    }

    /*
     * EFFECTS: returns quizzes data from storage,
     *          returns empty list if file doesn't exist
     *          throws ReadErrorException if unable to read file
     *          throws CorruptDataException if file is formatted incorrectly
     */
    public List<Quiz> retrieveData() throws ReadErrorException {
        try {
            String stringData = readFile(filePath);
            JSONObject jsonData = new JSONObject(stringData);
            return jsonToQuizzes(jsonData);
        } catch (IOException e) {
            throw new ReadErrorException();
        }
    }

    /*
     * REQUIRES: filePath must be relative to root of application
     * MODIFIES: the file of given path
     * EFFECTS: writes content to file at filePath
     *          throws FileNotFoundException if file doesn't exist
     */
    private void writeFile(String filePath, String content) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filePath);
        writer.print(content);
        writer.close();
    }

    /*
     * EFFECTS: reads source file as string and returns it
     *          throws IOException if file does not exist
     * this method was taken from the demo referenced before this class's definition
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /*
     * REQUIRES: jsonObject must contain these keys and values:
     *      - "quizzes" : [list of quizzes that follow jsonToQuiz requirements]
     * EFFECTS: returns new Quiz object constructed from jsonObject
     *          throws CorruptDataException if file is formatted incorrectly
    */
    private List<Quiz> jsonToQuizzes(JSONObject jsonObject) {
        List<Quiz> result = new ArrayList<>();
        JSONArray quizzesJsonArray = jsonObject.getJSONArray("quizzes");

        for (Object json : quizzesJsonArray) {
            JSONObject quiz = (JSONObject) json;
            result.add(jsonToQuiz(quiz));
        }

        return result;
    }

    /*
     * REQUIRES: quizJson must contain these keys and values:
     *      - "name" : String
     *      - "questions" : [list of questions that follow jsonToQuiz requirements]
     * EFFECTS: returns new Quiz object constructed from quizJson
     *          throws CorruptDataException if file is formatted incorrectly
     */
    private Quiz jsonToQuiz(JSONObject quizJson) {
        String name = quizJson.getString("name");
        JSONArray questionsJson = quizJson.getJSONArray("questions");
        return new Quiz(name, jsonToQuestionList(questionsJson));
    }

    /*
     * REQUIRES: questions must be an array of Questions that follow jsonToQuestion requirements
     * EFFECTS: returns new list of Questions constructed from given JSONArray
     *          throws CorruptDataException if file is formatted incorrectly
     */
    private List<Question> jsonToQuestionList(JSONArray questions) {
        List<Question> result = new ArrayList<>();
        for (Object json : questions) {
            JSONObject questionJson = (JSONObject) json;
            result.add(jsonToQuestion(questionJson));
        }

        return result;
    }

    /*
     * REQUIRES: question must contain these keys and values:
     *      - "type" : String
     *      and must follow either jsonToMultipleChoice or jsonToFreeResponse requirements,
     *      depending on the value of "type"
     * EFFECTS: returns new Question object constructed from given JSONObject
     *          throws CorruptDataException if file is formatted incorrectly
     */
    private Question jsonToQuestion(JSONObject question) {
        String type = question.getString("type");
        String prompt = question.getString("prompt");

        switch (type) {
            case "MultipleChoice":
                return jsonToMultipleChoice(question, prompt);
            case "FreeResponse":
                return jsonToFreeResponse(question, prompt);
            default:
                throw new JSONException("Question type is incorrect");
        }
    }

    /*
     * REQUIRES: question must contain these keys and values:
     *      - "correctChoice" : String
     *      - "choices" : [an array of strings]
     * EFFECTS: returns new MultipleChoice object constructed from given JSONObject
     */
    private MultipleChoice jsonToMultipleChoice(JSONObject question, String prompt) {
        String correctChoice = question.getString("correctChoice");
        ArrayList<String> choices = jsonToStringList(question.getJSONArray("choices"));

        choices.remove(choices.indexOf(correctChoice));
        choices.add(0, correctChoice);

        return new MultipleChoice(prompt, choices);
    }

    /*
     * REQUIRES: question must contain these keys and values:
     *      - "keywords" : [an array of strings]
     * EFFECTS: returns new MultipleChoice object constructed from given JSONObject
     */
    private FreeResponse jsonToFreeResponse(JSONObject question, String prompt) {
        ArrayList<String> keywords = jsonToStringList(question.getJSONArray("keywords"));
        return new FreeResponse(prompt, keywords);
    }

    /*
     * REQUIRES: given JSONArray must only contain Strings:
     * EFFECTS: returns an ArrayList constructed from given JSONObject
     */
    private ArrayList<String> jsonToStringList(JSONArray array) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            result.add(array.getString(i));
        }

        return result;
    }

    /*
     * EFFECTS: parses JSON object from quiz list and returns it
     */
    private JSONObject quizzesToJson(List<Quiz> quizzes) {
        JSONObject result = new JSONObject();
        JSONArray quizzesJson = new JSONArray();

        for (Quiz quiz : quizzes) {
            quizzesJson.put(quiz.toJson());
        }

        result.put("quizzes", quizzesJson);

        return result;
    }

    /*
     * EFFECTS: creates a new file at path if file does not exist
     *          throws IOException if unable to create file
     */
    public void ensureFileExists() throws IOException {
        File dataFile = new File(filePath);
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }
    }

    /*
     * EFFECTS: deletes file at path if file exists
     *          throws IOException if unable to delete file
     */
    public void deleteFile() throws IOException {
        File dataFile = new File(filePath);
        if (dataFile.exists()) {
            dataFile.delete();
        }
    }
}
