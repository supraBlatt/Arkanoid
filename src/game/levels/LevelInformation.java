package game.levels;

import java.util.List;
import game.objects.Block;
import game.objects.ball.Velocity;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * A level information.
 */
public interface LevelInformation {

   /**
    * @return this number of Balls.
    */
   int numberOfBalls();

   /**
    * @return the Balls' initial Velocities.
    */
   List<Velocity> initialBallVelocities();

   /**
    * @return the Paddle's speed.
    */
   int paddleSpeed();

   /**
    * @return the Paddle's width.
    */
   int paddleWidth();

   /**
    * @return this level's name.
    */
   String levelName();

   /**
    * @return this level's background.
    */
   Sprite getBackground();

   /**
    * @return this level's Blocks.
    */
   List<Block> blocks();

   /**
    * @return the number of blocks in the level.
    */
   int numberOfBlocksToRemove();
}