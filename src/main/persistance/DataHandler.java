package persistance;

import exceptions.ReadErrorException;
import exceptions.WriteErrorException;
import model.Quiz;
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
     * TODO TALK ABOUT ERRORS THROWN
     */
    public void updateData(List<Quiz> quizzes) throws WriteErrorException {
        try {
            JSONObject jsonData = quizzesToJson(quizzes);
            writeFile(filePath, jsonData.toString());
        } catch (FileNotFoundException e) {
            throw new WriteErrorException();
        }

    }

    /*
     * EFFECTS: returns quizzes data from storage,
     *          returns empty list if file doesn't exist
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
     * this method was taken from the demo TODO is this allowed
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /*
     * EFFECTS: parses quiz list from JSON object and returns it
    */
    private List<Quiz> jsonToQuizzes(JSONObject jsonObject) {
        List<Quiz> result = new ArrayList<>();
        // TODO parse json
        return result;
    }
    /*
     * EFFECTS: parses JSON object from quiz list and returns it
     */
    private JSONObject quizzesToJson(List<Quiz> quizzes) {
        JSONObject result = new JSONObject();

        return result;
    }

    /*
     * EFFECTS: creates a new file at path file does not exist
     */
    public void ensureFileExists() throws IOException {
        File newFile = new File(filePath);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
    }
}
