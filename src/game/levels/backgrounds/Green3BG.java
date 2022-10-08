package game.levels.backgrounds;

import java.awt.Color;

import biuoop.DrawSurface;
import game.objects.sprite.Sprite;

/**
 * @author Yuval Ezra.
 * The background for the level Direct Hit.
 */
public class Green3BG implements Sprite {

    private int width;
    private int height;

    /**
     * A constructor.
     * @param width the width of the frame.
     * @param height the height of the frame.
     */
    public Green3BG(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawOn(DrawSurface d) {

        // green Background:
        d.setColor(Color.GREEN.darker().darker());
        d.fillRectangle(0, 0, width, height);

        // creating the building block
        int buildingX = this.width / 12 - 5;
        int buildingY = (3 * this.height) / 4;
        int buildingWidth = (3 * this.width) / 24;
        int buildingHeight = this.height / 4;
        d.setColor(new Color(46, 42, 41));
        d.fillRectangle(buildingX, buildingY, buildingWidth, buildingHeight);

        // creating the antenna
        d.setColor(new Color(62, 58, 57));
        d.fillRectangle(this.width / 12 + this.width / 20 - 7,
                        (3 * this.height) / 4 - 50, this.width / 30 - 1, 50);

        // creating the upper antenna
        d.setColor(new Color(78, 74, 73));
        d.fillRectangle((this.width / 7) - 7, (this.height / 3) , this.width / 85, this.height / 3);

        // the colours for the lighted Circle
        Color[] lightColors = {new Color(216, 172, 102), new Color(236, 108, 70), Color.WHITE};

        // creating the lighted circle
        for (int i = 0; i < 3; i++) {
            int rad = this.width / 65 - i * 5;
            d.setColor(lightColors[i]);
            d.fillCircle((this.width / 7) - 3, (this.height / 3), rad);
        }

        d.setColor(Color.WHITE);

        // creating the windows
        int startX = buildingX + 10;
        int startY = buildingY + 10;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                d.fillRectangle(startX + 18 * j, startY + 33 * i, 10, 26);
            }
        }

    }

    @Override
    public void timePassed(double dt) {
        //nothing

    }

}
