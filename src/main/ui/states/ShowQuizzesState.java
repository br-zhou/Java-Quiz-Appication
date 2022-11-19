package ui.states;

import ui.GuiAppFunctions;
import model.Quiz;
import ui.Gui;

import javax.swing.*;
import java.awt.*;

public class ShowQuizzesState extends GuiState {
    JButton selectQuizBtn;
    JList<String> quizListGui;
    DefaultListModel<String> listContent;
    JPanel mainBodyPanel;
    JList<String> list;

    public ShowQuizzesState(JFrame jframe, StateManager stateManager, GuiAppFunctions actions) {
        super(jframe, stateManager, actions);
        listContent = new DefaultListModel<>();

        createGuiElements();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        selectQuizBtn.setVisible(value);
        mainBodyPanel.setVisible(value);
    }

    @Override
    public void loadState() {
        redrawQuestionListGui();
        list.setSelectedIndex(0);

        super.loadState();
    }

    void redrawQuestionListGui() {

        listContent.clear();
        for (Quiz quiz : actions.getQuizzes()) {
            listContent.addElement(quiz.getName());
        }
    }

    void createGuiElements() {
        selectQuizBtn = makeSelectQuizButton();
        mainBodyPanel = makeMainBodyPanel();
    }

    JList<String> makeQuizzesJList() {
        list = new JList<>(listContent);
        jframe.add(list);

        return list;
    }

    JButton makeSelectQuizButton() {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton("Edit Quiz!");
        result.setBounds(Gui.centerX(WIDTH),  Gui.HEIGHT - Gui.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        jframe.add(result);

        result.addActionListener(e -> {
            stateManager.setEditingQuizTarget(actions.getQuizAtIndex(list.getSelectedIndex()));
            stateManager.gotoState(StateManager.State.EDIT_QUIZ);
        });

        return result;
    }

    JPanel makeMainBodyPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(Gui.centerX(300),40,700, 300);
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

    JLabel makeTitleLabel() {
        JLabel promptLabel = new JLabel("Quizzes:");
        promptLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        promptLabel.setBounds(0,0, 150,40);

        return promptLabel;
    }
}
