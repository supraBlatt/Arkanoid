package game.levels.levelCreators;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import game.levels.LevelInformation;
import game.misc.Tuple;

/**
 * @author Yuval Ezra.
 * A level-set-reader.
 */
public class LevelSetReader {

    /**
     * Gets a reader that is a level-set.txt reader, and build a
     * list of levels from its description.
     * @param reader the level-set reader.
     * @return a list of levels created from the description from
     * 'reader'.
     */
    public static List<LevelSet> fromReader(Reader reader) {
        List<LevelSet> finishedLevelSets = new ArrayList<>();
        int numLine = 1;
        String key = "";
        String levelName = "";

        BufferedReader re = new BufferedReader(reader);
        String line = "";
        try {
            line = re.readLine();

            while (line != null) {
                // if the line number is odd, it contains a name and a kye for this level-set
                if (numLine % 2 == 1) {
                    Tuple<String, String> t = LevelSpecificationReader.splitProp(line);
                    key = t.getLeft();
                    levelName = t.getRight();
                } else {

                    // if the line number is even, it contains the path to the
                    // level-specification-reader file.
                    InputStreamReader r =
                                new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(line));
                    List<LevelInformation> l = LevelSpecificationReader.fromReader(r);
                    LevelSet s = new LevelSet(key, levelName, l);
                    finishedLevelSets.add(s);

                    key = "";
                    levelName = "";
                }
                line = re.readLine();
                numLine++;
            }
        } catch (EOFException e) {
            System.out.println("Empty hi");
        } catch (IOException e) {
            System.out.println("failed to read " + line);
            e.printStackTrace();
        } finally {
            if (re != null) {
                try {
                    re.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return finishedLevelSets;
    }
}

