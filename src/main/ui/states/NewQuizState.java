package ui.states;

import ui.Gui;
import ui.GuiState;

import javax.swing.*;
import java.awt.*;

public class NewQuizState extends GuiState {
    JButton newQuizBtn;
    JButton newQuestionBtn;
    JButton deleteQuestionBtn;
    JList<String> questionsList;
    DefaultListModel<String> listContent;
    JPanel questionsListPanel;


    public NewQuizState(JFrame jframe, StateManager stateManager) {
        super(jframe, stateManager);
        listContent = new DefaultListModel<>();


        for (int i = 0; i < 10; i++) listContent.addElement("Question #" + i);

        createGuiElements();
        addEventListeners();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        newQuizBtn.setVisible(value);
        questionsListPanel.setVisible(value);
    }

    void createGuiElements() {
        newQuizBtn = generateBackToMenuButton();
        questionsListPanel = makeQuestionsListPanel();
    }

    void addEventListeners() {
        newQuizBtn.addActionListener(e -> {

        });

        questionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println(questionsList.getSelectedIndex());
                System.out.println(questionsList.getSelectedValue());
            }
        });
    }

    JPanel makeQuestionsListPanel() {
        JPanel panel = Gui.newEmptyPanel(jframe);

        questionsList = makeQuestionsJList();
        JScrollPane scrollPane = new JScrollPane(questionsList,
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
        JList<String> list = new JList<>(listContent);
        list.setSelectionInterval(0,0);
        jframe.add(list);

        jframe.repaint();

        return list;
    }

    JButton makeNewQuestionBtn() {
        JButton result = new JButton("New");
        result.setBounds(800,375, 70,25);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        return result;
    }

    JButton makeDeleteQuestionBtn() {
        JButton result = new JButton("Delete");
        result.setBounds(875,375, 70,25);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0xFF0000));

        return result;
    }

    JButton generateBackToMenuButton() {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton("Create New Quiz!");
        result.setBounds(Gui.centerX(WIDTH),  Gui.HEIGHT - Gui.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);

        Gui.removeButtonOutline(result);
        result.setForeground(Color.white);
        result.setBackground(new Color(0x57D188));

        jframe.add(result);

        return result;
    }

}
