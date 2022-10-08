package game.objects.sprite;
import biuoop.DrawSurface;
import game.geometry.Point;
import game.misc.Counter;
import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    private Block board;

    /**
     * A constructor for LivesIndicator.
     * @param lives the amount of starting lives.
     * @param board the block on which the indicator is presented.
     */
    public LivesIndicator(Counter lives, Block board) {
        this.lives = lives;
        this.board = board;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Point upperLeft = this.board.getCollisionRectangle().getUpperLeft();
        double x = upperLeft.getX();
        double y = upperLeft.getY();

        d.setColor(java.awt.Color.BLACK);
        int drawX =  (int) (x +  this.board.getWidth() / 6) - 50;
        int drawY =  (int) (y + this.board.getHeight() / 4) + 9;
        d.drawText(drawX, drawY, "Lives: " + Integer.toString(this.lives.getValue()), 15);
    }

    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub
    }
}
