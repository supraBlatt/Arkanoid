package game.objects.ball;
import java.awt.Color;

import biuoop.DrawSurface;
import game.collections.GameEnvironment;
import game.geometry.Line;
import game.geometry.Point;
import game.levels.GameLevel;
import game.objects.Paddle;
import game.objects.collidable.Collidable;
import game.objects.collidable.CollisionInfo;
import game.objects.sprite.Sprite;
/**
 * @author Yuval Ezra
 * A Ball.
 */
public class Ball implements Sprite {

    private Point center;
    private int size;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment environment;

    /**
     * A constructor for a Ball.
     * @param center the Ball's center
     * @param radius the Ball's radius
     * @param color the Ball's colour
     */
    public Ball(Point center, int radius, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.size = radius;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.environment = new GameEnvironment();
    }

    /**
     * Another constructor for a Ball.
     * @param x the center's x
     * @param y the center's y
     * @param radius the Ball's radius
     * @param color the Ball's colour
     */
    public Ball(int x, int y, int radius, java.awt.Color color) {
        this.center = new Point(x, y);
        this.size = radius;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.environment = new GameEnvironment();
    }

    /**
     * A constructor for a Ball - copying another Ball's properties.
     * @param other the Ball to create a copy of.
     */
    public Ball(Ball other) {
        this.center = new Point(other.getX(), other.getY());
        this.size = other.getSize();
        this.color = other.getColor();
        this.v = other.getVelocity();
        this.environment = other.getEnvironment();
    }

    /**
     * @return  the Ball's center's x.
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * @return the Ball's center's y.
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * @return the Ball's radius.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return the Ball's colour.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return the Ball's Velocity.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * @return  the Ball's GameEnvironment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Sets the Ball's colour to c.
     * @param c the colour to have the ball set to.
     */
    public void setColor(java.awt.Color c) {
        this.color = c;
    }

    /**
     * Sets the Ball's Velocity to Velocity.
     * @param velocity the Velocity to set to the Ball.
     */
    public void setVelocity(Velocity velocity) {
        this.v = new Velocity(velocity.getDX(), velocity.getDY());
    }

    /**
     * Sets the Ball's Velocity to v = (dx, dy).
     * @param dx the dx of the new Velocity.
     * @param dy the dy of the new Velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Sets the Ball's center to be 'newCenter'.
     * @param newCenter the Ball's new center-point.
     */
    public void setCenter(Point newCenter) {
        this.center = new Point(newCenter.getX(), newCenter.getY());
    }

    /**
     * Sets the Ball's environment to 'env'.
     * @param env the new GameEnvironment for the Ball.
     */
    public void setEnvironment(GameEnvironment env) {
        this.environment = env;
    }

    /**
     * Draws the Ball on the given DrawSurface.
     * @param surface the DrawSurface to draw the Ball on.
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) this.center.getX();
        int y = (int) this.center.getY();
        surface.setColor(this.color);
        surface.fillCircle(x, y, this.size);
        surface.setColor(Color.BLACK);
        surface.drawCircle(x, y, this.size);
    }

    /**
     * Add this Ball to the game.
     * @param g the game to add this Ball to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        this.setEnvironment(g.getEnvironment());
    }

    /**
     * Removes this Ball from the Game 'game'.
     * @param game the Game to remove this Ball from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

    /**
     * Moves the Ball one step.
     * @param dt the time interval between frames.
     */
    public void moveOneStep(double dt) {
        // This Ball's trajectory
        Velocity newV = new Velocity(this.v.getDX() * dt, this.v.getDY() * dt);
        Line trajectory = new Line(this.center, newV.applyToPoint(this.center));

        // The Collidables the Ball will hit
        java.util.List<CollisionInfo> obstacles = this.environment.getClosestCollisions(trajectory);
        if (obstacles == null) {
            this.center = newV.applyToPoint(this.center);
            return;
        }
        for (CollisionInfo c : obstacles) {
            Point collision = c.collisionPoint();
            if (collision != null) {
                if (c.collisionObject() instanceof Paddle) {
                    // Handle the case of a Ball inside the paddle
                    this.handleInsidePaddle(c.collisionObject());
                }

                // Sets the Ball's new center and speed
                this.moveToCollision(c.collisionObject());
                this.setVelocity(c.collisionObject().hit(this, collision, this.v));
            }
        }
    }

    /**
     * Moves the Ball to it's collision point with 'c'.
     * @param c the Collidable that collides with this Ball.
     */
    public void moveToCollision(Collidable c) {
        Line trajectory = new Line(this.center, this.v.applyToPoint(this.center));
        Point corner = c.getCollisionRectangle().getUpperLeft();
        Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
        if (collision == null) {
            return;
        }

        // Variables regarding the points for comfort
        double colX = collision.getX();
        double colY = collision.getY();
        double cornerX = corner.getX();
        double cornerY = corner.getY();
        double wid = c.getCollisionRectangle().getWidth();
        double height = c.getCollisionRectangle().getHeight();

        // An epsilon
        double epsilon = 0.01;

        double leftBorder = cornerX;
        double rightBorder = (cornerX + wid);
        double topBorder = cornerY;
        double bottomBorder = (cornerY + height);

        // Checking if the Ball is about to hit any border of the Collidable
        if (leftBorder - epsilon < colX && colX < leftBorder && this.v.getDX() >= 0) {

            // Moving the Ball's perimeter to the edge of the Collidable
            this.center = new Point(leftBorder - this.size, this.getY());
        }

        if (rightBorder < colX && colX < rightBorder + epsilon && this.v.getDX() <= 0) {
            this.center = new Point(rightBorder + this.size, this.getY());
        }

        if (topBorder - epsilon < colY && colY < topBorder && this.v.getDY() >= 0) {
            this.center = new Point(this.getX(), topBorder - this.size);
        }

        if (bottomBorder < colY && colY < bottomBorder + epsilon && this.v.getDY() <= 0) {
            this.center = new Point(this.getX(), bottomBorder + this.size);
        }
    }
    /**
     * Puts the Ball outside of the Paddle when it's in the Paddle.
     * @param p the Paddle to move to Ball out of.
     */
    public void handleInsidePaddle(Collidable p) {
        Point corner = p.getCollisionRectangle().getUpperLeft();
        double cornerX = corner.getX();
        double cornerY = corner.getY();
        double wid = p.getCollisionRectangle().getWidth();
        double height = p.getCollisionRectangle().getHeight();

        Point newCenter = new Point(this.getX(), this.getY());
        // If the Ball is in the Paddle Y axis wise
        if (cornerY < this.getY() && this.getY() <= cornerY + height) {

            // And x axis wise, and is on the first half of the Paddle
            if (cornerX < this.getX() && this.getX() < (cornerX + wid / 2)) {
                newCenter = new Point(cornerX - this.size, this.getY());
            } else if (cornerX + wid / 2 <= this.getX() && this.getX() < cornerX + wid) {

                // On the second half of the Paddle
                newCenter = new Point(cornerX + wid + this.size, this.getY());
            }
        }

        // Checks if the ball was moved into a Collidable
        Line trajectory = new Line(newCenter, this.center);
        CollisionInfo c = this.environment.getClosestCollision(trajectory);

        // If it collided with anything, move it above the paddle to make it "pop"
        if (c != null && c.collisionObject() != p) {
            this.center = new Point(this.getX(), cornerY - this.size);
        } else {

            // Else, let it move as the Paddle wishes.
            this.center = newCenter;
        }
    }

    /**
     * Informs the Ball that time passed.
     * @param dt the time interval between calls.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }
}