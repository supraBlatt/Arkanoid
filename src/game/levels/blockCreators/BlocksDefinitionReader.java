package game.levels.blockCreators;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.levels.levelCreators.LevelSpecificationReader;
import game.misc.Fill;
import game.misc.Tuple;

/**
 * @author Yuval Ezra.
 * A blocks-definition-reader.
 */
public class BlocksDefinitionReader {

    /**
     * Returning a BlocksFromSymbolsFactory object that creates Blocks according to
     * the definitions on the 'reader' Reader.
     * @param reader the definitions for the blocks (read from a blocks_definitions
     * File).
     * @return a new BlocksFromSymbolsFactory that maps from symbols ->
     * BlockCreators.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        Map<String, Integer> spacerWidths = new TreeMap<>();
        Map<String, BlockCreator> blockCreators = new TreeMap<>();

        List<String> defaults = new ArrayList<>();
        List<String> blockDefs = new ArrayList<>();
        List<String> spacerDefs = new ArrayList<>();

        BufferedReader read = new BufferedReader(reader);
        String line;
        try {
            line = read.readLine();

            // go over the file and split it into
            // block definitions, spacers and defaults
            while (line != null) {

                // # lines should be ignored - they are comments
                if (line.length() > 0 && line.charAt(0) != '#') {
                    splitDefaults(defaults, line);
                    splitBlockDefs(blockDefs, line);
                    splitSpacerDefs(spacerDefs, line);
                }
                line = read.readLine();
            }
        } catch (EOFException e) {
            System.out.println("Empty hi");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        Map<String, String> defaultMap = createDefaultMap(defaults);
        spacerWidths = makeSpacerWidth(spacerDefs);

        blockCreators = makeBlockCreators(defaultMap, blockDefs);
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Splits the defaultValues (default...) from the line.
     * @param defaults the list of defaultValues to add defaults to.
     * @param line the line in the File that's currently read.
     */
    public static void splitDefaults(List<String> defaults, String line) {
        Pattern pat = Pattern.compile("(?<=^default ).*");
        Matcher matcher = pat.matcher(line);

        // if a default value was found, add it to defaults.
        if (matcher.find()) {
            defaults.addAll(splitIntoProperty(line.substring(matcher.start(), matcher.end())));
        }
    }

    /**
     * Splits the blockDefs (bdef...) from the line.
     * @param blockDefs the list of blockDefs to add blockdefs to.
     * @param line the line in the File that's currently read.
     */
    public static void splitBlockDefs(List<String> blockDefs, String line) {
        Pattern pat = Pattern.compile("(?<=^bdef ).*[^\\s]");
        Matcher matcher = pat.matcher(line);

        // if a default value was found, add it to defaults.
        if (matcher.find()) {
            blockDefs.add(line.substring(matcher.start(), matcher.end()));
        }
    }

    /**
     * Splits the spacerDefs (sign,width) from the line.
     * @param spacerDefs the list of SpacerDefs to add spacers to.
     * @param line the line in the File that's currently read.
     */
    public static void splitSpacerDefs(List<String> spacerDefs, String line) {
        Pattern pat = Pattern.compile("(?<=^sdef ).*[^\\s]");
        Matcher matcher = pat.matcher(line);

        // if a default value was found, add it to defaults.
        if (matcher.find()) {
            spacerDefs.add(line.substring(matcher.start(), matcher.end()));
        }
    }

