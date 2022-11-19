package ui.states;

import exceptions.WriteErrorException;
import model.AppFunctions;
import ui.Gui;

import javax.swing.*;
import java.awt.*;

public class MenuState extends GuiState {
    JPanel mainPanel;
    JButton backToMenuButton;

    public MenuState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
        super(jframe, stateManager, actions);

        createMainPanel();
        makeMenuButtons();
        makeTitle();
        makeTitleImage();
        setContentVisibility(false);
    }

    // Toggles visibility of content
    @Override
    public void setContentVisibility(boolean value) {
        mainPanel.setVisible(value);

        backToMenuButton.setVisible(!value);
    }

    // EFFECTS: create all menu buttons
    void makeMenuButtons() {
        makeNewQuizBtn();
        makeTakeQuizBtn();
        makeLoadDataBtn();
        makeSaveDataBtn();
        makeBackToMenuBtn();
    }

    // EFFECTS: makes panel where all other elements sit in
    void createMainPanel() {
        mainPanel = new JPanel(null);
        mainPanel.setBounds(0,0,Gui.WIDTH, Gui.HEIGHT);
        jframe.add(mainPanel);
    }

    // EFFECTS: makes and adds title to panel
    void makeTitle() {
        final int WIDTH = 500;
        final int HEIGHT = 50;

        JLabel titleLabel = new JLabel("JAVA QUIZ APPLICATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        titleLabel.setBounds(Gui.centerX(WIDTH),  Gui.centerY(HEIGHT) - 100, WIDTH, HEIGHT);

        mainPanel.add(titleLabel);
    }

    // EFFECTS: makes and gives functionality to new quiz button
    void makeNewQuizBtn() {
        JButton btn = generateMenuButton("New Quiz", 0);

        btn.addActionListener(e -> {
            stateManager.setEditingQuizTarget(null);
            stateManager.gotoState(StateManager.State.EDIT_QUIZ);
        });
    }

    // EFFECTS: makes and gives functionality to take quiz button
    void makeTakeQuizBtn() {
        JButton btn = generateMenuButton("Take Quiz", 50);

        btn.addActionListener(e -> {
            Gui.newPopup("Functionality not added. (Not required)");
        });
    }

    // EFFECTS: makes and gives functionality to load data button
    void makeLoadDataBtn() {
        JButton btn = generateMenuButton("Load Data", 100);

        btn.addActionListener(e -> {
            loadData();
        });
    }

    // EFFECTS: makes and gives functionality to back to save data button
    void makeSaveDataBtn() {
        JButton btn = generateMenuButton("Save Data", 150);

        btn.addActionListener(e -> {
            saveData();
        });
    }

    // EFFECTS: makes and gives functionality to back to menu button
    void makeBackToMenuBtn() {
        backToMenuButton = generateBackButton();

        backToMenuButton.addActionListener(e -> {
            stateManager.gotoState(StateManager.State.MENU);
        });
    }

    // EFFECTS: creates and adds image to panel
    void makeTitleImage() {
        final double SCALE = 0.3;
        final int WIDTH = (int)(500 * SCALE);
        final int HEIGHT = (int)(295 * SCALE);
        final int OFFSET_X = 0;
        final int OFFSET_Y = -160;
        ImageIcon image = new ImageIcon("./src/main/ui/img/star.gif");
        Image scaleImage = image.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

        JLabel titleImage = new JLabel();
        titleImage.setIcon(new ImageIcon(scaleImage));

        titleImage.setBounds(
                Gui.centerX(WIDTH) + OFFSET_X,
                Gui.centerY(HEIGHT) + OFFSET_Y,
                WIDTH,
                HEIGHT
        );

        mainPanel.add(titleImage);
    }

    // EFFECTS: creates, adds to panel, and returns new menu
    JButton generateMenuButton(String text, int centerOffsetY) {
        final int WIDTH = 250;
        final int HEIGHT = 40;

        JButton result = new JButton(text);
        result.setBounds(Gui.centerX(WIDTH),  Gui.centerY(HEIGHT) + centerOffsetY, WIDTH, HEIGHT);

        Gui.applyCustomButtonStyle(result);

        mainPanel.add(result);

        return result;
    }

    // EFFECTS: creates, adds to panel, and returns new back button
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


    /*
     * MODIFIES: this
     * EFFECTS: saves quizzes to storage
     *          gives popup warning if unable to save resource
     */
    void saveData() {
        try {
            actions.pushDataToStorage();
            Gui.newPopup("Data saved successfully!");
        } catch (WriteErrorException e) {
            Gui.newPopup("An error occurred while trying to save data.");

        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads quizzes from storage
     *          gives popup warning if unable to load resource
     */
    public void loadData() {
        try {
            actions.pullDataFromStorage();
            Gui.newPopup("Data saved successfully!");
        } catch (Exception e) {
            Gui.newPopup("An error occurred while trying to load data.");
        }
    }
}
