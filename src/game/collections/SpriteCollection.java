package game.collections;
import java.util.LinkedList;

import biuoop.DrawSurface;
import game.objects.sprite.Sprite;
/**
 * @author Yuval Ezra
 * A sprite collection.
 */
public class SpriteCollection {
    private java.util.List<Sprite> sprites;

    /**
     * A SpriteCollection constructor.
     */
    public SpriteCollection() {
        this.sprites = new java.util.LinkedList<Sprite>();
    }

    /**
     * Adds a given Sprite to the collection.
     * @param s a Sprite to add to the collection.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes Sprite s from this SpriteCollection.
     * @param s the Sprite to remove from this SpriteCollection.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all Sprites.
     * @param dt the time interval between frames.
     */
    public void notifyAllTimePassed(double dt) {
        LinkedList<Sprite> copy = new LinkedList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all Sprites.
     * @param d a surface to draw on the sprites.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
