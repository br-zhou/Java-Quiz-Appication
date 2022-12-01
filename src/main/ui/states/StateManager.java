package ui.states;

import model.AppFunctions;
import model.Quiz;

import javax.swing.*;
import java.util.HashMap;

// Holds all GUI states related to program
public class StateManager {
    public enum State {
        MENU,
        EDIT_QUIZ,
        LIST_QUIZZES
    }

    GuiState currentState;
    HashMap<Enum<?>, GuiState> stateHash;

    MenuState menuState;
    EditQuizState editQuizState;
    QuizzesListState quizzesListState;

    // Creates state machine and loads all related states
    public StateManager(JFrame jframe, AppFunctions actions) {
        stateHash = new HashMap<>();
        menuState = new MenuState(jframe, this, actions);
        editQuizState = new EditQuizState(jframe, this, actions);
        quizzesListState = new QuizzesListState(jframe, this, actions);

        addState(State.MENU, menuState);
        addState(State.EDIT_QUIZ, editQuizState);
        addState(State.LIST_QUIZZES, quizzesListState);
    }

    // REQUIRES: state must be non-null
    // MODIFIES: this
    // EFFECTS: Sets currentState to given state
    void setInitialState(GuiState state) {
        currentState = state;
        currentState.loadState();
    }

    // REQUIRES: name cannot be associated with existing state
    // MODIFIES: this
    // EFFECTS: Adds state with name
    public void addState(State name, GuiState state) {
        stateHash.put(name, state);

        if (stateHash.size() == 1) {
            setInitialState(state);
        }
    }

    // REQUIRES: state cannot be null
    // MODIFIES: this
    // EFFECTS: sets current state to given state
    public void gotoState(State name) {
        currentState.unloadState();
        currentState = stateHash.get(name);
        currentState.loadState();
    }
}
