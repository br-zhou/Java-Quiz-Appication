package ui;

import ui.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;
    public static final int TITLE_BAR_BIAS = 40;

    private StateManager stateManager;

    public Gui() {
        initializeJFrame();
        initializeStates();
    }

    void initializeJFrame() {
        setLayout(null);
        setTitle("Java Quiz App");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("./src/main/ui/img/thumbnail.png");
        setIconImage(image.getImage());

        setVisible(true);
    }

    void initializeStates() {
        stateManager = new StateManager(this);
    }

    public static int centerX(int width) {
        return (WIDTH - width) / 2;
    }

    public static int centerY(int height) {
        return (HEIGHT - height - 40) / 2;
    }

    public static void newPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.NO_OPTION);
    }

    public static void applyCustomButtonStyle(JButton btn) {
        btn.setBackground(new Color(252, 186, 3));
        removeButtonOutline(btn);
    }

    public static void removeButtonOutline(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

    }

    public static JPanel newEmptyPanel(JFrame jframe) {
        JPanel panel = new JPanel(null);
        panel.setBounds(0,0,Gui.WIDTH, Gui.HEIGHT);
        jframe.add(panel);
        return panel;
    }
}