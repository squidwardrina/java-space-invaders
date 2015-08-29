package sprites;

import animation.GameLevel;
import biuoop.DrawSurface;
import graphics.Rectangle;
import listeners.AlienRemover;
import listeners.ScoreTrackingListener;
import sprites.collidables.Alien;
import sprites.collidables.Ship;
import utils.Consts;

import java.util.List;
import java.util.LinkedList;

/**
 * Aliens formation - represents the block of aliens moving together.
 */
public class Enemy implements Sprite {
    private int mostLeft;
    private int mostRight;
    private int bottom;
    private int top;
    private int direction = 1; // 1 for right, -1 for left
    private int initSpeed;
    private int currSpeed;
    private List<Alien> aliens;
    private List<Alien> shooters = new LinkedList<Alien>();
    private long lastFireTime = 1;
    private GameLevel game;

    /**
     * Creates an alien formation.
     *
     * @param speed     moving speed
     * @param game      game level to add to
     * @param aliens    collection of aliens
     */
    public Enemy(List<Alien> aliens, int speed, GameLevel game) {
        initSpeed = speed;
        currSpeed = initSpeed;
        this.game = game;
        this.aliens = aliens;
        resetEnemyProperties();
    }

    /**
     * Goes over the aliens and sets the enemy properties.
     */
    private void resetEnemyProperties() {
        // Initialize all the properties
        mostLeft = Consts.getInstance().getGameWidth();
        mostRight = 0;
        bottom = 0;
        top = Consts.getInstance().getGameHeight();
        shooters.clear();

        // Go over the aliens left in list and set the properties
        for (Alien alien : aliens) {
            enlargeEnemyLimitsIfNeeded(alien);
            addNewShooterIfNeeded(alien);
        }
    }

    /**
     * Updates the shooters table if needed.
     *
     * @param newAlien the new alien to be added
     */
    private void addNewShooterIfNeeded(Alien newAlien) {
        int newPos = newAlien.getLeft();
        boolean foundPos = false;

        // Go over the shooters and check if new alien is a new shooter
        for (Alien shooter : shooters) {
            int shooterPos = shooter.getLeft();
            int newBottom = newAlien.getBottom();
            int shooterBottom = shooter.getBottom();

            // If the new alien is below the shooter - change shooter
            if ((newPos == shooterPos) && (newBottom > shooterBottom)) {
                shooters.remove(shooter);
                shooters.add(newAlien);
                foundPos = true;
                break;
            }
        }

        // New position - add the shooter
        if (!foundPos) {
            shooters.add(newAlien);
        }
    }

    /**
     * Removes the alien from the formation.
     *
     * @param deadAlien the dead alien to be removed
     */
    public void removeAlien(Alien deadAlien) {
        aliens.remove(deadAlien);
        resetEnemyProperties();
    }

    /**
     * Gets the new alien rectangle and check if it changes the formation.
     *
     * @param newAlien the rectangle of the new alien to get coordinates
     */
    private void enlargeEnemyLimitsIfNeeded(Rectangle newAlien) {
        int newRight = newAlien.getRight();
        int newLeft = newAlien.getLeft();
        int newBottom = newAlien.getBottom();
        int newTop = newAlien.getTop();

        // Check if new coordinates influence the size of formation
        if (newRight > mostRight) {
            mostRight = newRight;
        }
        if (newLeft < mostLeft) {
            mostLeft = newLeft;
        }
        if (newBottom > bottom) {
            bottom = newBottom;
        }
        if (newTop < top) {
            top = newTop;
        }
    }

    /**
     * Moves all the aliens one step.
     *
     * @param moveTimeSec seconds for the move
     */
    public void moveOneStep(double moveTimeSec) {
        Consts f = Consts.getInstance();
        int step = (int) (direction * currSpeed * moveTimeSec);
        int screenWidth = f.getGameWidth() - f.getMargin();

        // If wall collision expected - move right before the wall
        if ((mostRight + step) > screenWidth) {
            step = screenWidth - mostRight;
        } else if ((mostLeft + step) < f.getMargin()) {
            step = f.getMargin() - mostLeft; // avoid going over left border
        }

        if (step == 0) {
            moveDown(); // nowhere to move forward. Move down
            direction *= -1;    // change direction
        } else {
            moveRight(step); // move on (left or right)
        }
    }

    /**
     * Moves all the aliens left/right.
     *
     * @param stepRight pixels to move
     */
    private void moveRight(int stepRight) {
        // Move all the aliens
        for (Alien alien : aliens) {
            alien.moveRight(stepRight);
        }

        // Move the formation
        mostLeft += stepRight;
        mostRight += stepRight;
    }

    /**
     * Aliens reached the wall - move down, change direction, increase speed.
     */
    private void moveDown() {
        int alienZone = Consts.getInstance().getBarricadesPosition();
        int downStep = Consts.getInstance().getAlienDownStep();

        // Check if the alien zone is not passed
        if (bottom + downStep <= alienZone) {
            top += downStep;
            bottom += downStep;
            currSpeed += currSpeed / 10;    // increase speed by 10%
            for (Alien alien : aliens) {    // move all the aliens down
                alien.moveDown(downStep);
            }
        } else { // passed the alien zone. Kill the ship
            Ship.getInstance().kill();
        }
    }

    /**
     * Random column of aliens shoots a bullet.
     */
    private void fire() {
        // Randomly choose position to shoot
        int chosenPos = (int) (Math.random() * shooters.size());
        shooters.get(chosenPos).shoot(game);
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        for (Alien alien : aliens) {
            alien.drawOn(d);
        }
    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {
        moveOneStep(dt);

        // Fire if possible
        int fireFrequency = Consts.getInstance().getEnemyFireFrequency();
        long currTime = System.currentTimeMillis();
        if (currTime >= lastFireTime + fireFrequency) {
            fire();
            lastFireTime = currTime;
        }
    }

    /**
     * Adds the sprite to the game.
     *
     * @param gameLevel game
     */
    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);

        // Go over the aliens, add them to game and add the listeners to them
        AlienRemover alienRemover = gameLevel.getAlienRemover();
        ScoreTrackingListener scoreListener = gameLevel.getScoreListener();
        for (Alien alien : aliens) {
            // Add hit listeners to the block
            alien.addHitListener(alienRemover);
            alien.addHitListener(scoreListener);

            // Add the alien to game
            alien.addToGame(gameLevel);
        }
    }

    /**
     * Moves all the aliens to the needed position.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    private void moveToPos(int x, int y) {
        int stepRight = x - mostLeft;
        int stepDown = y - top;
        for (Alien alien : aliens) {
            alien.move(stepRight, stepDown);
        }

        // Move the coordinates
        top += stepDown;
        bottom += stepDown;
        mostLeft += stepRight;
        mostRight += stepRight;
    }

    /**
     * Resets the enemy's position and speed.
     *
     * @param startX x start coord
     * @param startY y start coord
     */
    public void reset(int startX, int startY) {
        moveToPos(startX, startY);
        currSpeed = initSpeed;
    }
}
