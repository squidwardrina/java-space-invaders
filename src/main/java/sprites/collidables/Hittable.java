package sprites.collidables;

import biuoop.DrawSurface;
import graphics.Point;
import graphics.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Fill;
import sprites.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * Any hittable object that can be destroyed by a hit.
 */
abstract class Hittable extends Rectangle implements Collidable, HitNotifier {
    private int hitPoints = 1;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Fill fill;

    /**
     * Creates a default alien on specified position and size.
     *
     * @param upperLeft position point
     * @param width     width of the block
     * @param height    height of the block
     * @param fill      the filling of the object
     */
    public Hittable(Point upperLeft, double width,
                    double height, Fill fill) {
        super(upperLeft, width, height);
        this.fill = fill;
    }

    /**
     * Creates a default alien on specified position and size.
     *
     * @param x      x position
     * @param y      y position
     * @param width  width of the block
     * @param height height of the block
     * @param fill   the filling of the object
     */
    public Hittable(int x, int y, double width,
                    double height, Fill fill) {
        super(new Point(x, y), width, height);
        this.fill = fill;
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return collision shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    /**
     * Reduces the score of the block.
     */
    protected void reduceScore() {
        if (hitPoints >= 0) {
            hitPoints--;
        }
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        fill.fillRectangle(d, this);
    }

    /**
     * Adds listener to hit events.
     *
     * @param hl the listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Removes the listener from list.
     *
     * @param hl the listener to be removed
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Handles hit event for this alien.
     *
     * @param hitter the bullet that hit
     */
    public void notifyHit(Bullet hitter) {
        reduceScore();

        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners =
                new ArrayList<HitListener>(hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
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
        return obj instanceof Hittable
                && ((Hittable) obj).getUpperLeft().equals(this.getUpperLeft());
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
