package model.questions;

public abstract class Question {
    private String prompt;
    protected boolean answeredCorrectly;

    Question(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public abstract void answerQuestion();

    @Override
    public String toString() {
        return prompt;
    }
}
