package ui;

import ui.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private static final String FILE_PATH = "./data/gui-data.json";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;
    public static final int TITLE_BAR_BIAS = 40;

    private final GuiAppFunctions actions;

    public Gui() {
        actions = new GuiAppFunctions(FILE_PATH);
        loadJFrame();
        loadStates();

        setVisible(true);
    }

    void loadJFrame() {
        setLayout(null);
        setTitle("Java Quiz App");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("./src/main/ui/img/thumbnail.png");
        setIconImage(image.getImage());
    }

    void loadStates() {
        new StateManager(this, actions);
    }

    public static int centerX(int width) {
        return (WIDTH - width) / 2;
    }

    public static int centerY(int height) {
        return (HEIGHT - height - 40) / 2;
    }

    public static void newPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void applyCustomButtonStyle(JButton btn) {
        btn.setBackground(new Color(252, 186, 3));
        removeButtonOutline(btn);
    }

    public static void removeButtonOutline(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
    }
}