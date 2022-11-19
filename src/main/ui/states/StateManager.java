package ui.states;

import model.AppFunctions;
import model.Quiz;

import javax.swing.*;
import java.util.HashMap;

public class StateManager {
    public enum State {
        MENU,
        EDIT_QUIZ,
        SHOW_QUIZZES
    }

    GuiState currentState;
    HashMap<Enum<?>, GuiState> stateHash;

    MenuState menuState;
    EditQuizState editQuizState;
    ShowQuizzesState showQuizzesState;

    public StateManager(JFrame jframe, AppFunctions actions) {
        stateHash = new HashMap<>();
        menuState = new MenuState(jframe, this, actions);
        editQuizState = new EditQuizState(jframe, this, actions);
        showQuizzesState = new ShowQuizzesState(jframe, this, actions);

        addState(State.MENU, menuState);
        addState(State.EDIT_QUIZ, editQuizState);
        addState(State.SHOW_QUIZZES, showQuizzesState);
    }

    void setInitialState(GuiState state) {
        currentState = state;
        currentState.loadState();
    }

    public void addState(State name, GuiState state) {
        stateHash.put(name, state);

        if (stateHash.size() == 1) {
            setInitialState(state);
        }
    }

    public void gotoState(State name) {
        currentState.unloadState();
        currentState = stateHash.get(name);
        currentState.loadState();
    }

    public void setEditingQuizTarget(Quiz quiz) {
        editQuizState.setTargetQuiz(quiz);
    }
}
