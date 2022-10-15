package model;

public class Result {
    private int score;
    private final int maxScore;

    public Result(int maxScore) {
        this.maxScore = maxScore;
    }

    public void incrementScore() {
        this.score += 1;
    }

    public String toString() {
        return String.format("Test results: %s / %s", score, maxScore);
    }
}