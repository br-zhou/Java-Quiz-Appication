package ui.states;

import model.AppLogic;

import javax.swing.*;
import java.util.HashMap;

public class StateManager {
    GuiState currentState;
    HashMap<String, GuiState> stateHash;

    public StateManager(JFrame jframe, AppLogic actions) {
        stateHash = new HashMap<>();

        addState("Menu", new MenuState(jframe, this, actions));
        addState("New Quiz", new NewQuizState(jframe, this, actions));
    }

    void setInitialState(GuiState state) {
        currentState = state;
        currentState.loadState();
    }

    public void addState(String name, GuiState state) {
        stateHash.put(name, state);

        if (stateHash.size() == 1) {
            setInitialState(state);
        }
    }

    public void gotoState(String name) {
        currentState.unloadState();
        currentState = stateHash.get(name);
        currentState.loadState();
    }
}
