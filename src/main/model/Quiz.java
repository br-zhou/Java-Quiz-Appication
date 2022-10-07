package model;

import model.questions.*;

import java.util.ArrayList;

public class Quiz {
    String name;
    ArrayList<Question> questions;

    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public void takeQuiz() {
        // stub;
    }

    public void printScore() {
        // stub;
    }
}
