package sprites.collidables;

import animation.GameLevel;
import graphics.Rectangle;

/**
 * An object that can be collided with.
 */
public interface Collidable {
    /**
     * Adds the sprite to the game.
     *
     * @param g game
     */
    void addToGame(GameLevel g);

    /**
     * Returns the collision rectangle.
     *
     * @return the collision rectangle.
     */
    Rectangle getCollisionRectangle();
}