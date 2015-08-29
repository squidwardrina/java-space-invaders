import animation.AnimationRunner;
import biuoop.KeyboardSensor;
import run.SpaceInvaders;
import utils.Consts;

import java.io.IOException;

/**
 * Arkanoid game main class.
 */
public class Ass7Game {

    /**
     * Runs the game.
     *
     * @param args level numbers in chosen order
     */
    public static void main(String[] args) {
        AnimationRunner runner = new AnimationRunner(
                Consts.getInstance().getFramesPerSec());
        KeyboardSensor keyboardSensor
                = runner.getGui().getKeyboardSensor();

        // Create the game
        SpaceInvaders game = new SpaceInvaders(runner, keyboardSensor);
        try {
            game.runSpaceInvaders();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
