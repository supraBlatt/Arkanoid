package game.levels.levelCreators;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import game.levels.LevelInformation;
import game.levels.blockCreators.BlocksDefinitionReader;
import game.levels.blockCreators.BlocksFromSymbolsFactory;
import game.misc.Fill;
import game.misc.Tuple;
import game.objects.Block;
import game.objects.ball.Velocity;

/**
 * @author Yuval Ezra.
 * A level-specification-reader.
 */
public class LevelSpecificationReader {

    /**
     * Gets a Reader, and returns a new List of LevelInformations specified in 'reader'.
     * @param reader the Reader specifying what LevelInformations to create.
     * @return a list of LevelInformations created by the instructioons in 'reader'.
     */
    public static List<LevelInformation> fromReader(Reader reader) {
        List<LevelInformation> finishedLevels = new LinkedList<>();

        // splitting the file into levels
        List<String> levels = levelSplit(reader);

        // creating each level seperately
        for (String level : levels) {
            LevelInformation levelInfo = createLevel(level);
            if (levelInfo != null) {
                finishedLevels.add(levelInfo);
            }
        }
        return finishedLevels;
    }

    /**
     * Makes a file a 1-liner File, with each line being split by a 'space'.
     * @param reader the File's Reader.
     * @return a new 1-line String representing the File which
     * reader was its Reader.
     */
    public static String makeOneLine(Reader reader) {
        BufferedReader r = new BufferedReader(reader);
        String file = "";
        String line;
        try {
            line = r.readLine();

            // # lines should be ignored - they are comments
            while (line != null) {
                if (line.length() > 0 && line.charAt(0) != '#') {
                    file += line + " ";
                }
                line = r.readLine();
            }
        } catch (EOFException e) {
            System.out.println("Empty hi");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                r.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * Splits the one-lined File string into levels.
     * @param reader the Reader of the File containing level-descriptions.
     * @return a List containing all levels in the File (each in its own place in the List).
     */
    public static List<String> levelSplit(Reader reader) {
        List<String> levels = new LinkedList<>();
        String file = makeOneLine(reader);
        Pattern pat = Pattern.compile("START_LEVEL.*?END_LEVEL");
        Matcher matcher = pat.matcher(file);
        while (matcher.find()) {
            levels.add(file.substring(matcher.start(), matcher.end()));
        }
        return levels;
    }

    /**
     * Splits a line of a:b c:d ... into a list [a:b, c:d,...]
     * @param level a:b c:d ...
     * @return a list [a:b, c:d,...]
     */
    public static List<String> splitIntoProperty(String level) {
        List<String> properties = new LinkedList<>();
        Pattern pat = Pattern.compile("((\\w|-)*:.*?(?= [\\w-]+:))|((\\w|-)*:.*?(?= START_BLOCKS))");
        Matcher matcher = pat.matcher(level);
        while (matcher.find()) {
            properties.add(level.substring(matcher.start(), matcher.end()));
        }
        return properties;
    }

    /**
     * Takes an a:b property and splits it into a b.
     * @param property the a:b property.
     * @return a b Tuple of the property that was split.
     */
    public static Tuple<String, String> splitProp(String property) {
        Tuple<String, String> t = new Tuple<>();

        // split the property
        Pattern pat = Pattern.compile(".*(?=:)");
        Matcher matcher = pat.matcher(property);
        if (matcher.find()) {
            t.setLeft(property.substring(matcher.start(), matcher.end()));
        }
        pat = Pattern.compile("(?<=:).*");
        matcher = pat.matcher(property);
        if (matcher.find()) {
            t.setRight(property.substring(matcher.start(), matcher.end()));
        }
        return t;
    }

    /**
     * Takes a angle_1,speed_1 angle_2,speed_2 ... and makes from it a list of
     * Velocities.
     * @param right the angle_1,speed_1 angle_2,speed_2 ... part.
     * @return a new List of velocities made according to 'right'.
     */
    public static List<Velocity> makeVelocities(String right) {
        List<Velocity> ballVelocities = new ArrayList<>();

        List<String> velocities = new ArrayList<>();
        Pattern pat = Pattern.compile("[^\\s]*,[^\\s]*");
        Matcher matcher = pat.matcher(right);
        while (matcher.find()) {
            velocities.add(right.substring(matcher.start(),
                                           matcher.end()));
        }

        // for each velocity we found, turn it into a Velocity object
        for (String v : velocities) {
            int angle = 0;
            int speed = -1;
            // look for the angle
            pat = Pattern.compile(".*(?=,)");
            matcher = pat.matcher(v);
            if (matcher.find()) {
                angle = Integer.parseInt(v.substring(matcher.start(),
                        matcher.end()));
            }

            // look for the speed
            pat = Pattern.compile("(?<=,).*");
            matcher = pat.matcher(v);
            if (matcher.find()) {
                speed = Integer.parseInt(v.substring(matcher.start(),
                        matcher.end()));
            }
            ballVelocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return ballVelocities;
    }

    /**
     * Creates a LevelInformation level.
     * @param level the level's String representation.
     * @return a new LevelInformation created from 'level'.
     */
    public static LevelInformation createLevel(String level) {

        // initializing levelInformation properties
        String levelName = "";
        List<Velocity> ballVelocities = new ArrayList<>();
        Fill background = null;
        int paddleSpeed = -1;
        int paddleWidth = -1;
        String blockDefinitions = "";
        int blocksStartX = -1;
        int blocksStartY = -1;
        int rowHeight = -1;
        int numBlocks = -1;

        List<String> props = splitIntoProperty(level);
        for (String prop : props) {

            // split the property
            Tuple<String, String> split = splitProp(prop);
            String left = split.getLeft();
            String right = split.getRight();

            // check what each property is
            switch (left) {
                case "level_name":
                    levelName = right;
                    break;

                case "ball_velocities":
                    ballVelocities = makeVelocities(right);
                    break;

                case "background":
                    if (right != null) {
                        background = splitBackground(right);
                    }
                    break;

                case "paddle_speed":
                    paddleSpeed = Integer.parseInt(right);
                    break;

                case "paddle_width":
                    paddleWidth = Integer.parseInt(right);
                    break;

                case "block_definitions":
                    blockDefinitions = right;
                    break;

                case "blocks_start_x":
                    blocksStartX = Integer.parseInt(right);
                    break;

                case "blocks_start_y":
                    blocksStartY = Integer.parseInt(right);
                    break;

                case "row_height":
                    rowHeight = Integer.parseInt(right);
                    break;

                case "num_blocks":
                    numBlocks = Integer.parseInt(right);
                    break;

                default:
                    break;
            }
        }

        // if the data was insufficient
        if (levelName.length() == 0 || background == null
            || (background.getColor() == null && background.getImage() == null)
            || paddleSpeed < 0 || paddleWidth < 0 || rowHeight < 0 || numBlocks < 0 || blockDefinitions.length() == 0) {
            throw new RuntimeException("Incorrect data");
        }

        // creating the blocks list
        String blockSymbols = splitBlockPart(level);
        BlocksFromSymbolsFactory bReader = null;
        InputStreamReader levelRead =
                new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions));
        bReader = BlocksDefinitionReader.fromReader(levelRead);

        List<Block> blocks = new ArrayList<>();

        int curX = blocksStartX;
        int curY = blocksStartY;

        for (int i = 0; i < blockSymbols.length(); i++) {
            String curSymbol = Character.toString(blockSymbols.charAt(i));
            if (curSymbol.equals(" ")) {
                curX = blocksStartX;
                curY += rowHeight;
            } else if (bReader.isBlockSymbol(curSymbol)) {
                Block b = (bReader.getBlock(curSymbol, curX, curY));
                blocks.add(b);
                curX += b.getWidth();
            } else if (bReader.isSpaceSymbol(curSymbol)) {
                curX += bReader.getSpaceWidth(curSymbol);
            }
        }
        int wid = 800, len = 600;
        Tuple<Integer, Integer> levelScale = new Tuple<>(wid, len);
        return new GeneralLevelCreator(levelName, ballVelocities,
                                       background, paddleSpeed, paddleWidth,
                                       blocks, levelScale);
    }

    /**
     * Splits the part between START_BLOCKS and END_BLOCKS (not including) in 'level'.
     * @param level the level-description File string given.
     * @return the part between START_BLOCKS and END_BLOCKS in 'level'.
     */
    public static String splitBlockPart(String level) {
        String blocks = "";
        Pattern pat = Pattern.compile("(?<=START_BLOCKS ).*(?= END_BLOCKS)");
        Matcher matcher = pat.matcher(level);
        if (matcher.find()) {
            blocks = level.substring(matcher.start(), matcher.end());
        }
        return blocks;
    }

    /**
     * Takes a fill-k:a and turns it into a Fill.
     * @param background fill-k:a.
     * @return the newely created Fill.
     */
    public static Fill splitBackground(String background) {
        Pattern pat = Pattern.compile("(?<=color\\()[^\\s]*(?=\\))");
        Matcher matcher = pat.matcher(background);

        // if it's a colour
        if (matcher.find()) {
            String color = background.substring(matcher.start(),
                    matcher.end());

            // if it's an rgb - (
            String rgbIsh = "(0|1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([0-9][0-9])";
            pat = Pattern.compile("RGB\\(" + rgbIsh + "," + rgbIsh + "," + rgbIsh + "\\)");
            matcher = pat.matcher(color);

            // if it's indeed an rgb, make it
            if (matcher.find()) {

                List<Integer> values = new ArrayList<>();
                pat = Pattern.compile(rgbIsh);
                matcher = pat.matcher(color);

                // add the rgb values, create a colour and return a new Fill.
                while (matcher.find()) {
                    values.add(Integer.parseInt(color.substring(matcher.start(),
                            matcher.end())));
                }
                Color c = new Color(values.get(0), values.get(1), values.get(2));
                Fill f = new Fill(c);
                return f;

            } else {
                pat = Pattern.compile("(?<=color\\()\\w*(?=\\))");
                matcher = pat.matcher(background);

                // if it's a normal colour
                if (matcher.find()) {
                    String col = background.substring(matcher.start(),
                            matcher.end());
                    Color c = findColor(col);
                    Fill f = new Fill(c);
                    return f;
                }
            }

            // it's a fake.
            return null;
        } else {
            pat = Pattern.compile("(?<=image\\().*(?=\\))");
            matcher = pat.matcher(background);

            // if it's an image, add it to Fill and return.
            if (matcher.find()) {
                String imagePath = background.substring(matcher.start(),
                        matcher.end());
                Image image;
                try {
                    image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imagePath));
                    Fill f = new Fill(image);
                    return f;
                } catch (IOException e) {
                    System.out.println("Couldn't open image " + imagePath);
                    e.printStackTrace();
                }
                return null;
            }

            // it's a fake
            return null;
        }
    }

    /**
     * Checks what Color was the inputted Color.
     * @param color the inputted Color.
     * @return the according Color representing 'color'.
     */
    private static Color findColor(String color) {
        switch (color) {
            case "black":
                return Color.black;

            case "cyan":
                return Color.cyan;

            case "gray":
                return Color.gray;

            case "lightGray":
                return Color.lightGray;

            case "green":
                return Color.green;

            case "orange":
                return Color.orange;

            case "pink":
                return Color.pink;

            case "red":
                return Color.red;

            case "white":
                return Color.white;

            case "yellow":
                return Color.yellow;

            case "blue":
                return Color.blue;

           default:
                return null;
        }
    }
}