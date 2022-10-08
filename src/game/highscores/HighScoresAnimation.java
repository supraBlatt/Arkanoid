package game.highscores;

import java.awt.Color;

import biuoop.DrawSurface;
import game.animation.Animation;

/**
 * @author Yuval Ezra
 * A high-score animation
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * A constructor for HighScoresAnimation.
     * @param scores the scores to show.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * Does a single frame of the highscore table screen.
     * @param d the DrawSurface to draw this highscore on.
     * @param dt the time interval between frames.
     */
    public void doOneFrame(DrawSurface d, double dt) {

       // black background
       d.setColor(Color.BLACK);
       d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

       // drawing the pause-text
       d.setColor(Color.WHITE);

       // final score
       int startY = d.getHeight() / 4;
       int startX = d.getWidth() / 4 + 30;
       d.drawText(startX, startY, "HIGH SCORES ", 40);

       for (ScoreInfo score: this.scores.getHighScores()) {
          startY += 25;
          d.drawText(startX, startY, score.getName() + " " + score.getScore(), 20);
       }
    }

    @Override
    public boolean shouldStop() { return false; }
 }