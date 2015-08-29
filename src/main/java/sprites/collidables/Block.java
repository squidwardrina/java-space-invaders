package sprites.collidables;

import animation.GameLevel;
import sprites.Fill;

import java.awt.Color;

/**
 * This class represents a block - rectangle that a ball may collide.
 */
public class Block extends Hittable {
    private static final int SIZE = 5;

    /**
     * Creates a default alien on specified position and size.
     *
     * @param x     x position
     * @param y     y position
     * @param color color of the block
     */
    public Block(int x, int y, Color color) {
        super(x, y, SIZE, SIZE, new Fill(color));
    }

    /**
     * Gets the size of block.
     *
     * @return block's size
     */
    public static int getSize() {
        return SIZE;
    }

    /**
     * Adds the block to the game.
     *
     * @param game game
     */
    @Override
    public void addToGame(GameLevel game) {
        game.addPlayerCollidable(this);
        game.addEnemyCollidable(this);
    }

    /**
     * Removes the block from the gameLevel.
     *
     * @param game the gameLevel object to remove from
     */
    public void removeFromGame(GameLevel game) {
        game.removePlayerCollidable(this);
        game.removeEnemyCollidable(this);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Block
                && ((Block) obj).getUpperLeft().equals(this.getUpperLeft());
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
