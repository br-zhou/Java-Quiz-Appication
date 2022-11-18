package ui.states;

import model.AppLogic;
import ui.states.StateManager;

import javax.swing.*;

public abstract class GuiState {
    protected JFrame jframe;
    protected StateManager stateManager;
    protected AppLogic actions;

    public GuiState(JFrame jframe, StateManager stateManager, AppLogic actions) {
        this.jframe = jframe;
        this.stateManager = stateManager;
        this.actions = actions;
    }

    public abstract void setContentVisibility(boolean value);

    public void loadState() {
        setContentVisibility(true);
    }

    public void unloadState() {
        setContentVisibility(false);
    }
}
