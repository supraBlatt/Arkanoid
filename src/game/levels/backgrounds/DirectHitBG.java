package game.levels.backgrounds;

import java.awt.Color;

import biuoop.DrawSurface;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * The background for the level Direct Hit.
 */
public class DirectHitBG implements Sprite {
    private int width;
    private int height;

    /**
     * A constructor for DirectHitBG.
     * @param width the screen's width.
     * @param height the screen's height.
     */
    public DirectHitBG(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // black Background:
        d.setColor(Color.black);
        d.fillRectangle(0, 0, width, height);
        d.setColor(Color.BLUE.darker());

        int offset = 127;

        // vertical lines
        d.drawLine(this.width / 2, this.height / 2 + 20, this.width / 2, this.height / 2 - offset + 22);
        d.drawLine(this.width / 2 , 0, this.width / 2, this.height / 2 - offset - 20);

        // horizontal lines
        d.drawLine(this.width / 2 - 22, this.height / 2 - offset,
                   this.width / 2 - offset - 20, this.height / 2 - offset);
        d.drawLine(this.width / 2 + 22, this.height / 2 - offset,
                   this.width / 2 + offset + 20, this.height / 2 - offset);

        // drawing the "aim"
        d.drawCircle(this.width / 2, this.height / 2 - offset, 60);
        d.drawCircle(this.width / 2, this.height / 2 - offset, 95);
        d.drawCircle(this.width / 2, this.height / 2 - offset, 129);
    }

    @Override
    public void timePassed(double dt) {
    }
}
