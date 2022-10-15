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

    public int getMaxScore() {
        return maxScore;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return String.format("%s / %s", score, maxScore);
    }
}