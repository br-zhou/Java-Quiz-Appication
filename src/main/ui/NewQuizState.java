package ui;

import javax.swing.*;

public class NewQuizState extends State {
    JFrame jframe;
    StateManager stateManager;
    JButton newQuizButton;

    public NewQuizState(JFrame jframe, StateManager stateManager) {
        this.jframe = jframe;
        this.stateManager = stateManager;

        makeMenuButtons();
        addEvenListeners();
        setContentVisibility(false);
    }

    @Override
    public void loadState() {
        setContentVisibility(true);
    }

    @Override
    public void unloadState() {
        setContentVisibility(false);
    }

    public void setContentVisibility(boolean value) {
        newQuizButton.setVisible(value);
    }

    void makeMenuButtons() {
        newQuizButton = generateMenuButton("Add Question", 175);
    }

    void addEvenListeners() {
        newQuizButton.addActionListener(e -> {

        });
    }

    JButton generateMenuButton(String text, int centerOffsetY) {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(SwingGui.centerX(WIDTH),  SwingGui.centerY(HEIGHT) + centerOffsetY, WIDTH, HEIGHT);

        jframe.add(result);

        return result;
    }
}
