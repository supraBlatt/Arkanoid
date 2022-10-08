package game.levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.CountdownAnimation;
import game.animation.PauseScreen;
import game.collections.GameEnvironment;
import game.collections.SpriteCollection;
import game.decorators.KeyPressStoppableAnimation;
import game.geometry.Point;
import game.geometry.Rectangle;
import game.hitListeners.BallRemover;
import game.hitListeners.BlockRemover;
import game.hitListeners.ScoreTrackingListener;
import game.misc.Counter;
import game.misc.Fill;
import game.objects.Block;
import game.objects.Paddle;
import game.objects.ball.Ball;
import game.objects.collidable.Collidable;
import game.objects.sprite.LevelIndicator;
import game.objects.sprite.LivesIndicator;
import game.objects.sprite.ScoreIndicator;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra
 * An Arkanoid game.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private int wid;
    private int len;
    private Paddle paddle;
    private KeyboardSensor keyboard;

    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter lives;

    private AnimationRunner runner;

    private LevelInformation level;

    /**
     * A constructor for a GameLevel.
     * @param level the level we would play.
     * @param keyboard the GameLevel keyboard.
     * @param ar the AnimationRunner.
     * @param lives the lives.
     * @param score the score.
     */
    public GameLevel(LevelInformation level, KeyboardSensor keyboard, AnimationRunner ar,
                     Counter lives, Counter score) {
        this.level = level;
        this.score = score;
        this.lives = lives;
        this.keyboard = keyboard;
        this.wid = 800;
        this.len = 600;
        this.runner = ar;
    }

    /**
     * Add a Collidable to the Game.
     * @param c a Collidable to add to the Game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a Sprite to the Game.
     * @param s a Sprite to add to the Game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes Collidable c from this Game.
     * @param c the Collidable to remove from this Game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes Sprite s from this Game.
     * @param s the Sprite to remove from this Game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * @return this GameEnvironment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * @return this remainingBlocks.
     */
    public Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }

    /**
     *  Initialize a new game: create the Blocks and Ball (and Paddle)
     *  and add them to the game.
     */
    public void initialize() {

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        // initialize the Counters
        this.remainingBlocks = new Counter(this.level.numberOfBlocksToRemove());
        this.remainingBalls = new Counter(0);

        // initialize the HitListeners
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(score);


        // special Blocks creation - the borders and the death border
        java.util.List<Block> specialBlocks = new java.util.LinkedList<>();
        int thickness = 26;

        // creating the border blocks, screen and scoreboard
        this.addSprite(new Block(new Rectangle(new Point(0, 0), this.wid, this.len),
                       Color.BLUE.darker().darker(), -1));

        // right border
        specialBlocks.add(new Block(new Rectangle(new Point(this.wid - thickness, 0),
                          thickness, this.len), Color.GRAY, -1));

        // left border
        specialBlocks.add(new Block(new Rectangle(new Point(0, 0), thickness, this.len),
                          java.awt.Color.GRAY, -1));

        // scoreboard
        int scoreboardThickness = 20;
        Block scoreblock = new Block(new Rectangle(new Point(0, 0), this.wid, scoreboardThickness),
                                     Color.WHITE, -1);
        ScoreIndicator scoreboard = new ScoreIndicator(score, scoreblock);

        // live counter
        LivesIndicator liveIndicator = new LivesIndicator(lives, scoreblock);

        // level indicator
        LevelIndicator levelIndicator = new LevelIndicator(this.level.levelName(), scoreblock);

        // upper border
        specialBlocks.add(new Block(new Rectangle(new Point(0, scoreboardThickness), this.wid, thickness),
                         Color.GRAY, -1));

        // death border
        Block deathBorder = new Block(new Rectangle(new Point(0, len), this.wid, thickness),
                                      Color.GRAY, -1);

        deathBorder.addHitListener(ballRemover);
        specialBlocks.add(deathBorder);

        this.addSprite(this.level.getBackground());
        java.util.List<Block> blocks = this.level.blocks();

        // adding the special Blocks to this Game
        for (Block b : specialBlocks) {
            b.addToGame(this);
        }

        // adding the inner Blocks to this Game
        for (Block b : blocks) {
            b.addHitListener(blockRemover);
            b.addHitListener(scoreTracker);
            b.addToGame(this);
        }

        // adding the score and life counters
        this.addSprite(scoreboard);
        this.addSprite(liveIndicator);
        this.addSprite(levelIndicator);
    }

    /**
     * Creates the Paddle. A conveniecnce function.
     */
    public void placePaddle() {

        // Paddle initialization
        int paddleWidth = this.level.paddleWidth();
        int paddleHeight = 19, thickness = 23;
        int paddleSpeed = this.level.paddleSpeed();

        // the middle of the screen, X axis wise
        double paddleX = (this.wid - paddleWidth) / 2;
        double paddleY = this.len - thickness - paddleHeight;
        Rectangle paddleRect = new Rectangle(new Point(paddleX, paddleY), paddleWidth, paddleHeight);

        if (this.paddle == null) {
            List<Fill> fills = new ArrayList<>();
            fills.add(new Fill(Color.ORANGE));
            Block b = new Block(paddleRect, fills, new Fill(Color.black), -1);
            this.paddle = new Paddle(b, this.keyboard, paddleSpeed);
        } else {
            this.paddle.getBlock().setCollisionRectangle(paddleRect);
        }

        // setting this Paddle's environment
        this.paddle.setEnvironment(this.environment);
        this.paddle.addToGame(this);
    }

    /**
     * Creates the Balls. A convenience function.
     */
    public void createBallsOnTopOfPaddle() {

        // creating the Balls
        List<Ball> balls = new ArrayList<>();
        Rectangle c = this.paddle.getCollisionRectangle();
        double x = c.getUpperLeft().getX() + c.getWidth() / 2;
        double y = c.getUpperLeft().getY() - 10;
        Point sharedCenter = new Point(x, y);

        for (int i = 0; i < this.level.numberOfBalls(); i++) {
            balls.add(new Ball(sharedCenter, 5, java.awt.Color.WHITE));
            balls.get(i).setVelocity(this.level.initialBallVelocities().get(i));
        }

        // adds the Balls to this Game
        for (Ball ball : balls) {
            ball.addToGame(this);
            this.remainingBalls.increase(1);
        }
    }

    /**
     * Play the game -- start the animation loop for a single life.
     */
    public void playOneTurn() {

        // creates the balls the places the Paddle
        this.placePaddle();
        this.createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        this.runner.run(this);
        this.paddle.removeFromGame(this);
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                                                           new PauseScreen()));
        }

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
    }

    @Override
    public boolean shouldStop() {

        // if no Blocks or remain, draw the screen after the loss, and stop the game
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            return true;
        }

        if (this.remainingBalls.getValue() == 0) {
            this.lives.decrease(1);
            return true;
        }
        return false;
    }
}
