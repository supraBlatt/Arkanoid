package game.levels.blockCreators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.geometry.Point;
import game.geometry.Rectangle;
import game.misc.Fill;
import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A general block creator.
 */
public class GeneralBlockCreator implements BlockCreator {

    private int width;
    private int height;
    private int hitPoints;
    private List<Fill> fills;
    private Fill stroke;

    /**
     * A constructor for a GeneralBlockCreator.
     * @param width the Block's width.
     * @param height the Block's height.
     * @param hitPoints the Block's hit points.
     * @param fills the Block's colours.
     * @param stroke the Block's stroke.
     */
    public GeneralBlockCreator(int width, int height, int hitPoints, List<Fill> fills, Fill stroke) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.fills = fills;
        this.stroke = stroke;
    }

    /**
     * A constructor for a GeneralBlockCreator.
     * @param width the Block's width.
     * @param height the Block's height.
     * @param hitPoints the Block's hit points.
     * @param fills the Block's colours.
     */
    public GeneralBlockCreator(int width, int height, int hitPoints, List<Fill> fills) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.fills = new ArrayList<>();
        Collections.copy(this.fills, fills);
        this.stroke = null;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle r = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        Block b = new Block(r, this.fills, this.stroke, this.hitPoints);
        return b;
    }

    /**
     * @return this GeneralBlockCreator's string format.
     */
    public String toString() {
        String s = "_BLOCKCREATOR_ \n";
        s += " width ->" + this.width + '\n';
        s += "height ->" + this.height + '\n';
        s += "hit_points -> " + this.hitPoints + '\n';
        s += this.fills.toString() + '\n';
        if (this.stroke != null) {
            s += this.stroke.toString() + '\n';
        }
        return s;
    }
}
