package game.levels.blockCreators;

import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A blockCreator.
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     * @param xpos the x-position of the Block.
     * @param ypos the y-position of the Block.
     * @return a new Block, which its left corner is located at
     * (xpos, ypos).
     */
    Block create(int xpos, int ypos);
}
