package game.objects.sprite;
import biuoop.DrawSurface;
import game.geometry.Point;
import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A level indicator.
 */
public class LevelIndicator implements Sprite {
    private String name;
    private Block board;

    /**
     * A constructor for LevelIndicator.
     * @param name the name of the level.
     * @param board the block on which the indicator is presented.
     */
    public LevelIndicator(String name, Block board) {
        this.name = name;
        this.board = board;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Point upperLeft = this.board.getCollisionRectangle().getUpperLeft();
        double x = upperLeft.getX();
        double y = upperLeft.getY();

        d.setColor(java.awt.Color.BLACK);
        int drawX =  (int) (x +  this.board.getWidth()) - 250;
        int drawY =  (int) (y + this.board.getHeight() / 4) + 9;
        d.drawText(drawX, drawY, "Level Name: " + this.name, 15);
    }

    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub
    }
}