package ui;

import javax.swing.*;

public class SwingGui extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private StateManager stateManager;

    public SwingGui() {
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
}