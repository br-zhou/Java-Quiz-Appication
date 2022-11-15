package ui;

import javax.swing.*;
import java.util.HashMap;

public class StateManager {
    State currentState;
    HashMap<String, State> stateHash;

    StateManager(JFrame jframe) {
        stateHash = new HashMap<>();

        addState("Menu", new MenuState(jframe, this));
        addState("New Quiz", new NewQuizState(jframe, this));
    }

    void setInitialState(State state) {
        currentState = state;
        currentState.loadState();
    }

    public void addState(String name, State state) {
        stateHash.put(name, state);

        if (stateHash.size() == 1) {
            setInitialState(state);
        }
    }

    public void gotoState(String name) {
        currentState.unloadState();
        stateHash.get(name).loadState();
    }
}
