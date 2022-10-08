package game.animation;
import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * @author Yuval Ezra.
 * An Animation Runner.
 */
public class AnimationRunner {
   private GUI gui;
   private int framesPerSecond;

   /**
    * A constructor for AnimationRunner.
    * @param gui the GUI to run this animation on.
    * @param framesPerSecond the number of FPS in the animation.
    */
   public AnimationRunner(GUI gui, int framesPerSecond) {
       this.gui = gui;
       this.framesPerSecond = framesPerSecond;
   }

   /**
    * Running the Animation 'animation'.
    * @param animation the Animation to run.
    */
   public void run(Animation animation) {
      biuoop.Sleeper sleeper = new biuoop.Sleeper();
      int millisecondsPerFrame = 1000 / this.framesPerSecond;
      while (!animation.shouldStop()) {
         long startTime = System.currentTimeMillis(); // timing
         DrawSurface d = gui.getDrawSurface();

         animation.doOneFrame(d, 1.0 / this.framesPerSecond);

         gui.show(d);
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         if (milliSecondLeftToSleep > 0) {
             sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
}