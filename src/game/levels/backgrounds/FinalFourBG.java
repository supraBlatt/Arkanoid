package game.levels.backgrounds;

import java.awt.Color;
import biuoop.DrawSurface;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * A map for the level Final Four.
 */
public class FinalFourBG implements Sprite {

    private int width;
    private int height;

    /**
     * A constructor for a FinalFourBG.
     * @param width the frame's width.
     * @param height the frame's height.
     */
    public FinalFourBG(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // blue Background
        Color sky = new Color(56, 126, 237);
        d.setColor(sky);
        d.fillRectangle(0, 0, width, height);

        // draw 2 clouds
        this.drawClowd(d, 105, (2 * this.height) / 3 + 20);
        this.drawClowd(d, this.width - 218, this.height - 80);

    }

    @Override
    public void timePassed(double dt) {
        //nothing.

    }

    /**
     * Draws a clowd draw in d starting by center at the coordinates (x, y).
     * @param d the DrawSurface to draw the cloud on.
     * @param x the X coordinate of the starting center.
     * @param y the Y coordinate of the starting center.
     */
    public void drawClowd(DrawSurface d, int x, int y) {
        Color[] cloudsColors = {new Color(204, 204, 204),
                                new Color(187, 187, 187),
                                new Color(170, 170, 170)};

        // the circle's fixed radius
        int r = 27;

        // drawing the rain drops
        d.setColor(Color.WHITE);
        int startingX = x + 3;
        for (int i = 1; i <= 10; i++) {
            d.drawLine(startingX + 10 * i, y, (startingX - 30) + 10 * i, this.height);
        }

        // drawing the clouds themselves
        d.setColor(cloudsColors[0]);
        d.fillCircle(x, y, r);

        // the light gray part
        d.fillCircle(x + r, y + r, r + 1);
        d.setColor(cloudsColors[1]);

        // the mid-gray tone part
        d.fillCircle(x + (3 * r) / 2, y - 5, r + 3);
        d.setColor(cloudsColors[2]);

        // the dark gray part
        d.fillCircle(x + 3 * r, y, r + 10);
        d.fillCircle(x + (5 * r) / 2, y + r, r - 5);
    }

}