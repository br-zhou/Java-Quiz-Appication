package ui.states;

import model.AppFunctions;

import javax.swing.*;

public abstract class GuiState {
    protected JFrame jframe;
    protected StateManager stateManager;
    protected AppFunctions actions;

    public GuiState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
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