    /**
     * Creates a default values map. value_name -> value.
     * @param defaults the default list containing (value_name,value).
     * @return a default values map. value_name -> value.
     */
    public static Map<String, String> createDefaultMap(List<String> defaults) {

        // create a map of default values
        Map<String, String> defaultMap = new TreeMap<>();
        for (String def : defaults) {
            Tuple<String, String> temp = LevelSpecificationReader.splitProp(def);

            // if you have found a default property, add it to the map
            if (temp.getLeft() != null && temp.getRight() != null) {
                try {
                    defaultMap.put(temp.getLeft(), temp.getRight());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return defaultMap;
    }

    /**
     * Makes a map that maps a spacer-sign -> its width.
     * @param spacerDefs a List of (spacer,width) to parse.
     * @return a map that maps a spacer-sign -> its width.
     */
    public static Map<String, Integer> makeSpacerWidth(List<String> spacerDefs) {
        Map<String, Integer> spacerWidths = new TreeMap<>();
        for (String spacer : spacerDefs) {
            Pattern pat = Pattern.compile("(?<=^symbol:).(?= )");
            Matcher matcher = pat.matcher(spacer);
            String symbol = "";
            if (matcher.find()) {
                symbol = spacer.substring(matcher.start(), matcher.end());
            }

            pat = Pattern.compile("(?<=width:)\\d*(?= |)");
            matcher = pat.matcher(spacer);
            String width = "";
            if (matcher.find()) {
                width = spacer.substring(matcher.start(), matcher.end());
            }

            // if the symbol and width were found
            if (symbol.length() > 0 && width.length() > 0) {
                spacerWidths.put(symbol, Integer.parseInt(width));
            }
        }
        return spacerWidths;
    }

    /**
     * Makes a map of BlockSymbol -> BlockCreator.
     * @param defaultMap the given default values map.
     * @param blockDefs the given lines that start with 'bdef', which contain
     * definitions for blocks.
     * @return a Map that maps every Block-symbol to it's according BlockCreator.
     */
    private static Map<String, BlockCreator> makeBlockCreators(Map<String, String> defaultMap, List<String> blockDefs) {
        Map<String, BlockCreator> blocks = new TreeMap<>();
        for (String def : blockDefs) {
            String symbol = "";
            int width = -1;
            int height = -1;
            int hitPoints = -2;
            List<Fill> fills = new ArrayList<>();
            Map<String, Fill> tempFills = new TreeMap<>();
            Fill stroke = null;

            // split into properties
            List<String> props = splitIntoProperty(def);
            for (String prop : props) {

                // split the property
                Tuple<String, String> split = LevelSpecificationReader.splitProp(prop);
                String left = split.getLeft();
                String right = split.getRight();

                // if it's a normal fill
                if (left.equals("fill")) {

                    Fill f = LevelSpecificationReader.splitBackground(right);
                    tempFills.put("1", f);
                }

                // if it's a fill-k
                Pattern pat = Pattern.compile("(?<=fill-?)\\d+");
                Matcher matcher = pat.matcher(left);
                if (matcher.find()) {
                    // CHECK IF INT
                    String k = left.substring(matcher.start(), matcher.end());
                    Fill f = LevelSpecificationReader.splitBackground(right);
                    tempFills.put(k, f);
                } else {

                    // check what the property is, then
                    switch (left) {
                    case "symbol":
                        symbol = right;
                        break;

                    case "width":
                        width = Integer.parseInt(right);
                        break;

                    case "hit_points":
                        hitPoints = Integer.parseInt(right);
                        break;

                    case "height":
                        height = Integer.parseInt(right);
                        break;

                    case "stroke":
                        stroke = LevelSpecificationReader.splitBackground(right);
                        break;

                    default:
                        break;
                    }
                }
            }

            // checking the validation of the properties.
            if (symbol.length() == 0) {
                throw new RuntimeException("Wrong symbol");
            }

            if (width == -1) {
                if (defaultMap.containsKey("width")) {
                    width = Integer.parseInt(defaultMap.get("width"));
                } else {
                    throw new RuntimeException("Wrong width");
                }
            }

            if (height == -1) {
                if (defaultMap.containsKey("height")) {
                    height = Integer.parseInt(defaultMap.get("height"));
                } else {
                    throw new RuntimeException("Wrong height");
                }
            }

            if (hitPoints == -2) {
                if (defaultMap.containsKey("hit_points")) {
                    hitPoints = Integer.parseInt(defaultMap.get("hit_points"));
                } else {
                    throw new RuntimeException("Wrong hit points");
                }
            }

            if (stroke == null) {
                if (defaultMap.containsKey("stroke")
                        && LevelSpecificationReader.splitBackground(defaultMap.get("stroke")) != null) {
                    stroke = LevelSpecificationReader.splitBackground(defaultMap.get("stroke"));
                }
            }

            fills = handleFill(tempFills, hitPoints, defaultMap);
            GeneralBlockCreator b = new GeneralBlockCreator(width, height, hitPoints, fills, stroke);
            blocks.put(symbol, b);
        }
        return blocks;
    }

    /**
     * Takes a level as a string, and puts in a list all the a:b parts in it.
     * @param level the String that is the game-level.
     * @return a List containing all a:b parts in 'level'.
     */
    public static List<String> splitIntoProperty(String level) {
        List<String> properties = new LinkedList<>();
        Pattern pat = Pattern.compile("((\\w|-)*:.*?(?= [\\w-]+:))|(\\w|-)*:.*?(?=$)");
        Matcher matcher = pat.matcher(level);
        while (matcher.find()) {
            properties.add(level.substring(matcher.start(), matcher.end()));
        }
        return properties;
    }

    /**
     * Creates a new Fill list from temporary fills, amount of hit_points and a
     * default values map. fills in all fill-1....fill-(hit_points) with according
     * fills. If it can't find fill-k, it tries putting the default value for fill-k
     * instead, and if there's no default value, it puts fill-1 instead.
     * @param tempFills the current Fills, without 'filling' the rest of
     * fill-1...fill-(hit_points).
     * @param hitPoints the current Block's hitpoints.
     * @param defaultMap a map containing all default values given in the
     * blocks_definition file.
     * @return a new Fill List that contains fill-1....fil-k.
     */
    private static List<Fill> handleFill(Map<String, Fill> tempFills, int hitPoints, Map<String, String> defaultMap) {

        List<Fill> fills = new ArrayList<>();

        Fill defFill = null;
        if (!tempFills.containsKey("1")) {
            throw new RuntimeException("No default fill");
        }

        defFill = tempFills.get("1");
        fills.add(defFill);
        for (int i = 2; i <= hitPoints; i++) {
            if (!tempFills.containsKey(Integer.toString(i))) {
                if (defaultMap.containsKey("fill-" + i)) {
                    String f = defaultMap.get("fill-" + i);
                    Fill fill = LevelSpecificationReader.splitBackground(f);
                    fills.add(fill);
                } else {
                    fills.add(defFill);
                }
            } else {
                fills.add(tempFills.get(Integer.toString(i)));
            }
        }

        return fills;
    }
}