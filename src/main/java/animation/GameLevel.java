package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameplay.GameEnvironment;
import gameplay.LevelInformation;
import gameplay.SpriteCollection;
import listeners.AlienRemover;
import listeners.Killer;
import listeners.ScoreTrackingListener;
import sprites.Barricade;
import sprites.Enemy;
import sprites.Sprite;
import sprites.collidables.Collidable;
import sprites.collidables.Ship;
import sprites.collidables.Alien;
import sprites.bullet.Bullet;
import sprites.statusbar.Indicator;
import sprites.statusbar.ScoreIndicator;
import sprites.statusbar.LevelIndicator;
import sprites.statusbar.LivesIndicator;
import sprites.statusbar.StatusBar;
import utils.Consts;
import utils.Counter;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class holds all the sprites and is in charge of animation.
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment playerEnvironment = new GameEnvironment();
    private GameEnvironment enemyEnvironment = new GameEnvironment();
    private KeyboardSensor keyboard;
    private Counter aliensToRemove;
    private Counter playerScore;
    private Counter livesLeft;
    private LevelInformation levelInfo;
    private long lastShootTime = -1;
    private Enemy enemy = null;
    private Killer killer;
    private List<Bullet> bulletsOnScreen = new LinkedList<Bullet>();

    /**
     * Creates the game level.
     *
     * @param levelInfo current level information
     * @param keyboard  keyboard sensor of the game
     * @param runner    the game's animation runner
     * @param score     user's current score
     * @param livesLeft player's lives left
     * @throws IOException problem reading file
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard,
                     AnimationRunner runner, Counter score, Counter livesLeft)
            throws IOException {
        // Game properties
        this.runner = runner;
        this.keyboard = keyboard;
        this.playerScore = score;
        this.livesLeft = livesLeft;

        // Current level properties
        this.levelInfo = levelInfo;
        aliensToRemove = new Counter(levelInfo.numberOfAliensToRemove());
        killer = new Killer(this);

    }

    /**
     * Adds a collidable to collection.
     *
     * @param c new collidable
     */
    public void addPlayerCollidable(Collidable c) {
        playerEnvironment.addCollidable(c);
    }

    /**
     * Adds a collidable to collection.
     *
     * @param c new collidable
     */
    public void addEnemyCollidable(Collidable c) {
        enemyEnvironment.addCollidable(c);
    }

    /**
     * Initializes game level. Creates the background, status bar, the ship and
     * the enemy.
     *
     * @throws IOException problem reading file
     */
    public void initialize() throws IOException {
        levelInfo.getBackground().addToGame(this); // add the background
        createStatusBar();
        enemy = new Enemy(levelInfo.aliens(), levelInfo.aliensSpeed(), this);
        enemy.addToGame(this);
        createShip();
        createBarricades();
    }

    /**
     * Creates the barricades below the player.
     */
    private void createBarricades() {
        int barrNum = 3;
        int gameWidth = Consts.getInstance().getGameWidth();
        int margin = (gameWidth - Barricade.WIDTH * barrNum) / (barrNum + 1);

        // Create colors for the barricades
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(new Color(205, 197, 255));
        colors.add(new Color(52, 255, 206));
        colors.add(new Color(255, 197, 139));

        int barricadeStart = margin;
        for (int i = 0; i < barrNum; i++) {
            new Barricade(barricadeStart, colors.get(i)).addToGame(this);
            barricadeStart += Barricade.WIDTH + margin;
        }
    }

    /**
     * Creates and returns a score tracking listener.
     *
     * @return score tracking listener.
     */
    public ScoreTrackingListener getScoreListener() {
        return new ScoreTrackingListener(playerScore);
    }

    /**
     * Creates and returns an alien remover listener.
     *
     * @return alien remover listener.
     */
    public AlienRemover getAlienRemover() {
        return new AlienRemover(this, aliensToRemove, enemy);
    }

    /**
     * Creates a status bar and all it's indicators.
     */
    private void createStatusBar() {
        // Create a list of indicators (in needed order!)
        ArrayList<Indicator> statusIndicators = new ArrayList<Indicator>();
        statusIndicators.add(new LivesIndicator(livesLeft));
        statusIndicators.add(new ScoreIndicator(playerScore));
        statusIndicators.add(new LevelIndicator(levelInfo.levelName()));

        // Create a status bar
        new StatusBar(statusIndicators).addToGame(this);
    }

    /**
     * Adds a sprite to collection.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Loads a level.
     */
    public void playOneTurn() {
        Ship.getInstance().reborn();
        enemy.reset(levelInfo.aliensStartX(), levelInfo.aliensStartY());
        clearBulletsFromScreen();

        // Count down
        runner.run(new CountdownAnimation(2, 3, sprites));

        // Run the game
        running = true;
        runner.run(this); // run the turn
    }

    /**
     * Creates a ship and adds it to the game.
     */
    private void createShip() {
        Ship ship = Ship.getInstance(keyboard, levelInfo.shipSpeed(),
                levelInfo.shipWidth());
        ship.addToGame(this);
        ship.addHitListener(killer);
    }

    /**
     * Plays one game frame.
     *
     * @param d  the game surface
     * @param dt seconds passed since last move
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        sprites.drawAllOn(d);
        sprites.notifyAllTimePassed(dt);

        // Check if we need to stop the game
        if (!areAliensLeft()) {
            running = false;
        } else if (!Ship.getInstance().isAlive()) {
            livesLeft.decrease(1);
            running = false;
        }

        // Run pause screen animation if needed
        if (keyboard.isPressed("p")) {
            String stopKey = Consts.getInstance().getStopAnimationKey();
            runner.run(new KeyPressStoppableAnimation(keyboard,
                    new PauseScreen(), stopKey));

            // Check if the user shot
        } else if (canShoot() && keyboard.isPressed("space")) {
            Ship.getInstance().shoot(this);
            lastShootTime = System.currentTimeMillis();
        }
    }

    /**
     * Checks if enough time has passed and the user can fire.
     *
     * @return can fire - true. False otherwise
     */
    private boolean canShoot() {
        return (lastShootTime == -1)
                || (System.currentTimeMillis() >= lastShootTime + 350);
    }

    /**
     * Returnes whether the game should stop.
     *
     * @return check result
     */
    @Override
    public boolean shouldStop() {
        return !running;
    }

    /**
     * Removes the collidable from the collidables collection.
     *
     * @param c the collidable to remove
     */
    public void removePlayerCollidable(Collidable c) {
        playerEnvironment.removeCollidable(c);
    }

    /**
     * Removes the collidable from the collidables collection.
     *
     * @param c the collidable to remove
     */
    public void removeEnemyCollidable(Collidable c) {
        enemyEnvironment.removeCollidable(c);
    }

    /**
     * Removes the sprite from the sprites collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Removes alien from formation.
     *
     * @param alien alien to remove
     */
    public void removeFromFormation(Alien alien) {
        enemy.removeAlien(alien);
    }

    /**
     * Returns whether there are more aliens.
     *
     * @return true or false
     */
    public boolean areAliensLeft() {
        return aliensToRemove.getValue() > 0;
    }

    /**
     * Gets the game playerEnvironment.
     *
     * @return the game playerEnvironment
     */
    public GameEnvironment getPlayerEnvironment() {
        return playerEnvironment;
    }

    /**
     * Gets the game enemy environment.
     *
     * @return the game enemy environment
     */
    public GameEnvironment getEnemyEnvironment() {
        return enemyEnvironment;
    }

    /**
     * Adds a bullet to the collection.
     *
     * @param newBullet new created bullet
     */
    public void addBullet(Bullet newBullet) {
        bulletsOnScreen.add(newBullet);
    }

    /**
     * Clears all the bullets from the screen.
     */
    private void clearBulletsFromScreen() {
        for (Bullet bullet : bulletsOnScreen) {
            bullet.removeFromGame(this);
        }
    }
}
