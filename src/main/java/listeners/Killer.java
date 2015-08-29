package listeners;

import animation.GameLevel;
import sprites.collidables.Collidable;
import sprites.collidables.Ship;
import sprites.bullet.Bullet;

/**
 * Class in charge of killing the player when he was hit.
 */
public class Killer implements HitListener {
    private GameLevel gameLevel;

    /**
     * Creates the killer.
     *
     * @param gameLevel the current game level
     */
    public Killer(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * Kills the player.
     *
     * @param beingHit the object that was hit
     * @param hitter   the bullet that hit the object
     */
    @Override
    public void hitEvent(Collidable beingHit, Bullet hitter) {
        hitter.removeFromGame(gameLevel);
        Ship.getInstance().kill();
    }
}