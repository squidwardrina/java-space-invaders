package gameplay;

import sprites.collidables.Alien;
import sprites.Sprite;

import java.io.IOException;
import java.util.List;

/**
 * Information about a certain level.
 */
public interface LevelInformation {
    /**
     * Gets aliens speed.
     *
     * @return the aliens speed
     */
    int aliensSpeed();

    /**
     * sprites.Paddle's peed.
     *
     * @return speed of the paddle for the level
     */
    int shipSpeed();

    /**
     * sprites.Paddle's with.
     *
     * @return width of the paddle for the level
     */
    int shipWidth();

    /**
     * Name of the level.
     *
     * @return level's name
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level. Each block contains it's size, color
     * and location.
     *
     * @return aliens
     * @throws IOException problem with file
     */
    List<Alien> aliens() throws IOException;

    /**
     * Number of aliens to remove before the level is cleared. This number is <=
     * aliens.size()
     *
     * @return aliens to remove
     * @throws IOException problem with file
     */
    int numberOfAliensToRemove() throws IOException;

    /**
     * Get alien's start position's X coordinate.
     *
     * @return alien's start X coordinate.
     */
    int aliensStartX();

    /**
     * Get alien's start position's Y coordinate.
     *
     * @return alien's start Y coordinate.
     */
    int aliensStartY();
}
