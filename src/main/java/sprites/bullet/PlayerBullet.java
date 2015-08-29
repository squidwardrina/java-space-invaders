package sprites.bullet;

import animation.GameLevel;
import graphics.Point;

import java.awt.Color;

/**
 * A bullet shot from the player.
 */
public class PlayerBullet extends Bullet {
    /**
     * Constructs a player's bullet.
     *
     * @param x x coord
     * @param y y coord
     */
    public PlayerBullet(double x, double y) {
        super(new Point(x, y), 2, new Color(65, 255, 0), new Color(47, 185, 0),
                Velocity.fromAngleAndSpeed(0, 500));
    }

    /**
     * Adds the bullet to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        setEnvironment(g.getPlayerEnvironment());
        super.addToGame(g);
    }
}
