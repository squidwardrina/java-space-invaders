package gameplay;

import sprites.bullet.CollisionInfo;
import graphics.Line;
import graphics.Point;
import graphics.Rectangle;
import sprites.collidables.Collidable;

import java.util.LinkedList;
import java.util.List;

/**
 * The game environment - contains all the objects on the game screen.
 */
public class GameEnvironment {
    private LinkedList<Collidable> collidables;

    /**
     * Creates the game environment and adds aliens on screen sides.
     */
    public GameEnvironment() {
        collidables = new LinkedList<Collidable>();
    }

    /**
     * Add a collidable to the environment.
     *
     * @param c collidable object
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Removes the collidable from the list.
     *
     * @param c collidable to remove
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Get the collision info by the track line.
     *
     * @param trajectory the line to check collision
     * @return Gets collision with the closest object, null if no collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double minDistance = -1;
        double currDistance;
        Point currIntersection;
        Point closestIntersection = null;
        Collidable closestObject = null;

        // Make a copy to prevent exceptions if the list changes
        List<Collidable> collidablesCopy
                = new LinkedList<Collidable>(collidables);

        // Go over all the collidables
        for (Collidable collidable : collidablesCopy) {
            Rectangle rectangle = collidable.getCollisionRectangle();
            // Get the intersection point with current rectangle
            currIntersection = trajectory
                    .closestIntersectionToStartOfLine(rectangle);
            if (currIntersection == null) {
                continue;
            }

            // If it's the closest point yet - save it
            currDistance = trajectory.start().distance(currIntersection);

            if (currDistance == 0) { // if it's the same point - don't count it
                continue;
            }
            if ((minDistance == -1) || (currDistance < minDistance)) {
                minDistance = currDistance;
                closestIntersection = currIntersection;
                closestObject = collidable;
            }
        }

        if (closestIntersection != null) {
            // Return coordinates
            closestIntersection.setX(closestIntersection.getX());
            closestIntersection.setY(closestIntersection.getY());
            return new CollisionInfo(closestIntersection, closestObject);
        }
        return null; // no collision
    }
}