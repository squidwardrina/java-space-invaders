package utils;

/**
 * Holds all the finals needed for the game.
 */
public final class Consts {
    private static Consts instance;
    private final int framesPerSec = 60;
    private final int gameHeight = 600;
    private final int gameWidth = 800;
    private final int margin = 5;
    private final double shipHeight = 20;
    private final double shipYStart = gameHeight - shipHeight - margin;
    private final int statusBarHeight = 20;
    private final String scoresFileName = "highscores";
    private final String stopAnimationKey = "space";
    private final int lives = 3;
    private final int aliensSpeed = 80;
    private final String alienImage = "alien.png";
    private final int aliensWidth = 40;
    private final int barricadesPosition = 500;
    private final int alienDownStep = 20;
    private final int enemyFireFrequency = 500;

    /**
     * Creates the instance.
     */
    private Consts() {
    }

    /**
     * Returns the finals class instance.
     *
     * @return instance of finals class
     */
    public static Consts getInstance() {
        if (Consts.instance == null) {
            Consts.instance = new Consts();
        }
        return instance;
    }

    /**
     * Returns the name of the scores file.
     *
     * @return the name of the scores file
     */
    public String getScoresFileName() {
        return scoresFileName;
    }

    /**
     * Get game height.
     *
     * @return game screen height
     */
    public int getGameHeight() {
        return gameHeight;
    }

    /**
     * Gets game frame time.
     *
     * @return one frame time
     */
    public int getFramesPerSec() {
        return framesPerSec;
    }

    /**
     * Gets game screen width.
     *
     * @return game screen width
     */
    public int getGameWidth() {
        return gameWidth;
    }

    /**
     * Gets game screen margin.
     *
     * @return gets game margin
     */
    public int getMargin() {
        return margin;
    }

    /**
     * Gets paddle height.
     *
     * @return paddle height
     */
    public double getShipHeight() {
        return shipHeight;
    }

    /**
     * Gets paddle's start point at Y axe.
     *
     * @return paddle's y start
     */
    public double getShipYStart() {
        return shipYStart;
    }

    /**
     * Gets the height of the status bar.
     *
     * @return status bar height
     */
    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    /**
     * Gets the key that would stop animation.
     *
     * @return the key that would stop animation
     */
    public String getStopAnimationKey() {
        return stopAnimationKey;
    }

    /**
     * Gets lives number for the game.
     *
     * @return number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Returns the default alien's moving speed.
     *
     * @return default alien's moving speed.
     */
    public int getAliensSpeed() {
        return aliensSpeed;
    }

    /**
     * Returns the name of image file for the alien.
     *
     * @return the name of image file for the alien.
     */
    public String getAlienImage() {
        return alienImage;
    }

    /**
     * Gets alien width.
     *
     * @return alien width.
     */
    public int getAliensWidth() {
        return aliensWidth;
    }

    /**
     * Gets the size of alien zone. When aliens reach it's end - player looses.
     *
     * @return height of the alien zone
     */
    public int getBarricadesPosition() {
        return barricadesPosition;
    }

    /**
     * Returns the pixels number for aliens to move down when needed.
     *
     * @return moving down pixels
     */
    public int getAlienDownStep() {
        return alienDownStep;
    }

    /**
     * Returns the enemy's fire frequency in milliseconds.
     *
     * @return the enemy's fire frequency in milliseconds.
     */
    public int getEnemyFireFrequency() {
        return enemyFireFrequency;
    }
}
