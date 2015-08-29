package scores;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table of high scores - player name + score.
 */
public class HighScoresTable implements Serializable {
    private static final int SCORES_MAX = 5;
    private int size;
    private List<ScoreInfo> scores = new ArrayList<ScoreInfo>();

    /**
     * Create an empty high-scores table with the specified size.
     *
     * @param size number of top scores to hold
     */
    public HighScoresTable(int size) {
        this.size = size;
    }

    /**
     * Read a table from file and return it.
     *
     * @param file is the file to read from
     * @return the scores table
     * @throws IOException problem with file
     */
    public static HighScoresTable loadFromFile(File file) throws IOException {
        ObjectInputStream stream = null;

        // No file yet - create empty table
        if (!file.exists()) {
            return new HighScoresTable(SCORES_MAX);
        }

        // Try to open the file and read from it
        try {
            stream = new ObjectInputStream(new FileInputStream(file));
            return (HighScoresTable) stream.readObject();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No such class");

        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * Add a high-score if it fits.
     *
     * @param score score to add
     */
    public void add(ScoreInfo score) {
        if (isToAdd(score.getScore())) {
            scores.add(getRank(score.getScore()) - 1, score);
        }
        if (scores.size() > size) {
            scores.remove(size);
        }
    }

    /**
     * Returns the place number of a score in the table.
     *
     * @param score the score to be checked
     * @return proper place
     */
    public int getRank(int score) {
        // Go over the scores and find proper place
        int place = 1;
        for (ScoreInfo scoreInfo : scores) {
            if (score < scoreInfo.getScore()) {
                place++;
            } else {
                return place;
            }
        }

        // Return the last place
        return scores.size() + 1;
    }

    /**
     * Tell whether the score should be added to the table.
     *
     * @param newScore score of the player to check
     * @return check result
     */
    public boolean isToAdd(int newScore) {
        int lastIndex = scores.size() - 1;

        // Add if table is not full or score is bigger than the last high-score
        return (scores.size() < size)
                || (newScore >= scores.get(lastIndex).getScore());
    }

    /**
     * Return table size.
     *
     * @return size of the table
     */
    public int size() {
        return size;
    }

    /**
     * Return a sorted list of high scores.
     *
     * @return sorted list of high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return scores;
    }

    /**
     * Save table data to the specified file.
     *
     * @param file file to save to
     * @throws IOException any IO exception
     */
    public void save(File file) throws IOException {
        ObjectOutputStream stream = null;

        // Try to save object into the file
        try {
            stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeObject(this);

        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}