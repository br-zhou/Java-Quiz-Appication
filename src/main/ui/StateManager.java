package ui;

public class StateManager {
    State currentState;

    StateManager(State initialState) {
        currentState = initialState;
        currentState.loadState();
    }

    public void gotoState(State state) {
        currentState.unloadState();
        state.loadState();
    }

}
