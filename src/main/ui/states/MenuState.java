package ui.states;

import ui.Gui;

import javax.swing.*;
import java.awt.*;

public class MenuState extends GuiState {
    JButton newQuizButton;
    JButton takeQuizButton;
    JButton loadDataButton;
    JButton saveDataButton;
    JButton backToMenuButton;
    JLabel titleLabel;
    JLabel titleImage;

    public MenuState(JFrame jframe, StateManager stateManager) {
        super(jframe, stateManager);

        makeMenuButtons();
        makeTitle();
        makeTitleImage();
        addEvenListeners();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        newQuizButton.setVisible(value);
        takeQuizButton.setVisible(value);
        loadDataButton.setVisible(value);
        saveDataButton.setVisible(value);
        titleLabel.setVisible(value);
        titleImage.setVisible(value);

        backToMenuButton.setVisible(!value);
    }

    void makeMenuButtons() {
        newQuizButton = generateMenuButton("New Quiz", 0);
        takeQuizButton = generateMenuButton("Take Quiz", 50);
        loadDataButton = generateMenuButton("Load Data", 100);
        saveDataButton = generateMenuButton("Save Data", 150);
        backToMenuButton = generateBackButton();
    }

    void makeTitle() {
        final int WIDTH = 500;
        final int HEIGHT = 50;

        titleLabel = new JLabel("JAVA QUIZ APPLICATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        titleLabel.setBounds(Gui.centerX(WIDTH),  Gui.centerY(HEIGHT) - 100, WIDTH, HEIGHT);

        jframe.add(titleLabel);
    }

    void makeTitleImage() {
        final double SCALE = 0.3;
        final int WIDTH = (int)(500 * SCALE);
        final int HEIGHT = (int)(295 * SCALE);
        final int OFFSET_X = 0;
        final int OFFSET_Y = -160;
        ImageIcon image = new ImageIcon("./src/main/ui/img/star.gif");
        Image scaleImage = image.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

        titleImage = new JLabel();
        titleImage.setIcon(new ImageIcon(scaleImage));

        titleImage.setBounds(
                Gui.centerX(WIDTH) + OFFSET_X,
                Gui.centerY(HEIGHT) + OFFSET_Y,
                WIDTH,
                HEIGHT
        );

        jframe.add(titleImage);
    }

    void addEvenListeners() {
        newQuizButton.addActionListener(e -> {
            stateManager.gotoState("New Quiz");
        });

        loadDataButton.addActionListener(e -> {
            Gui.newPopup("Data loaded successfully!");
        });

        saveDataButton.addActionListener(e -> {
            Gui.newPopup("Data saved successfully!");
        });

        backToMenuButton.addActionListener(e -> {
            stateManager.gotoState("Menu");
        });
    }

    JButton generateMenuButton(String text, int centerOffsetY) {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(Gui.centerX(WIDTH),  Gui.centerY(HEIGHT) + centerOffsetY, WIDTH, HEIGHT);

        Gui.applyCustomButtonStyle(result);

        jframe.add(result);

        return result;
    }

    JButton generateBackButton() {
        final int WIDTH = 150;
        final int HEIGHT = 40;

        JButton result = new JButton("Back to Menu");
        result.setBounds(25,  Gui.HEIGHT - Gui.TITLE_BAR_BIAS - HEIGHT - 25, WIDTH, HEIGHT);
        Gui.removeButtonOutline(result);

        result.setForeground(Color.white);
        result.setBackground(new Color(0x5A5A5A));
        jframe.add(result);

        return result;
    }
}
