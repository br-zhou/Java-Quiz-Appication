package ui.states;

import model.AppFunctions;
import model.Quiz;
import model.questions.FreeResponse;
import model.questions.Question;
import ui.GuiApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// State that represents the EDIT Quiz state
public class EditQuizState extends GuiState {
    JButton saveChangesBtn;
    JButton newQuestionBtn;
    JButton deleteQuestionBtn;
    JList<String> questionsListGui;
    DefaultListModel<String> listContent;
    JPanel questionsListPanel;
    JPanel mainBodyPanel;
    JTextField promptInput;
    JTextArea responseInput;
    JList<String> questionList;

    /*
     * EFFECTS: constructs new Editing Quiz state, along with associated GUI
     */
    public EditQuizState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
        super(jframe, stateManager, actions);
        listContent = new DefaultListModel<>();

        createGuiElements();
        setContentVisibility(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets visibility of GUI associated with EditQuizState to 'value'
     */
    @Override
    public void setContentVisibility(boolean value) {
        saveChangesBtn.setVisible(value);
        questionsListPanel.setVisible(value);
        mainBodyPanel.setVisible(value);
    }

    /*
     * MODIFIES: this
     * EFFECTS: renders state GUI
     */
    @Override
    public void loadState() {
        if (actions.getTargetQuiz() == null) {
            Quiz newQuiz = newTemplateQuiz();
            actions.addQuiz(newQuiz);
            actions.setTargetQuiz(newQuiz);
        }
        updateQuestionListGui();
        questionList.setSelectedIndex(0);
        selectQuestionInList(0);

        super.loadState();
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates question list to display latest content
     */
    void updateQuestionListGui() {
        List<Question> listQuestions = actions.getTargetQuiz().getQuestions();

        listContent.clear();
        for (int i = 0; i < listQuestions.size(); i++) {
            listContent.addElement(listQuestions.get(i).getPrompt());
        }

        selectQuestionInList(actions.getTargetQuiz().getQuestions().size() - 1);
    }


    /*
     * MODIFIES: this
     * EFFECTS: tells state which item is selected from Question list
     */
    void selectQuestionInList(int index) {
        actions.setTargetQuestion(index);
        loadTargetQuestionInGUI();
    }

    /*
     * REQUIRES: targetQuestion cannot be null
     * MODIFIES: this
     * EFFECTS: loads Question stored in target
     */
    void loadTargetQuestionInGUI() {
        promptInput.setText(actions.getTargetQuestion().getPrompt());

        FreeResponse question = (FreeResponse) actions.getTargetQuestion();
        StringBuilder responseContent = new StringBuilder();
        for (String keyword : question.getKeywords()) {
            responseContent.append(keyword + '\n');
        }

        responseInput.setText(responseContent.toString());
    }


    /*
     * MODIFIES: this
     * EFFECTS: creates all gui elements associated with state
     */
    void createGuiElements() {
        saveChangesBtn = makeSaveChangesButton();
        mainBodyPanel = makeMainBodyPanel();
        questionsListPanel = makeQuestionsListPanel();
    }


    /*
     * MODIFIES: this
     * EFFECTS: updates target question's data with input from user
     */
    void saveChangesToTargetQuestion() {
        updateTargetQuestion(promptInput.getText(), responseInput.getText());
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates target question with given prompt and answers
     */
    void updateTargetQuestion(String newPrompt, String newAnswers) {
        FreeResponse question = (FreeResponse) actions.getTargetQuestion();
        question.setPrompt(newPrompt);
        question.setKeywords(strToKeywords(newAnswers));
    }


    /*
     * EFFECTS: converts and returns given string to list of strings, spliced by newline
     */
    List<String> strToKeywords(String str) {
        String[] keywordsArr = str.split("\\r?\\n");
        return Arrays.asList(keywordsArr);
    }


    /*
     * MODIFIES: this
     * EFFECTS: draws gui for panel holding question lists and 'add/delete' buttons
     */
    JPanel makeQuestionsListPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(0,0, GuiApp.WIDTH, GuiApp.HEIGHT);
        jframe.add(panel);

        questionsListGui = makeQuestionsJList();
        JScrollPane scrollPane = new JScrollPane(questionsListGui,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBounds(800,60, 150, 300);
        panel.add(scrollPane);

        newQuestionBtn = makeNewQuestionBtn();
        panel.add(newQuestionBtn);

        deleteQuestionBtn = makeDeleteQuestionBtn();
        panel.add(deleteQuestionBtn);


        JLabel questionsListLabel = new JLabel("QUESTIONS:");
        questionsListLabel.setBounds(800,25, 150,40);
        panel.add(questionsListLabel);

        jframe.add(panel);

        return panel;
    }


    /*
     * MODIFIES: this
     * EFFECTS: makes and returns new questions list
     */
    JList<String> makeQuestionsJList() {
        questionList = new JList<>(listContent);
        jframe.add(questionList);

        questionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && questionsListGui.getSelectedIndex() >= 0) {
                saveChangesToTargetQuestion();
                actions.setTargetQuestion(questionsListGui.getSelectedIndex());
                loadTargetQuestionInGUI();
                updateTilesOfGuiList();
            }
        });

        return questionList;
    }


