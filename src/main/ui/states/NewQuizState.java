package ui.states;

import model.AppLogic;
import ui.Gui;

import javax.swing.*;
import java.awt.*;

public class NewQuizState extends GuiState {
    JButton newQuizBtn;
    JButton newQuestionBtn;
    JButton deleteQuestionBtn;
    JList<String> questionsList;
    DefaultListModel<String> listContent;
    JPanel questionsListPanel;
    JPanel mainBodyPanel;
    JTextField promptInput;
    JTextArea responseInput;


    public NewQuizState(JFrame jframe, StateManager stateManager, AppLogic actions) {
        super(jframe, stateManager, actions);
        listContent = new DefaultListModel<>();


        for (int i = 0; i < 10; i++) {
            listContent.addElement("Question #" + i);
        }

        createGuiElements();
        addEventListeners();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        newQuizBtn.setVisible(value);
        questionsListPanel.setVisible(value);
        mainBodyPanel.setVisible(value);
    }

    void createGuiElements() {
        newQuizBtn = generateBackToMenuButton();
        mainBodyPanel = makeMainBodyPanel();
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
        JPanel panel = new JPanel(null);
        panel.setBounds(0,0,Gui.WIDTH, Gui.HEIGHT);
        jframe.add(panel);

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

    JPanel makeMainBodyPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(60,60,700, 300);
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

}
