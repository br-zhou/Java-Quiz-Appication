package ui;

import ui.states.StateManager;

import javax.swing.*;

public abstract class GuiState {
    protected JFrame jframe;
    protected StateManager stateManager;

    public GuiState(JFrame jframe, StateManager stateManager) {
        this.jframe = jframe;
        this.stateManager = stateManager;
    }

    public abstract void setContentVisibility(boolean value);

    public void loadState() {
        setContentVisibility(true);
    }

    public void unloadState() {
        setContentVisibility(false);
    }
}