    /*
     * MODIFIES: this
     * EFFECTS: updates questions list with most recent data
     */
    void updateTilesOfGuiList() {
        for (int i = 0; i < listContent.size(); i++) {
            listContent.set(i, getQuestionPrompt(i));
        }
    }


    /*
     * REQUIRES: i must be valid index in quesion
     * MODIFIES: this
     * EFFECTS: returns question from prompt
     */
    String getQuestionPrompt(int i) {
        return actions.getTargetQuiz().getQuestions().get(i).getPrompt();
    }


    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for new question button
     */
    JButton makeNewQuestionBtn() {
        JButton result = new JButton("New");
        result.setBounds(800,375, 70,25);

        GuiApp.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        result.addActionListener(e -> {
            makeNewQuestionFunc();
        });

        return result;
    }

    void makeNewQuestionFunc() {
        Question newQuestion = newTemplateQuestion();
        actions.addQuestionToTarget(newQuestion);
        saveChangesToTargetQuestion();
        actions.setTargetQuestion(newQuestion);
        updateQuestionListGui();
        questionList.setSelectedIndex(actions.getTargetQuiz().getQuestions().size() - 1);

        loadTargetQuestionInGUI();
    }


    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for delete button
     */
    JButton makeDeleteQuestionBtn() {
        JButton result = new JButton("Delete");
        result.setBounds(875,375, 70,25);

        GuiApp.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0xFF0000));

        result.addActionListener(e -> {

            if (actions.getTargetQuestions().size() > 1) {
                deleteQuestionFunc();
            } else {
                GuiApp.newPopup("You must have at least one question!");
            }
        });

        return result;
    }

    void deleteQuestionFunc() {
        actions.deleteTargetQuestion();
        updateQuestionListGui();
        selectLastOptionInGuiList();
    }

    void selectLastOptionInGuiList() {
        int lastQuestionIndex = actions.getTargetQuestions().size() - 1;
        actions.setTargetQuestion(lastQuestionIndex);
        questionList.setSelectedIndex(lastQuestionIndex);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for new save changes button
     */
    JButton makeSaveChangesButton() {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton("Save Changes!");
        result.setBounds(GuiApp.centerX(WIDTH),  GuiApp.HEIGHT - GuiApp.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);

        GuiApp.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        jframe.add(result);

        result.addActionListener(e -> {
            saveChangesToTargetQuestion();
        });

        return result;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for main gui content
     */
    JPanel makeMainBodyPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(60,40,700, 300);
        jframe.add(panel);

        panel.add(makePromptLabel());

        promptInput = generatePromptInput();
        panel.add(promptInput);

        responseInput = generateResponseInput();
        panel.add(responseInput);

        panel.add(makeResponseLabel());

        return panel;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for prompt text
     */
    JLabel makePromptLabel() {
        JLabel promptLabel = new JLabel("Prompt:");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,0, 150,40);

        return promptLabel;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for prompt input
     */
    JTextField generatePromptInput() {
        JTextField result = new JTextField();
        result.setBounds(0,50, 500, 40);
        return result;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for response text
     */
    JLabel makeResponseLabel() {
        JLabel promptLabel = new JLabel("Keywords: (separate keywords by newline)");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,100, 500,40);

        return promptLabel;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns gui for response input
     */
    JTextArea generateResponseInput() {
        JTextArea result = new JTextArea();
        result.setBounds(0,150, 500, 150);
        return result;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns template for new quiz
     */
    Quiz newTemplateQuiz() {
        ArrayList<Question> questionsList = new ArrayList<>();
        questionsList.add(newTemplateQuestion());

        return new Quiz("Quiz #" + (actions.getQuizzes().size() + 1), questionsList);
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns template for new question
     */
    Question newTemplateQuestion() {
        ArrayList<String> answers = new ArrayList<>();
        answers.add("Keyword #1");
        answers.add("Keyword #2");
        answers.add("Keyword #3");

        return new FreeResponse("Question Prompt", answers);
    }
}
