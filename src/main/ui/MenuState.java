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
    JLabel titleImage;

    public MenuState(JFrame jframe, StateManager stateManager) {
        this.jframe = jframe;
        this.stateManager = stateManager;

        makeMenuButtons();
        makeTitle();
        makeTitleImage();
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
        final int WIDTH = 500;
        final int HEIGHT = 50;

        titleLabel = new JLabel("JAVA QUIZ APPLICATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        titleLabel.setBounds(SwingGui.centerX(WIDTH),  SwingGui.centerY(HEIGHT) - 100, WIDTH, HEIGHT);

        jframe.add(titleLabel);
    }

    void makeTitleImage() {
        final double SCALE = 0.3;
        final int WIDTH = (int)(500 * SCALE);
        final int HEIGHT = (int)(295 * SCALE);
        final int OFFSET_X = 0;
        final int OFFSET_Y = -145;
        ImageIcon image = new ImageIcon("./src/main/ui/img/star.gif");
        Image scaleImage = image.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

        titleImage = new JLabel();
        titleImage.setIcon(new ImageIcon(scaleImage));

        titleImage.setBounds(
                SwingGui.centerX(WIDTH) + OFFSET_X,
                SwingGui.centerY(HEIGHT) + OFFSET_Y,
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
