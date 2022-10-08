package game.menu;

import game.animation.Animation;
import game.animation.AnimationRunner;

/**
 * @author Yuval Ezra.
 * A high-score-shower task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * A constructor for ShowHiScoresTask.
     * @param runner an animationRunner for running this highscore shower.
     * @param highScoresAnimation an Animation for running.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
       this.runner = runner;
       this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * Running the animation.
     * @return null.
     */
    public Void run() {
       this.runner.run(this.highScoresAnimation);
       return null;
    }
 }
