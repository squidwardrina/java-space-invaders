package sprites.bullet;

import graphics.Point;
import sprites.collidables.Collidable;

/**
 * This class represents the information about occurred collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Set the collision info.
     *
     * @param collisionPoint  point of collision
     * @param collisionObject the object of collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * The point at which the collision occurs.
     *
     * @return collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * The collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}