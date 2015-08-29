package gameplay;

import animation.AnimationRunner;
import animation.GameLevel;
import animation.Animation;
import animation.GameOverScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import scores.HighScoresTable;
import scores.ScoreInfo;
import utils.Consts;
import utils.Counter;

import java.io.File;
import java.io.IOException;

/**
 * Class in charge of creating the game levels and running them one after
 * another.
 */
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private HighScoresTable scoresTable;
    private Counter playerScore = new Counter(0);
    private Counter livesLeft;
    private Consts consts = Consts.getInstance();

    /**
     * Creates the game flow.
     *
     * @param runner    the animation runner
     * @param keySensor the keyboard sensor
     * @param lives     amount of lives user will have
     * @throws IOException problem with file
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor keySensor,
                    int lives) throws IOException {
        this.animationRunner = runner;
        this.keyboardSensor = keySensor;
        this.livesLeft = new Counter(lives);

        // Load the high scores table
        File scoresFile = new File(consts.getScoresFileName());
        scoresTable = HighScoresTable.loadFromFile(scoresFile);
    }

    /**
     * Run the game.
     *
     * @throws IOException problem reading file
     */
    public void runLevels() throws IOException {
        boolean playerAlive = true;
        int levelNum = 1;

        // Run levels as they are in list
        while (playerAlive) {
            // Create the current level
            GameLevel level =
                    new GameLevel(new Battle(levelNum), keyboardSensor,
                            animationRunner, playerScore, livesLeft);
            level.initialize();

            // Play current level while there are more aliens and lives
            while (level.areAliensLeft() && areLivesLeft()) {
                level.playOneTurn();
            }

            // No more lives - stop the game
            if (livesLeft.getValue() == 0) {
                playerAlive = false;
            }

            levelNum++;
            // currLevel = new Battle(levelNum);
        }

        // Game ended
        finishGame();
    }

    /**
     * Save high scores to file.
     */
    private void saveScores() {
        File scoresFile = new File(consts.getScoresFileName());
        try {
            scoresTable.save(scoresFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the game end process.
     */
    private void finishGame() {
        // Display the end screen
        displayEndScreen();

        // Add the player's score if needed, and display the scores table
        if (scoresTable.isToAdd(playerScore.getValue())) {
            addHighScore();
        }
        displayHighScores();
    }

    /**
     * Displays the end game screen - "you lose".
     */
    private void displayEndScreen() {
        Animation endScreen;
        int currScore = playerScore.getValue();
        endScreen = new GameOverScreen(currScore);

        // Wrap the screen with a stoppable animation
        KeyPressStoppableAnimation stoppableEndScreen =
                new KeyPressStoppableAnimation(keyboardSensor,
                        endScreen, consts.getStopAnimationKey());
        animationRunner.run(stoppableEndScreen);
    }

    /**
     * Displays the high scores screen.
     */
    private void displayHighScores() {
        // Create the screen
        KeyPressStoppableAnimation scoresScreen
                = new KeyPressStoppableAnimation(keyboardSensor,
                new HighScoresAnimation(scoresTable),
                consts.getStopAnimationKey());

        // Run the screen animation
        animationRunner.run(scoresScreen);
    }

    /**
     * Adds new high score and saves updated table to file.
     */
    private void addHighScore() {
        // Create new score info
        int currScore = playerScore.getValue();
        String playerName = getPlayerName();
        ScoreInfo scoreInfo = new ScoreInfo(playerName, currScore);

        // Add and save
        scoresTable.add(scoreInfo);
        saveScores();
    }

    /**
     * Gets a name from the user.
     *
     * @return player's name
     */
    private String getPlayerName() {
        DialogManager dialog = animationRunner.getGui().getDialogManager();
        return dialog.showQuestionDialog(
                "New high score!", "What is your name?", "");
    }

    /**
     * Returns whether player has more lives.
     *
     * @return true or false
     */
    private boolean areLivesLeft() {
        return livesLeft.getValue() > 0;
    }
}