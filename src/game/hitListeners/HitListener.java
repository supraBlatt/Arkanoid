package game.hitListeners;

import game.objects.Block;
import game.objects.ball.Ball;

/**
 * @author Yuval Ezra.
 * a hit Listener.
 */
public interface HitListener {

   /**
    * This method is called whenever the beingHit object is hit.
    * @param beingHit the Block that's being hit.
    * @param hitter the Ball that's doing the hitting.
    */
   void hitEvent(Block beingHit, Ball hitter);
}
