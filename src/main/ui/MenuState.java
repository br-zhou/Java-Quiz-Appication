package ui;

import javax.swing.*;

public class MenuState extends State {
    JFrame jframe;
    JButton newQuizButton;
    JButton takeQuizButton;
    JButton loadDataButton;
    JButton saveDataButton;

    public MenuState(JFrame jframe) {
        this.jframe = jframe;

        makeMenuButtons();
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
        takeQuizButton.setVisible(value);
        loadDataButton.setVisible(value);
        saveDataButton.setVisible(value);
    }

    void makeMenuButtons() {
        newQuizButton = generateMenuButton("New Quiz", 0);
        takeQuizButton = generateMenuButton("Take Quiz", 50);
        loadDataButton = generateMenuButton("Load Data", 100);
        saveDataButton = generateMenuButton("Save Data", 150);
    }

    JButton generateMenuButton(String text, int centerOffsetY) {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(SwingGui.centerX(WIDTH),  SwingGui.centerY(HEIGHT) + centerOffsetY, WIDTH, HEIGHT);

        result.setVisible(false);
        jframe.add(result);

        return result;
    }
}
