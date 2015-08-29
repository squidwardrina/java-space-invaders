package sprites.statusbar;

import animation.GameLevel;
import biuoop.DrawSurface;
import graphics.Rectangle;
import sprites.Sprite;
import utils.Consts;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents the status bar at top of the screen. Holds a list of indicators to
 * display.
 */
public class StatusBar extends Rectangle implements Sprite {
    private final Color fillColor = new Color(50, 101, 60);
    private final Color borderColor = new Color(112, 221, 136);
    private ArrayList<Indicator> indicators;

    /**
     * Define the status indicators.
     *
     * @param statusIndicators includes the status indicators
     */
    public StatusBar(ArrayList<Indicator> statusIndicators) {
        super(0, 0, Consts.getInstance().getGameWidth(),
                Consts.getInstance().getStatusBarHeight());
        this.indicators = statusIndicators;
    }

    /**
     * Draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Fill the bar
        d.setColor(fillColor);
        d.fillRectangle((int) getUpperLeft().getX(),
                (int) getUpperLeft().getY(), (int) getWidth(),
                (int) getHeight());

        // Draw the border
        d.setColor(borderColor);
        d.drawRectangle((int) getUpperLeft().getX(),
                (int) getUpperLeft().getY(), (int) getWidth(),
                (int) getHeight());

        // Draw all the indicators on the bar
        int currStart = 100;
        for (Indicator indicator : indicators) {
            indicator.setX(currStart);
            indicator.drawOn(d);
            currStart += indicator.getWidth();
        }
    }

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * Adds the status bar and all indicators to the game.
     *
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        for (Indicator indicator : indicators) {
            indicator.addToGame(g);
        }
    }
}
