package game.objects;
import java.awt.Color;

import biuoop.DrawSurface;
import game.collections.GameEnvironment;
import game.geometry.Line;
import game.geometry.Point;
import game.geometry.Rectangle;
import game.levels.GameLevel;
import game.objects.ball.Ball;
import game.objects.ball.Velocity;
import game.objects.collidable.Collidable;
import game.objects.collidable.CollisionInfo;
import game.objects.sprite.Sprite;
/**
 * @author Yuval Ezra
 * A paddle.
 */
public class Paddle implements Sprite, Collidable {
   private biuoop.KeyboardSensor keyboard;
   private Block block;
   private double speed;
   private GameEnvironment env;

   /**
    * A Paddle constructor.
    * @param block the Paddle's Block.
    * @param keyboard the keyboard sensor to sense the Paddle's movement via
    * the keyboard.
    * @param speed the Paddle's speed.
    */
   public Paddle(Block block, biuoop.KeyboardSensor keyboard, double speed) {
       this.block = block;
       this.keyboard = keyboard;
       this.speed = speed;
   }

   /**
    * Moves the Paddle to the left.
    * @param dt the time interval between frames.
    */
   public void moveLeft(double dt) {
       double newSpeed = this.speed * dt;
       Point newCorner;
       Line trajectory = new Line(new Point(this.getX(), this.getY()),
                                  new Point(this.getX() - newSpeed, this.getY()));
       CollisionInfo c = this.env.getClosestCollision(trajectory);

       // If the Paddle hit something on the left, don't allow it to continue in that direction.
       if (c != null && this.getX() - newSpeed < c.collisionPoint().getX()) {
           newCorner = new Point(c.collisionPoint().getX(), this.getY());
       } else {
           newCorner = new Point(this.getX() - newSpeed, this.getY());
       }
       Rectangle collisionRectangle = new Rectangle(newCorner, this.getWidth(), this.getHeight());
       this.block = new Block(collisionRectangle, this.block.getColor().getColor(), Color.BLACK,
                              this.block.getHitPoints());
   }

   /**
    * Moves the Paddle to the right.
    * @param dt the time interval between frames.
    */
   public void moveRight(double dt) {
       double newSpeed = this.speed * dt;
       Point newCorner;
       Line trajectory = new Line(new Point(this.getX() + this.getWidth(), this.getY()),
                                  new Point(this.getX() + this.getWidth() + newSpeed, this.getY()));
       CollisionInfo c = this.env.getClosestCollision(trajectory);

       // If the Paddle hit something on its right, don't allow it to continue in that direction.
       // Also if the Paddle is going to cross it on the next move, and is on its left before moving.
       if (c != null && (this.getX() + this.getWidth()) + newSpeed > c.collisionPoint().getX()
           && this.getX() < c.collisionPoint().getX()) {
           newCorner = new Point(c.collisionPoint().getX() - this.getWidth(), this.getY());
       } else {
           newCorner = new Point(this.getX() + newSpeed,
                                 this.getY());
       }
       Rectangle collisionRectangle = new Rectangle(newCorner, this.getWidth(), this.getHeight());
       this.block = new Block(collisionRectangle, this.block.getColor().getColor(), Color.BLACK,
                              this.block.getHitPoints());
   }

   /**
    * Notifies the Paddle that time has passed, and moves it.
    * @param dt the time interval between drawings.
    */
   public void timePassed(double dt) {
       if (this.keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY)) {
           this.moveLeft(dt);
       }
       if (this.keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY)) {
           this.moveRight(dt);
       }
   }

   /**
    * Draws the Paddle on the given DrawSurface 'd'.
    * @param d  the given DrawSurface.
    */
   public void drawOn(DrawSurface d) {
       this.block.drawOn(d);
   }

   /**
    * @return the Paddle's collision Rectangle.
    */
   public Rectangle getCollisionRectangle() {
       return this.block.getCollisionRectangle();
   }

   /**
   * Changes the Ball's speed after it hit the Paddle.
   * @param hitter the hitting Ball.
   * @param collisionPoint the Ball's collision point with this Paddle.
   * @param currentVelocity the Paddle's current velocity.
   * @return a new Velocity for the Ball, after hitting this Paddle
   * at the collision point 'collisionPoint'.
   */
   public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
       double wid = this.getWidth();

       double x = collisionPoint.getX();
       Velocity newVelocity = new Velocity(currentVelocity.getDX(), currentVelocity.getDY());

       // The Paddle's hit-spots are divided into 5 equal parts.
       for (int i = 1; i <= 5; i++) {
           if (x <= this.getX() + i * (wid / 5)) {

               // If the Ball hit the middle part, 'hit' normally.
               if (i == 3) {
                   newVelocity = this.block.hit(hitter, collisionPoint, currentVelocity);
                   break;
               }

               /*
                * The Ball's new angle of movement (having 360 degrees as the up direction):
                * area 1 -> 300, area 2 -> 330
                * area 3 -> 360, area 4 -> 390
                * area 5 -> 420
                */
               double newAngle = 270 + 30 * i;
               newVelocity = Velocity.fromAngleAndSpeed(newAngle, newVelocity.getSpeed());
               break;
           }
       }
       return newVelocity;
   }

   /**
    * @return the Paddle's collision rectangle width.
    */
   public double getWidth() {
       return this.block.getCollisionRectangle().getWidth();
   }

   /**
    * @return the Paddle's collision rectangle height.
    */
   public double getHeight() {
       return this.block.getCollisionRectangle().getHeight();
   }

   /**
    * @return the Paddle's collision rectangle's corner X coordinate.
    */
   public double getX() {
       return this.block.getCollisionRectangle().getUpperLeft().getX();
   }

   /**
    * @return the Paddle's collision rectangle's corner Y coordiate.
    */
   public double getY() {
       return this.block.getCollisionRectangle().getUpperLeft().getY();
   }

   /**
    * @return this Paddle's Block.
    */
   public Block getBlock() {
       return this.block;
   }

   /**
    * Sets the Paddles GameEnvironment to 'environment'.
    * @param environment the new GameEnvironemnt for the Paddle.
    */
   public void setEnvironment(GameEnvironment environment) {
       this.env = new GameEnvironment(environment.getObstacles());
   }

   /**
    * Add this Paddle to the game.
    * @param g the game to add this Paddle to
    */
   public void addToGame(GameLevel g) {
       g.addSprite(this);
       g.addCollidable(this);
   }

   /**
    * Removes this Paddle from the Game 'game'.
    * @param game the Game to remove this Paddle from.
    */
   public void removeFromGame(GameLevel game) {
       game.removeCollidable(this);
       game.removeSprite(this);
   }
}