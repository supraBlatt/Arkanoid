package game.misc;

import java.awt.Color;
import java.awt.Image;

/**
 * @author Yuval Ezra.
 * A fill for an object.
 */
public class Fill {
    private Image image;
    private Color color;

    /**
     * A constructor for Fill.
     * @param image an Image that is the 'fill'.
     */
    public Fill(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * A constructor for Fill.
     * @param color a Color that is the 'fill'.
     */
    public Fill(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * @return this Image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * @return this Color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return this Fill's String format.
     */
    public String toString() {
        if (this.color != null) {
            return this.color.toString();
        } else if (this.image != null) {
            return this.image.toString();
        }
        return "";
    }
}
