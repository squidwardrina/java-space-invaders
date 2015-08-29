package sprites.collidables;

import animation.GameLevel;
import graphics.Point;
import sprites.bullet.AlienBullet;
import sprites.bullet.Bullet;
import utils.Consts;
import utils.ImageFiller;

import java.io.IOException;

/**
 * This class represents a block - rectangle that a ball may collide.
 */
public class Alien extends PlayerHittable {
    /**
     * Creates a default alien on specified position and size.
     *
     * @param upperLeft position point
     * @param width     width of the block
     * @param height    height of the block
     * @throws IOException problem with the image file
     */
    public Alien(Point upperLeft, double width, double height)
            throws IOException {
        super(upperLeft, width, height, ImageFiller
                .createImageFill(Consts.getInstance().getAlienImage()));
    }

    /**
     * Makes the alien fire.
     *
     * @param game game to fire in
     */
    public void shoot(GameLevel game) {
        Point pos = getUpperLeft();
        Bullet bullet = new AlienBullet(pos.getX() + (getWidth() / 2),
                pos.getY() + getHeight());
        bullet.addToGame(game);   // Add bullet to game
    }

    /**
     * Moves the alien one step right if step is positive. Otherwise - left.
     *
     * @param step step to move right
     */
    public void moveRight(int step) {
        getUpperLeft().moveX(step); // move freely
    }

    /**
     * Moves the alien one step down. If step negative - move up.
     *
     * @param step step to move down
     */
    public void moveDown(int step) {
        getUpperLeft().moveY(step);
    }

    /**
     * Moves the alien.
     *
     * @param stepRight step to move right
     * @param stepDown  step to move down
     */
    public void move(int stepRight, int stepDown) {
        moveRight(stepRight);
        moveDown(stepDown);
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
        return obj instanceof Alien
                && ((Alien) obj).getUpperLeft().equals(this.getUpperLeft());
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
