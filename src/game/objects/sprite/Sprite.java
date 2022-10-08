package game.objects.sprite;
import biuoop.DrawSurface;
/**
 * @author Yuval Ezra
 * An object that will be drawn on screen.
 */
public interface Sprite {

   /**
    * Draw the sprite to the screen.
    * @param d the drawSurface to draw the Sprite on.
    */
   void drawOn(DrawSurface d);

   /**
    * Notify the sprite that time has passed.
    * @param dt the time interval between drawings.
    */
   void timePassed(double dt);
}