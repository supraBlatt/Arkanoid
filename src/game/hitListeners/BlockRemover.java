package game.hitListeners;

import game.levels.GameLevel;
import game.misc.Counter;
import game.objects.Block;
import game.objects.ball.Ball;

/**
 * @author Yuval Ezra.
 * A BlockRemover is in charge of removing Blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {

   // the Game it removes Blocks from
   private GameLevel game;

   // the number of Blocks remaining in game.
   private Counter remainingBlocks;

   /**
    * A BlockRemover constructor.
    * @param game the Game this BlockRemover removes Blocks from.
    * @param removedBlocks the Number of remaining Blocks in the game.
    */
   public BlockRemover(GameLevel game, Counter removedBlocks) {
       this.game = game;
       this.remainingBlocks = removedBlocks;
   }

   /**
    * Blocks that are hit and reach 0 hit-points should be removed
    * from the game.
    * @param beingHit the Block that was hit.
    * @param hitter the Ball that hit 'beingHit'.
    */
   public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getHitPoints() == 0) {
           beingHit.removeHitListener(this);
           beingHit.removeFromGame(this.game);
           this.remainingBlocks.decrease(1);
       }
   }
}