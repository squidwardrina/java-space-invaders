package run;

import animation.AnimationRunner;
import animation.MenuAnimation;
import animation.KeyPressStoppableAnimation;
import animation.HighScoresAnimation;
import biuoop.KeyboardSensor;
import gameplay.GameFlow;
import menu.Task;
import scores.HighScoresTable;
import utils.Consts;

import java.io.File;
import java.io.IOException;

/**
 * Class runs the space invaders game.
 */
public class SpaceInvaders {
    private Consts consts = Consts.getInstance();
    private AnimationRunner runner;
    private KeyboardSensor sensor;

    /**
     * Creates the arkanoid game session.
     *
     * @param runner the animation runner for the game session
     * @param sensor the keyboard sensor for user input
     */
    public SpaceInvaders(AnimationRunner runner, KeyboardSensor sensor) {
        this.runner = runner;
        this.sensor = sensor;
    }

    /**
     * Runs the game menu.
     *
     * @throws IOException problem with file
     */
    public void runSpaceInvaders() throws IOException {
        showMainMenu();
    }

    /**
     * Displays the user menu and prepares the chosen tasks.
     *
     * @throws IOException problem with file
     */
    private void showMainMenu() throws IOException {
        // Run the menu until user exits
        while (true) {
            // Creating new menu each time to refresh the levels
            MenuAnimation<Task<Void>> menu = createMenu();
            runner.run(menu); // run the menu
            Task<Void> task = menu.getStatus(); // wait for user selection
            task.run();
        }
    }

    /**
     * Creates the game menu with relevant options.
     *
     * @return the new created menu
     * @throws IOException problem with file
     */
    private MenuAnimation<Task<Void>> createMenu() throws IOException {
        MenuAnimation<Task<Void>> menu
                = new MenuAnimation<Task<Void>>(sensor, runner);

        // Add menu options
        menu.addSelection("s", "Press \"s\" to start a new game.",
                playOption());
        menu.addSelection("h", "Press \"h\" to see the high scores.",
                scoresOption());
        menu.addSelection("q", "Press \"q\" to quit.", quitOption());

        return menu; // return the prepared menu
    }

    /**
     * Returns the game play task that starts the game.
     *
     * @return game task
     * @throws IOException problem with file
     */
    private Task<Void> playOption() throws IOException {
        return new Task<Void>() {
            private GameFlow gameFlow = new GameFlow(runner, sensor,
                    Consts.getInstance().getLives());

            @Override
            public Void run() throws IOException {
                gameFlow.runLevels();
                return null;
            }
        };
    }

    /**
     * Creates the quit task.
     *
     * @return quit task
     */
    private Task<Void> quitOption() {
        return new Task<Void>() {
            // Quits the game
            public Void run() {
                System.exit(0);
                return null;
            }
        };
    }

    /**
     * Creates the showing high scores task.
     *
     * @return showing high scores task
     */
    private Task<Void> scoresOption() {
        // Get the high score table file
        final File scoresFile = new File(consts.getScoresFileName());

        // Create the screen task
        return new Task<Void>() {
            // Runs the animation
            public Void run() throws IOException {
                // Create the screen animation
                HighScoresTable scoresTable =
                        HighScoresTable.loadFromFile(scoresFile);
                KeyPressStoppableAnimation scoresScreen
                        = new KeyPressStoppableAnimation(sensor,
                        new HighScoresAnimation(scoresTable),
                        consts.getStopAnimationKey());
                runner.run(scoresScreen);
                return null;
            }
        };
    }
}
