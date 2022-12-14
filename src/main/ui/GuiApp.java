package ui;

import model.AppFunctions;
import model.Event;
import ui.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

// Represents the GUI frame
public class GuiApp extends JFrame {
    private static final String FILE_PATH = "./data/gui-data.json";

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;
    public static final int TITLE_BAR_BIAS = 40;

    private final AppFunctions actions;

    /*
     * EFFECTS: starts application
     */
    public static void main(String[] args) {
        new GuiApp();
    }

    // EFFECTS: creates new window with some initial state loaded
    public GuiApp() {
        actions = new AppFunctions(FILE_PATH);
        loadJFrame();
        loadStates();

        setVisible(true);
    }

    // EFFECTS: Creates frame for GUI
    void loadJFrame() {
        setLayout(null);
        setTitle("Java Quiz App");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        ImageIcon image = new ImageIcon("./src/main/ui/img/thumbnail.png");
        setIconImage(image.getImage());
    }

    // EFFECTS: prints event log to console
    void onClose() {
        Iterator<Event> eventIterator = actions.getEventIterator();

        System.out.println("Event log:");

        while (eventIterator.hasNext()) {
            System.out.println(eventIterator.next());
        }
    }

    void loadStates() {
        new StateManager(this, actions);
    }

    // EFFECTS: returns position of x coordinate to center element of given width
    public static int centerX(int width) {
        return (WIDTH - width) / 2;
    }

    // EFFECTS: returns position of y coordinate to center element of given height
    public static int centerY(int height) {
        return (HEIGHT - height - 40) / 2;
    }

    // EFFECTS: shows popup with the given message
    public static void newPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: makes color yellow and removes background
    public static void applyCustomButtonStyle(JButton btn) {
        btn.setBackground(new Color(252, 186, 3));
        removeButtonOutline(btn);
    }

    // EFFECTS: removes outlines in buttons
    public static void removeButtonOutline(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
    }
}