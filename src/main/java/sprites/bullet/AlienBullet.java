package sprites.bullet;

import animation.GameLevel;
import graphics.Point;

import java.awt.Color;

/**
 * A bullet shot from an alien.
 */
public class AlienBullet extends Bullet {
    /**
     * Constructs an alien's bullet.
     *
     * @param x x coord
     * @param y y coord
     */
    public AlienBullet(double x, double y) {
        super(new Point(x, y), 4, new Color(251, 15, 0), new Color(161, 9, 0),
                Velocity.fromAngleAndSpeed(180, 400));
    }

    /**
     * Adds the bullet to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        setEnvironment(g.getEnemyEnvironment());
        super.addToGame(g);
    }
}