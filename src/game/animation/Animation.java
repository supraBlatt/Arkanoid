package game.animation;
import biuoop.DrawSurface;

/**
 * @author Yuval Ezra.
 * An Animation interface.
 */
public interface Animation {

   /**
    * Runs a single frame of animation.
    * @param d the DrawSurface to draw the animation on.
    * @param dt the time interval between drawings.
    */
   void doOneFrame(DrawSurface d, double dt);

   /**
    * Figures wether the animation should stop or not.
    * @return true if it should stop, false otherwise.
    */
   boolean shouldStop();
}