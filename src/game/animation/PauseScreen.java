package game.animation;

import biuoop.DrawSurface;

/**
 * @author Yuval Ezra.
 * A Pause Screen.
 */
public class PauseScreen implements Animation {
   private boolean stop;

   /**
    * A constructor for a PauseScreen.
    */
   public PauseScreen() {
      this.stop = false;
   }

   /**
    * Does a single frame of a pause screen.
    * @param d the DrawSurface to draw the PauseScreen on.
    * @param dt the time interval between frames.
    */
   public void doOneFrame(DrawSurface d, double dt) {

      // drawing the pause-text
      d.drawText(d.getWidth() / 3 + 30, d.getHeight() / 3 + 50, "PAUSED", 40);
      d.drawText(d.getWidth() / 6 + 40, d.getHeight() - 100,
              "PRESS SPACE TO CONTINUE", 32);
   }

   /**
    * Checking if the Animation should stop.
    * @return true if the Animation should stop, false otherwise.
    */
   public boolean shouldStop() { return this.stop; }
}