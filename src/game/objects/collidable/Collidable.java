package game.objects.collidable;

import game.geometry.Point;
import game.geometry.Rectangle;
import game.objects.ball.Ball;
import game.objects.ball.Velocity;

/**
 * @author Yuval Ezra
 * An interface for Collidable objects.
 */
public interface Collidable {

    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Changes the objects's speed after it hit the Paddle.
     * @param hitter the hitting Ball.
     * @param collisionPoint the Ball's collision point with this object.
     * @param currentVelocity the Ball's current velocity.
     * @return a new Velocity for the Ball, after hitting this object
     * at the collision point 'collisionPoint'.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
