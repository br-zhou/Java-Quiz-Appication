package ui.states;

import model.AppFunctions;
import model.Quiz;
import ui.GuiApp;

import javax.swing.*;
import java.awt.*;

// State that represents 'load quizzes' page
public class QuizzesListState extends GuiState {
    JButton selectQuizBtn;
    JList<String> quizListGui;
    DefaultListModel<String> listContent;
    JPanel mainBodyPanel;
    JList<String> list;

    // REQUIRES: given objects must be non-null
    // EFFECTS: creates a new GUI state representing main menu page
    public QuizzesListState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
        super(jframe, stateManager, actions);
        listContent = new DefaultListModel<>();

        createGuiElements();
        setContentVisibility(false);
    }

    // EFFECTS: Sets related GUI elements' visibility to target value
    @Override
    public void setContentVisibility(boolean value) {
        selectQuizBtn.setVisible(value);
        mainBodyPanel.setVisible(value);
    }

    // EFFECTS: Loads elements and sets selected item of quiz list to first tiem
    @Override
    public void loadState() {
        loadQuizListGui();
        list.setSelectedIndex(0);

        super.loadState();
    }

    // EFFECTS: Loads the GUI list
    void loadQuizListGui() {
        listContent.clear();
        for (Quiz quiz : actions.getQuizzes()) {
            listContent.addElement(quiz.getName());
        }
    }

    // EFFECTS: creates state related GUI
    void createGuiElements() {
        selectQuizBtn = makeSelectQuizButton();
        mainBodyPanel = makeMainBodyPanel();
    }

    // EFFECTS: makes and returns GUI list for quizzes
    JList<String> makeQuizzesJList() {
        list = new JList<>(listContent);
        jframe.add(list);

        return list;
    }

    // EFFECTS: makes and returns 'select button'
    JButton makeSelectQuizButton() {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton("Edit Quiz!");
        result.setBounds(GuiApp.centerX(WIDTH),  GuiApp.HEIGHT - GuiApp.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);

        GuiApp.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        jframe.add(result);

        result.addActionListener(e -> {
            actions.setTargetQuiz(list.getSelectedIndex());
            stateManager.gotoState(StateManager.State.EDIT_QUIZ);
        });

        return result;
    }

    // EFFECTS: makes and returns main body panel
    JPanel makeMainBodyPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(GuiApp.centerX(300),40,700, 300);
        jframe.add(panel);

        panel.add(makeTitleLabel());

        quizListGui = makeQuizzesJList();
        JScrollPane scrollPane = new JScrollPane(quizListGui,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBounds(0,50, 300, 240);
        panel.add(scrollPane);

        return panel;
    }

    // EFFECTS: makes title text
    JLabel makeTitleLabel() {
        JLabel promptLabel = new JLabel("Quizzes:");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,0, 150,40);

        return promptLabel;
    }
}
