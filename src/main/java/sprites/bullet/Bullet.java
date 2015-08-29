package sprites.bullet;

import animation.GameLevel;
import biuoop.DrawSurface;
import graphics.Line;
import graphics.Point;
import gameplay.GameEnvironment;
import listeners.HitNotifier;
import sprites.collidables.Collidable;
import sprites.Sprite;

import java.awt.Color;

/**
 * This class represents a ball.
 */
public abstract class Bullet implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Color stroke;
    private Velocity velocity;
    private GameEnvironment environment;

    /**
     * Constructs a bullet.
     *
     * @param center   ball's center point
     * @param r        ball's radius
     * @param color    ball's color
     * @param stroke   stroke color
     * @param velocity velocity of the bullet
     */
    public Bullet(Point center, int r, Color color,
                  Color stroke, Velocity velocity) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.stroke = stroke;
        this.velocity = velocity;
    }

    /**
     * Gets x value of ball's center.
     *
     * @return x
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Returns Y coordinate of ball's center.
     *
     * @return y
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param d the d to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillCircle(getX(), getY(), radius);
        d.setColor(stroke);
        d.drawCircle(getX(), getY(), radius);
    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * Adds the ball to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addBullet(this);
    }

    /**
     * Removes the ball from the gameLevel.
     *
     * @param gameLevel the gameLevel object to remove from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * Move the ball one step.
     *
     * @param secPerMove time for one move
     */
    public void moveOneStep(double secPerMove) {
        // Check for collision and get the closest one
        Line trajectory = getTrajectory(secPerMove);
        CollisionInfo collision = getCollision(trajectory);

        // If no collision - move tha ball to needed place
        if (collision == null) {
            applyVelocity(secPerMove);
        } else {
            handleCollision(collision);
        }
    }

    /**
     * Applies the velocity to move the bullet.
     *
     * @param secPerMove seconds per move
     */
    protected void applyVelocity(double secPerMove) {
        center = velocity.applyToPoint(center, secPerMove);
    }

    /**
     * Sets the center position.
     *
     * @param x x coord
     * @param y y coord
     */
    public void setCenter(double x, double y) {
        center = new Point(x, y);
    }

    /**
     * Handles collision with another object.
     *
     * @param collision the collision info
     */
    protected void handleCollision(CollisionInfo collision) {
        Point collisionPoint = collision.collisionPoint();
        Collidable collisionObject = collision.collisionObject();
        double smallDx = velocity.getX() / 100;
        double smallDy = velocity.getY() / 100;
        double collisionX = collisionPoint.getX();
        double collisionY = collisionPoint.getY();

        // Move the ball slightly before the collision point
        setCenter(collisionX - smallDx, collisionY - smallDy);
        ((HitNotifier) collisionObject).notifyHit(this);
    }

    /**
     * Gets the trajectory of the ball from start point to next step.
     *
     * @param secPerMove seconds per move to apply velocity
     * @return trajectory
     */
    protected Line getTrajectory(double secPerMove) {
        return new Line(center, velocity.applyToPoint(center, secPerMove));
    }

    /**
     * Get the collision info by the trajectory.
     *
     * @param trajectory the line to check collision
     * @return Gets collision with the closest object, null if no collision
     */
    private CollisionInfo getCollision(Line trajectory) {
        return environment.getClosestCollision(trajectory);
    }

    /**
     * Sets the bullet's environment.
     *
     * @param bulletEnvironment collection of hittable objects
     */
    public void setEnvironment(GameEnvironment bulletEnvironment) {
        environment = bulletEnvironment;
    }
}