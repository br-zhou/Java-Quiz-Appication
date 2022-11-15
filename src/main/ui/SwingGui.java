package ui;

import javax.swing.*;
import java.awt.*;


public class SwingGui extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private StateManager stateManager;
    private State menuState;

    public SwingGui() {
        initializeJFrame();
        menuState = new MenuState(this);

        stateManager = new StateManager(menuState);
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

    public static int centerX(int width) {
        return (WIDTH - width) / 2;
    }

    public static int centerY(int height) {
        return (HEIGHT - height * 2) / 2;
    }

}