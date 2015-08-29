package scores;

import java.io.Serializable;

/**
 * Holds a player's score information - name and score.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Creates the score information.
     *
     * @param name  name of the player
     * @param score the score of the player
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returnes the player's name.
     *
     * @return name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the player's score.
     *
     * @return score of the player
     */
    public int getScore() {
        return this.score;
    }
}
