package gameplay;

import graphics.Point;
import sprites.collidables.Alien;
import sprites.Background;
import sprites.Fill;
import sprites.Sprite;
import utils.Consts;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * One level of the game.
 */
public class Battle implements LevelInformation {
    private int levelNum;

    /**
     * Creates new battle level.
     *
     * @param levelNum number of level
     */
    public Battle(int levelNum) {
        this.levelNum = levelNum;
    }

    /**
     * Gets aliens speed.
     *
     * @return the aliens speed
     */
    @Override
    public int aliensSpeed() {
        return Consts.getInstance().getAliensSpeed() * levelNum;
    }

    /**
     * Ship's speed.
     *
     * @return speed of the paddle for the level
     */
    @Override
    public int shipSpeed() {
        return 450;
    }

    /**
     * Ship's with.
     *
     * @return width of the paddle for the level
     */
    @Override
    public int shipWidth() {
        return 50;
    }

    /**
     * Name of the level.
     *
     * @return level's name
     */
    @Override
    public String levelName() {
        return "Battle No.  " + String.valueOf(levelNum);
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
        return new Background(new Fill(Color.BLACK));
    }

    /**
     * Get alien's start position's X coordinate.
     *
     * @return alien's start X coordinate.
     */
    public int aliensStartX() {
        return 20;
    }

    /**
     * Get alien's start position's Y coordinate.
     *
     * @return alien's start Y coordinate.
     */
    public int aliensStartY() {
        return 50;
    }

    /**
     * The aliens that make up this level.
     *
     * @return aliens
     * @throws IOException problem reading alien's image
     */
    @Override
    public List<Alien> aliens() throws IOException {
        List<Alien> aliens = new LinkedList<Alien>();
        int rows = 5;
        int cols = 10;
        int alienHeight = 30;
        int alienWidth = Consts.getInstance().getAliensWidth();

        // Add the aliens in each row and column
        int rowsGap = 0;
        for (int i = 0; i < cols; i++) {
            int x = i * alienWidth + aliensStartX() + rowsGap;
            int colsGap = 0;
            for (int j = 0; j < rows; j++) {
                int y = j * alienHeight + aliensStartY() + colsGap;
                Point upperLeft = new Point(x, y);
                aliens.add(new Alien(upperLeft, alienWidth, alienHeight));
                colsGap += 10;
            }
            rowsGap += 10;
        }

        return aliens;
    }

    /**
     * Number of aliens to remove before the level is cleared. This number is <=
     * aliens.size()
     *
     * @return aliens to remove
     * @throws IOException problem reading alien's image
     */
    @Override
    public int numberOfAliensToRemove() throws IOException {
        return aliens().size();
    }
}
