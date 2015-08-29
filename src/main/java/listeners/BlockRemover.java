package listeners;

import animation.GameLevel;
import sprites.Barricade;
import sprites.bullet.Bullet;
import sprites.collidables.Block;
import sprites.collidables.Collidable;

/**
 * Class in charge of removing hit blocks from the game.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Barricade barricade;

    /**
     * Constructs the block remover.
     *
     * @param gameLevel reference to a gameLevel object
     * @param barricade the barricade the block belongs to
     */
    public BlockRemover(GameLevel gameLevel, Barricade barricade) {
        this.gameLevel = gameLevel;
        this.barricade = barricade;
    }


    /**
     * If the hit block reached 0 hit points - remove it.
     *
     * @param beingHit the object that was hit
     * @param hitter   the bullet that hit the object
     */
    @Override
    public void hitEvent(Collidable beingHit, Bullet hitter) {
        // Remove the bullet
        hitter.removeFromGame(gameLevel);

        // Remove the alien
        Block blockHit = (Block) beingHit;
        blockHit.removeHitListener(this);
        blockHit.removeFromGame(gameLevel);
        barricade.removeBlock(blockHit);
    }
}