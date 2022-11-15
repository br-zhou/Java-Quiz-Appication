package ui;

import javax.swing.*;
import java.awt.*;

public class NewQuizState extends GuiState {
    private static final int PANEL_WIDTH = 300;

    JButton newQuizButton;
    JPanel listPanel;

    public NewQuizState(JFrame jframe, StateManager stateManager) {
        super(jframe, stateManager);

        createElements();
        addEvenListeners();
        setContentVisibility(false);
    }

    @Override
    public void setContentVisibility(boolean value) {
        newQuizButton.setVisible(value);
        listPanel.setVisible(value);
    }

    void createElements() {
        newQuizButton = generateMenuButton("Add Question", 175);
        listPanel = generateListPanel();

        for (int i = 0; i < 50; i++) {
            JButton btn = generateListButton("Button" + i, i);
            listPanel.add(btn);
        }

    }

    void addEvenListeners() {
        newQuizButton.addActionListener(e -> {

        });
    }

    JPanel generateListPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, PANEL_WIDTH, SwingGui.HEIGHT - 40);
        jframe.add(panel);

        return panel;
    }

    JButton generateListButton(String text, int index) {
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(0,  index * HEIGHT,  PANEL_WIDTH, HEIGHT);
        result.setBackground(new Color(252, 186, 3));

        return result;
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
