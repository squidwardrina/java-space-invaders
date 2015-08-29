package sprites.collidables;

import animation.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import graphics.Point;
import sprites.Fill;
import sprites.Sprite;
import sprites.bullet.Bullet;
import sprites.bullet.PlayerBullet;
import utils.Consts;

import java.awt.Color;

/**
 * sprites.Paddle - the player in the game.
 */
public final class Ship extends Hittable implements Sprite {
    // Constants
    private static final Color FILL_COLOR = new Color(122, 168, 249);
    private static final Color BORDER_COLOR = new Color(39, 26, 249);

    // Properties
    private static Ship instance;
    private biuoop.KeyboardSensor keyboard;
    private double step;
    private boolean isAlive = true;

    /**
     * Create a new paddle with the keyboard sensor.
     *
     * @param keyboard keyboard sensor
     * @param step     paddle's speed
     * @param width    paddle's width
     */
    private Ship(KeyboardSensor keyboard, double step, double width) {
        super((int) (Consts.getInstance().getGameWidth() / 2 - width / 2),
                (int) (Consts.getInstance().getShipYStart()), width,
                Consts.getInstance().getShipHeight(),
                new Fill(Ship.FILL_COLOR));
        this.keyboard = keyboard;
        this.step = step;
    }

    /**
     * Returns a ship with new width and speed. If doesn't exist - create it.
     *
     * @param keyboard keyboard sensor
     * @param speed    ship's speed
     * @param width    ship's width
     * @return the ship
     */
    public static Ship getInstance(KeyboardSensor keyboard, double speed,
                                   double width) {
        if (instance == null) { // create if doesn't exist
            instance = new Ship(keyboard, speed, width);
        } else {
            instance.setStep(speed);
            instance.setWidth(width);
            instance.moveToDefault();
        }
        return instance;
    }

    /**
     * Returns the ship instance. If doesn't exist return null.
     *
     * @return the ship
     */
    public static Ship getInstance() {
        return instance;
    }

    /**
     * Sets paddle speed.
     *
     * @param newStep new speed
     */
    private void setStep(double newStep) {
        step = newStep;
    }

    /**
     * Moves the paddle to default place.
     */
    public void moveToDefault() {
        Consts f = Consts.getInstance();
        getUpperLeft().setX(f.getGameWidth() / 2 - getWidth() / 2);
    }

    /**
     * Draws the paddle on surface d.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw a border
        Point pos = getUpperLeft();
        d.setColor(Ship.BORDER_COLOR);
        d.drawRectangle((int) pos.getX(), (int) pos.getY(),
                (int) getWidth(), (int) getHeight());

        // Draw the ship
        super.drawOn(d);
    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * Adds the paddle to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addEnemyCollidable(this);
    }

    /**
     * Moves right.
     *
     * @param moveTimeSec seconds for move
     */
    public void moveRight(double moveTimeSec) {
        Consts consts = Consts.getInstance();
        int speed = (int) (step * moveTimeSec);
        Point pos = getUpperLeft();
        int gameWidth = consts.getGameWidth() - consts.getMargin();

        // Make sure not to pass the screen borders
        if ((pos.getX() + getWidth() + speed) > gameWidth) {
            pos.setX(gameWidth - getWidth());
        } else {
            pos.moveX(speed);
        }
    }

    /**
     * Moves left.
     *
     * @param moveTimeSec seconds for move
     */
    public void moveLeft(double moveTimeSec) {
        int speed = (int) (step * moveTimeSec);
        Consts consts = Consts.getInstance();
        Point pos = getUpperLeft();

        // Make sure not to pass the screen borders
        if ((pos.getX() - speed) < consts.getMargin()) {
            pos.setX(consts.getMargin());
        } else {
            pos.moveX(-speed);
        }
    }

    /**
     * Shoots a bullet.
     *
     * @param game the game level to fire to
     */
    public void shoot(GameLevel game) {
        Point pos = getUpperLeft();
        Bullet bullet
                = new PlayerBullet(pos.getX() + (getWidth() / 2), pos.getY());
        bullet.addToGame(game);   // Add bullet to game
    }

    /**
     * Kills the ship.
     */
    public void kill() {
        isAlive = false;
    }

    /**
     * Checks if ship is alive.
     *
     * @return true if alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the ship as alive.
     */
    public void reborn() {
        isAlive = true;
        moveToDefault();
    }
}