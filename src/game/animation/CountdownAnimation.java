package game.animation;
import java.awt.Color;

import biuoop.DrawSurface;
import game.collections.SpriteCollection;

/**
 * @author Yuval Ezra.
 * A Countdown Animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int currentCount;
    private SpriteCollection gameScreen;

    // the time in which the countdown should stop
    private long finishTime;

    // checking whether it's the start of the countdown
    private boolean start;

    /**
     * A constructor for CountdownAnimation.
     * @param numOfSeconds the total number of animation seconds.
     * @param countFrom the first number to count from it to 0.
     * @param gameScreen the screen to draw this countdown on.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {

        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.currentCount = countFrom;
        this.finishTime = System.currentTimeMillis() + (long) numOfSeconds * 1000;
        this.start = true;
    }

    /**
     * Does a single frame of animation.
     * @param d the DrawSurface to draw this Animation on.
     * @param dt the time interval between frames.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        // start time
        long startTime = System.currentTimeMillis();

        // the text that would be drawn
        String text = Integer.toString(this.currentCount);

        // drawing the screen and the countdown
        this.gameScreen.drawAllOn(d);
        if (this.currentCount != 0) {
            d.setColor(Color.ORANGE);
            d.drawText(d.getWidth() / 2 - 15, d.getHeight() / 2 - 15, text, 100);
        }
        long overallTime = System.currentTimeMillis() - startTime;

        // if it's not the first number to be drawn, wait until this number's
        // time is over, and then move on to the next number in the countdown
        if (!this.start) {
            while (overallTime < 1000 * this.numOfSeconds / this.countFrom) {
                overallTime = System.currentTimeMillis() - startTime;
            }
        } else {
            this.start = false;
        }

        // moving on to the next number on the countdown
        this.currentCount--;
    }

    /**
     * Checks if this Animation should stop.
     * @return true if it should stop, else otherwise.
     */
    public boolean shouldStop() {
        return System.currentTimeMillis() >= finishTime;
    }
}