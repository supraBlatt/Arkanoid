package game.objects.sprite;
import biuoop.DrawSurface;
import game.geometry.Point;
import game.misc.Counter;
import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A lives indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private Block board;

    /**
     * A constructor for ScoreIndicator.
     * @param score the amount of starting score.
     * @param board the block on which the indicator is presented.
     */
    public ScoreIndicator(Counter score, Block board) {
        this.score = score;
        this.board = board;
    }

    @Override
    public void drawOn(DrawSurface d) {
        board.drawOn(d);
        Point upperLeft = this.board.getCollisionRectangle().getUpperLeft();
        double x = upperLeft.getX();
        double y = upperLeft.getY();

        d.setColor(java.awt.Color.BLACK);
        int drawX =  (int) (x +  this.board.getWidth() / 2) - 50;
        int drawY =  (int) (y + this.board.getHeight() / 2) + 4;
        d.drawText(drawX, drawY, "Score: " + Integer.toString(this.score.getValue()), 15);
    }

    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub
    }
}
