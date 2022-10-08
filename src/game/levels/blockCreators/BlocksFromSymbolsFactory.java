package game.levels.blockCreators;

import java.util.Map;

import game.objects.Block;

/**
 * @author Yuval Ezra.
 * A blocks-from-symbols-factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * A constructor for BlocksFromSymbolsFacory.
     * @param spacerWidths the Map that maps the spacer_symbols -> spacer_widths.
     * @param blockCreators the Map that maps the block_symbols -> blockCreators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Checks if 's' is a valid space symbol.
     * @param s the string to check if it's a valid space symbol.
     * @return true if yes, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
       return this.spacerWidths.containsKey(s);
    }

    /**
     * Checks if 's' is a valid block symbol.
     * @param s the string to check if it's a valid block symbol.
     * @return true if yes, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Creates a block that is associated with symbol 's' and that will be
     * located at (xpos, ypos).
     * @param s the Block's symbol.
     * @param xpos the Block's left-corner's x-axis position.
     * @param ypos the Block's left-corner's y-axis position.
     * @return a Block according to the definitions associated with
     * symbol 's'. The Block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return (this.blockCreators.get(s)).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s the spacer symbol.
     * @return the width associated with 's'.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
 }

