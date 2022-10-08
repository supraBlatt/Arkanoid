package game.hitListeners;

import game.levels.GameLevel;
import game.misc.Counter;
import game.objects.Block;
import game.objects.ball.Ball;

/**
 * @author Yuval Ezra.
 * A BallkRemover is in charge of removing Balls from the game,
 * as well as keeping count of the number of Balls that remain.
 */
public class BallRemover implements HitListener {

   // the Game it removes Balls from
   private GameLevel game;

   // the number of Balls remaining in game.
   private Counter remainingBalls;

   /**
    * A BlockRemover constructor.
    * @param game the Game this BlockRemover removes Blocks from.
    * @param removedBalls the Number of remaining Balls in the game.
    */
   public BallRemover(GameLevel game, Counter removedBalls) {
       this.game = game;
       this.remainingBalls = removedBalls;
   }

   /**
    * Balls that hit and 'beingHit' should be removed
    * from the game.
    * @param beingHit a death-block that destroys a Ball on touch.
    * @param hitter the Ball that hit 'beingHit'.
    */
   public void hitEvent(Block beingHit, Ball hitter) {
      hitter.removeFromGame(this.game);
      this.remainingBalls.decrease(1);
   }
}