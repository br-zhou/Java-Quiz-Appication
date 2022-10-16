package model;

// Represents results of quizzes, with a score and maximum score
public class Result {
    private int score;
    private final int maxScore;

    /*
     * REQUIRES: maxScore > 0
     * EFFECTS: max possible score is set to maxScore
     *          score set to 0
     */
    public Result(int maxScore) {
        this.maxScore = maxScore;
        this.score = 0;
    }

    /*
     * REQUIRES: score < maxScore
     * MODIFIES: this
     * EFFECTS: increases score by 1
     */
    public void incrementScore() {
        this.score += 1;
    }

    /*
     * EFFECTS: Returns score and max score as a string
     */
    public String toString() {
        return String.format("%s / %s", score, maxScore);
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getScore() {
        return score;
    }
}