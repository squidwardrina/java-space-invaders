package sprites.collidables;

import animation.GameLevel;
import graphics.Point;
import sprites.Fill;

/**
 * Any object that can be hit by a player.
 */
public abstract class PlayerHittable extends Hittable {
    /**
     * Creates a player hittable object on specified position and size.
     *
     * @param upperLeft position point
     * @param width     width of the block
     * @param height    height of the block
     * @param fill      the filling of the object
     */
    public PlayerHittable(Point upperLeft, double width, double height,
                          Fill fill) {
        super(upperLeft, width, height, fill);
    }

    /**
     * Creates a player hittable object on specified position and size.
     *
     * @param x      x position
     * @param y      y position
     * @param width  width of the block
     * @param height height of the block
     * @param fill   the filling of the object
     */
    public PlayerHittable(int x, int y, double width, double height,
                          Fill fill) {
        super(x, y, width, height, fill);
    }

    /**
     * Adds the block to the game.
     *
     * @param game game
     */
    @Override
    public void addToGame(GameLevel game) {
        game.addPlayerCollidable(this);
    }

    /**
     * Removes the block from the gameLevel.
     *
     * @param game the gameLevel object to remove from
     */
    public void removeFromGame(GameLevel game) {
        game.removePlayerCollidable(this);
    }
}
