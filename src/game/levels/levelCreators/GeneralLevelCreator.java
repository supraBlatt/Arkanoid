package game.levels.levelCreators;

import java.util.List;

import game.levels.LevelInformation;
import game.levels.backgrounds.GeneralBackground;
import game.misc.Fill;
import game.misc.Tuple;
import game.objects.Block;
import game.objects.ball.Velocity;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * A general level creator.
 */
public class GeneralLevelCreator implements LevelInformation {
    private String levelName;
    private List<Velocity> ballVelocities;
    private Fill background;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Block> blocks;

    private int width;
    private int height;

    /**
     * A constructor for GeneralLevelCreator.
     * @param levelName the level's name.
     * @param ballVelocities the level's ball velocities.
     * @param background the level's background.
     * @param paddleSpeed the level's paddle's speed.
     * @param paddleWidth the level's paddle's width.
     * @param blocks the level's name.
     * @param levelScale the scale of the level (width, height).
     */
    public GeneralLevelCreator(String levelName, List<Velocity> ballVelocities,
                               Fill background, int paddleSpeed, int paddleWidth,
                               List<Block> blocks, Tuple<Integer, Integer> levelScale) {
        this.levelName = levelName;
        this.ballVelocities = ballVelocities;
        this.background = background;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocks = blocks;
        this.width = levelScale.getLeft();
        this.height = levelScale.getRight();
    }

    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return new GeneralBackground(this.width, this.height, this.background);
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }

}
