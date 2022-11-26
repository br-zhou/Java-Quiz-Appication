package ui.states;

import model.AppFunctions;

import javax.swing.*;

// Base class for representing a GUI page
public abstract class GuiState {
    protected JFrame jframe;
    protected StateManager stateManager;
    protected AppFunctions actions;

    // REQUIRES: given objects must be non-null
    // EFFECTS: creates a new GUI state representing a page
    public GuiState(JFrame jframe, StateManager stateManager, AppFunctions actions) {
        this.jframe = jframe;
        this.stateManager = stateManager;
        this.actions = actions;
    }

    // EFFECTS: sets visibility of related GUI to given value
    public abstract void setContentVisibility(boolean value);

    // EFFECTS: make all state related GUI visible
    public void loadState() {
        setContentVisibility(true);
    }

    // EFFECTS: makes all state related GUI invisible
    public void unloadState() {
        setContentVisibility(false);
    }
}
