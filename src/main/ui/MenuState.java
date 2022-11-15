package ui;

import javax.swing.*;
import java.awt.*;

public class MenuState extends State {
    JFrame jframe;
    StateManager stateManager;
    JButton newQuizButton;
    JButton takeQuizButton;
    JButton loadDataButton;
    JButton saveDataButton;
    JLabel titleLabel;

    public MenuState(JFrame jframe, StateManager stateManager) {
        this.jframe = jframe;
        this.stateManager = stateManager;

        makeMenuButtons();
        makeTitle();
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
        takeQuizButton.setVisible(value);
        loadDataButton.setVisible(value);
        saveDataButton.setVisible(value);
        titleLabel.setVisible(value);
    }

    void makeMenuButtons() {
        newQuizButton = generateMenuButton("New Quiz", 0);
        takeQuizButton = generateMenuButton("Take Quiz", 50);
        loadDataButton = generateMenuButton("Load Data", 100);
        saveDataButton = generateMenuButton("Save Data", 150);
    }

    void makeTitle() {
        titleLabel = new JLabel();
        titleLabel.setText("HIdddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        jframe.revalidate();
        jframe.add(titleLabel);
    }

    void addEvenListeners() {
        newQuizButton.addActionListener(e -> {
            stateManager.gotoState("New Quiz");
        });

        loadDataButton.addActionListener(e -> {
            SwingGui.newPopup("Data NOT loaded hehe!");
        });

        saveDataButton.addActionListener(e -> {
            SwingGui.newPopup("Data NOT save hehe!");
        });
    }

    JButton generateMenuButton(String text, int centerOffsetY) {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(SwingGui.centerX(WIDTH),  SwingGui.centerY(HEIGHT) + centerOffsetY, WIDTH, HEIGHT);

        result.setBackground(new Color(252, 186, 3));

        jframe.add(result);

        return result;
    }
}
