package game.levels.backgrounds;

import java.awt.Color;
import java.awt.Image;

import biuoop.DrawSurface;
import game.misc.Fill;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * A background for a general level.
 */
public class GeneralBackground implements Sprite {
    private int width;
    private int height;
    private Fill fill;

    /**
     * A constructor for GeneralBackground.
     * @param width the screen's width.
     * @param height the screen's height.
     * @param fill this background's Fill.
     */
    public GeneralBackground(int width, int height, Fill fill) {
        this.width = width;
        this.height = height;
        this.fill = fill;
        if (fill.getImage() != null) {
            Image im = fill.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            this.fill = new Fill(im);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
     // Draw the Block itself
        Fill f = this.fill;

        // if it should be coloured, color it.
        if (f.getColor() != null) {
            d.setColor(f.getColor());
            d.fillRectangle(0, 0, this.width, this.height);
        } else  {

            // if there should be an image instead, put it on the block as a "decorator".
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, this.width, this.height);
            d.drawImage(0, 0, f.getImage());
        }
    }

    @Override
    public void timePassed(double dt) {
    }
}