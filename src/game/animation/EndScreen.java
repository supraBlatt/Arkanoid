package game.animation;

import java.awt.Color;
import biuoop.DrawSurface;
import game.misc.Counter;

/**
 * @author Yuval Ezra.
 * A Pausee Screen.
 */
public class EndScreen implements Animation {
   private boolean stop;
   private boolean win;
   private Counter score;

   /**
    * A constructor for a PauseScreen.
    * @param score the final-score.
    * @param win true if the game was won, false otherwise.
    */
   public EndScreen(Counter score, boolean win) {
      this.stop = false;
      this.win = win;
      this.score = score;
   }

   /**
    * Does a single frame of an End Screen.
    * @param d the DrawSurface to draw this EndScreen on.
    * @param dt the time interval between frames.
    */
   public void doOneFrame(DrawSurface d, double dt) {

      // black background
      d.setColor(Color.BLACK);
      d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

      // drawing the pause-text
      d.setColor(Color.WHITE);
      if (win) {
          d.drawText(d.getWidth() / 3 + 3, d.getHeight() / 3 + 50, "YOU WON!", 40);
      } else {
          d.drawText(d.getWidth() / 3 , d.getHeight() / 3 + 50, "GAME OVER", 40);
      }

      // final score
      d.drawText(d.getWidth() / 4 + 15, d.getHeight() / 2,
              "YOUR SCORE IS " + this.score.getValue() , 40);

      d.drawText(d.getWidth() / 6 + 40, d.getHeight() - 100,
                 "PRESS SPACE TO CONTINUE", 32);
   }

   /**
    * Checking if the Animation should stop.
    * @return true if the Animation should stop, false otherwise.
    */
   public boolean shouldStop() { return this.stop; }
}