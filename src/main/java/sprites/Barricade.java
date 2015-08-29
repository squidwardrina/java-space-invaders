package sprites;

import animation.GameLevel;
import biuoop.DrawSurface;
import graphics.Rectangle;
import listeners.BlockRemover;
import sprites.collidables.Block;
import utils.Consts;

import java.awt.Color;
import java.util.LinkedList;

/**
 * Represents a barricade of blocks below the ship.
 */
public class Barricade extends Rectangle implements Sprite {
    private LinkedList<Block> blocks = new LinkedList<Block>();
    private static final int HEIGHT = 15;
    public static final int WIDTH = 150;

    /**
     * Create a new barricade with location and width/height.
     *
     * @param xPos  upper left vertex
     * @param color barricade's color
     */
    public Barricade(int xPos, Color color) {
        super(xPos, Consts.getInstance().getBarricadesPosition(),
                WIDTH, HEIGHT);
        createBlocks(color);
    }

    /**
     * Creates the blocks for the barricade.
     *
     * @param color color of the barricade blocks
     */
    private void createBlocks(Color color) {
        int top = getTop();
        int bottom = getBottom();
        int right = getRight();
        int left = getLeft();

        // Fill all the barricade width blocks
        for (int row = top; row < bottom; row += Block.getSize()) {
            for (int col = left; col < right; col += Block.getSize()) {
                blocks.add(new Block(col, row, color));
            }
        }
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        for (Block block : blocks) {
            block.drawOn(d);
        }
    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Adds the sprite to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);

        // Go over the blocks, add them to game and register a remover listener
        BlockRemover remover = new BlockRemover(g, this);
        for (Block block : blocks) {
            block.addHitListener(remover);
            block.addToGame(g);
        }
    }

    /**
     * Remove the block from barricade.
     *
     * @param hitBlock block to be removed
     */
    public void removeBlock(Block hitBlock) {
        blocks.remove(hitBlock);
    }
}
