package ui.states;

import model.AppFunctions;
import model.Quiz;
import model.questions.FreeResponse;
import model.questions.Question;
import ui.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    JList<String> list;

    Quiz targetQuiz;
    Question targetQuestion;

    public EditQuizState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
        super(jframe, stateManager, actions);
        listContent = new DefaultListModel<>();

        targetQuiz = null;

        createGuiElements();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        saveChangesBtn.setVisible(value);
        questionsListPanel.setVisible(value);
        mainBodyPanel.setVisible(value);
    }

    @Override
    public void loadState() {
        if (targetQuiz == null) {
            targetQuiz = newTemplateQuiz();
            actions.addQuiz(targetQuiz);
        }
        redrawQuestionListGui();
        list.setSelectedIndex(0);
        selectQuestionInList(0);

        super.loadState();
    }

    void redrawQuestionListGui() {
        List<Question> listQuestions = targetQuiz.getQuestions();

        listContent.clear();
        for (int i = 0; i < listQuestions.size(); i++) {
            listContent.addElement(listQuestions.get(i).getPrompt());
        }

        selectQuestionInList(targetQuiz.getQuestions().size() - 1);
    }

    void selectQuestionInList(int index) {
        targetQuestion = targetQuiz.getQuestions().get(index);
        loadTargetQuestion();
    }

    void loadTargetQuestion() {
        promptInput.setText(targetQuestion.getPrompt());

        FreeResponse question = (FreeResponse) targetQuestion;
        StringBuilder responseContent = new StringBuilder();
        for (String keyword : question.getKeywords()) {
            responseContent.append(keyword + '\n');
        }

        responseInput.setText(responseContent.toString());
    }

    void createGuiElements() {
        saveChangesBtn = makeSaveChangesButton();
        mainBodyPanel = makeMainBodyPanel();
        questionsListPanel = makeQuestionsListPanel();
    }

    void saveChangesToTargetQuestion() {
        updateTargetQuestion(promptInput.getText(), responseInput.getText());
    }

    void updateTargetQuestion(String newPrompt, String newAnswers) {
        FreeResponse question = (FreeResponse)targetQuestion;
        question.setPrompt(newPrompt);
        question.setKeywords(strToKeywords(newAnswers));
    }

    List<String> strToKeywords(String str) {
        String[] keywordsArr = str.split("\\r?\\n");
        return Arrays.asList(keywordsArr);
    }

    JPanel makeQuestionsListPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(0,0,Gui.WIDTH, Gui.HEIGHT);
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

    JList<String> makeQuestionsJList() {
        list = new JList<>(listContent);
        jframe.add(list);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && questionsListGui.getSelectedIndex() >= 0) {
                saveChangesToTargetQuestion();
                targetQuestion = targetQuiz.getQuestions().get(questionsListGui.getSelectedIndex());
                loadTargetQuestion();
                updateGuiListTitles();
            }
        });

        return list;
    }

    void updateGuiListTitles() {
        for (int i = 0; i < listContent.size(); i++) {
            listContent.set(i, getQuestionPrompt(i));
        }
    }

    String getQuestionPrompt(int i) {
        return targetQuiz.getQuestions().get(i).getPrompt();
    }

    JButton makeNewQuestionBtn() {
        JButton result = new JButton("New");
        result.setBounds(800,375, 70,25);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        result.addActionListener(e -> {
            Question newQuestion = newTemplateQuestion();
            targetQuiz.addQuestion(newQuestion);
            saveChangesToTargetQuestion();
            targetQuestion = newQuestion;
            redrawQuestionListGui();
            list.setSelectedIndex(targetQuiz.getQuestions().size() - 1);

            loadTargetQuestion();
        });

        return result;
    }

    JButton makeDeleteQuestionBtn() {
        JButton result = new JButton("Delete");
        result.setBounds(875,375, 70,25);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0xFF0000));

        result.addActionListener(e -> {

            if (targetQuiz.getQuestions().size() > 0) {
                targetQuiz.deleteQuestion(targetQuestion);
                redrawQuestionListGui();
                int lastQuestionIndex = targetQuiz.getQuestions().size() - 1;
                targetQuestion = targetQuiz.getQuestions().get(lastQuestionIndex);
                list.setSelectedIndex(lastQuestionIndex);
            } else {
                Gui.newPopup("There are no questions to delete!");
            }
        });

        return result;
    }

    JButton makeSaveChangesButton() {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton("Save Changes!");
        result.setBounds(Gui.centerX(WIDTH),  Gui.HEIGHT - Gui.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        jframe.add(result);

        result.addActionListener(e -> {
            saveChangesToTargetQuestion();
        });

        return result;
    }

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

    JLabel makePromptLabel() {
        JLabel promptLabel = new JLabel("Prompt:");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,0, 150,40);

        return promptLabel;
    }

    JTextField generatePromptInput() {
        JTextField result = new JTextField();
        result.setBounds(0,50, 500, 40);
        return result;
    }

    JLabel makeResponseLabel() {
        JLabel promptLabel = new JLabel("Keywords: (separate keywords by newline)");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,100, 500,40);

        return promptLabel;
    }

    JTextArea generateResponseInput() {
        JTextArea result = new JTextArea();
        result.setBounds(0,150, 500, 150);
        return result;
    }

    void setTargetQuiz(Quiz quiz) {
        targetQuiz = quiz;
    }

    Quiz newTemplateQuiz() {
        ArrayList<Question> questionsList = new ArrayList<>();
        questionsList.add(newTemplateQuestion());

        return new Quiz("Quiz #" + (actions.getQuizzes().size() + 1), questionsList);
    }

    Question newTemplateQuestion() {
        ArrayList<String> answers = new ArrayList<>();
        answers.add("Keyword #1");
        answers.add("Keyword #2");
        answers.add("Keyword #3");

        return new FreeResponse("Question Prompt", answers);
    }
}
