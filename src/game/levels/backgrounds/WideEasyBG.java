package game.levels.backgrounds;

import java.awt.Color;
import biuoop.DrawSurface;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * The background for the second level, "Wide Easy".
 */
public class WideEasyBG implements Sprite {

    private int width;
    private int height;

    /**
     * A WideEasyBG constructor.
     * @param width the background's width.
     * @param height the background's height.
     */
    public WideEasyBG(int width, int height) {
      this.width = width;
      this.height = height;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, width, height);

        /* the colours of the sun
         * sunColors[0] -> the outer sun
         * sunColors[1] -> the middle sun
         * sunColors[2] -> the innter sun
         */
        Color[] sunColors = {new Color(239, 231, 176),
                             new Color(236, 215, 73),
                             new Color(255, 255, 24)};

        int centerX = this.width / 6 + 23;
        int centerY = this.height / 4 + 2;
        int radius = this.width / 11 - 13;

        d.setColor(sunColors[0]);

        // creating the lines
        int lines = 100;
        int endY = 253, startX = centerX - radius - 110;
        for (int i = 0; i < lines; i++) {

            // hopping in constant "jumps"
            int endX = startX + (this.width / lines) * i;
            d.drawLine(centerX, centerY, endX, endY);
        }

        // drawing the sun
        for (int i = 0; i < 3; i++) {
            d.setColor(sunColors[i]);
            d.fillCircle(centerX, centerY, radius - 10 * i);
        }
    }

    @Override
    public void timePassed(double dt) {
        //nothing.
    }

}
