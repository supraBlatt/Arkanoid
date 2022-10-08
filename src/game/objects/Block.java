package game.objects;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.geometry.Rectangle;
import game.hitListeners.HitListener;
import game.hitListeners.HitNotifier;
import game.levels.GameLevel;
import game.misc.Fill;
import game.objects.ball.Ball;
import game.objects.ball.Velocity;
import game.objects.collidable.Collidable;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra
 * A block.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle collisionRectangle;
    private List<Fill> fills;
    private Fill stroke;
    private int hitPoints;
    private List<HitListener> hitListeners;

    /**
     * A constructor for a Block.
     * @param collisionRectangle the Block's shape (Rectangle)
     * @param fills the Block's colours
     * @param stroke the Block's stroke
     * @param hitPoints the Block's HP
     */
    public Block(Rectangle collisionRectangle, List<Fill> fills, Fill stroke, int hitPoints) {
        this.collisionRectangle = collisionRectangle;
        this.fills = new ArrayList<>();
        for (Fill f : fills) {
            if (f.getImage() != null) {
                Image im = f.getImage().getScaledInstance((int) collisionRectangle.getWidth(),
                                                          (int) collisionRectangle.getHeight(), Image.SCALE_DEFAULT);
                this.fills.add(new Fill(im));
            } else {
                this.fills.add(f);
            }
        }
        this.stroke = stroke;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * A constructor for a Block.
     * @param collisionRectangle the Block's shape (Rectangle)
     * @param c the Block's colour
     * @param hitPoints the Block's HP
     */
    public Block(Rectangle collisionRectangle, Color c, int hitPoints) {
        this.collisionRectangle = collisionRectangle;
        this.fills = new ArrayList<>();

        for (int i = 1; i <= Math.max(1, hitPoints); i++) {
            fills.add(new Fill(c));
        }

        this.stroke = null;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * A constructor for a Block.
     * @param collisionRectangle the Block's shape (Rectangle)
     * @param c the Block's colour
     * @param stroke the Block's stroke
     * @param hitPoints the Block's HP
     */
    public Block(Rectangle collisionRectangle, Color c, Color stroke, int hitPoints) {
        this.collisionRectangle = collisionRectangle;
        this.fills = new ArrayList<>();
        for (int i = 1; i <= Math.max(1, hitPoints); i++) {
            fills.add(new Fill(c));
        }
        this.stroke = new Fill(stroke);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.collisionRectangle;
    }

    /**
     * @return the Block's colour.
     */
    public Fill getColor() {
        return this.fills.get(Math.max(0, this.hitPoints - 1));
    }

    /**
     * @return this Block's hitPoints.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Sets the Block's colour to 'c'.
     * @param f the Block's new Fill
     */
    public void setColor(Fill f) {
        int index = Math.max(0, this.hitPoints - 1);
        this.fills.remove(index);
        this.fills.add(index, f);
    }

    /**
     * Sets this Block's collisionRectangle to rect.
     * @param rect this Block's new collisionRectangle
     */
    public void setCollisionRectangle(Rectangle rect) {
        this.collisionRectangle = rect;
    }

    /**
     * @return the Blocks's collision rectangle width.
     */
    public double getWidth() {
        return this.collisionRectangle.getWidth();
    }

    /**
     * @return the Blocks's collision rectangle height.
     */
    public double getHeight() {
        return this.collisionRectangle.getHeight();
    }

    /**
     * Changes the Ball's speed after it hit the Block.
     * @param hitter the hitting Ball.
     * @param collisionPoint the Ball's collision point with this Block.
     * @param currentVelocity the Ball's current velocity.
     * @return a new Velocity for the Ball, after hitting this Block at the
     * collision point 'collisionPoint'.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double cornerX = this.collisionRectangle.getUpperLeft().getX();
        double cornerY = this.collisionRectangle.getUpperLeft().getY();
        double width = this.getWidth();
        double height = this.getHeight();

        double colX = collisionPoint.getX();
        double colY = collisionPoint.getY();

        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();
        double epsilon = 0.01;

        Velocity newVelocity = new Velocity(dx, dy);

        // If the ball hit the Block from the right or left sides
        if ((cornerX >= colX && colX >= cornerX - epsilon && dx > 0)
                || (cornerX + width + epsilon >= colX && colX >= cornerX + width && dx < 0)) {
            newVelocity.setDX(-currentVelocity.getDX());
        }

        dx = currentVelocity.getDX();
        dy = currentVelocity.getDY();

        // If the Ball hit the Block from up or down
        if ((cornerY >= colY && colY >= cornerY - epsilon && dy > 0)
                || (cornerY + height + epsilon >= colY && colY >= cornerY + height && dy < 0)) {
            newVelocity.setDY(-currentVelocity.getDY());
        }
        if (this.hitPoints != 0) {
            hitPoints--;
        }

        // notifies about the hit and returns the new speed.
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * Draws the Ball on the given DrawSurface.
     * @param surface the DrawSurface to draw the Ball on.
     */
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.collisionRectangle.getUpperLeft();
        double x = upperLeft.getX();
        double y = upperLeft.getY();

        // Draw the Block itself
        Fill f;
        f = this.fills.get(Math.max(0, this.hitPoints - 1));

        // if it should be coloured, color it.
        if (f.getColor() != null) {
            surface.setColor(f.getColor());
            surface.fillRectangle((int) x, (int) y, (int) this.getWidth(), (int) this.getHeight());
        } else if (f.getImage() != null) {

            // if there should be an image instead, put it on the block as a "decorator".
            surface.setColor(Color.BLUE);
            surface.fillRectangle((int) x, (int) y, (int) this.getWidth(), (int) this.getHeight());
            surface.drawImage((int) x, (int) y, f.getImage());
        }

        if (this.stroke != null) {
            surface.setColor(this.stroke.getColor());
            surface.drawRectangle((int) x, (int) y, (int) this.getWidth(), (int) this.getHeight());
        }
    }

    /**
     * Notifies the Block that time has passed.
     * @param dt the time interval between movements.
     */
    public void timePassed(double dt) {
    }

    /**
     * Add this Block to the game.
     * @param g the game to add this Block to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Removes this Block from the Game 'game'.
     * @param game the Game to remove this Block from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Adds HitListener 'hl' to this hitListener list.
     * @param hl the HitListener to be added to this hitListener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes HitListener 'hl' to this hitListener list.
     * @param hl the HitListener to be removed from this hitListener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies that the Ball 'hitter' hit this Block.
     * @param hitter the Ball that hit this Block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}