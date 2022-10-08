package game.highscores;

import java.io.Serializable;

/**
 * @author Yuval Ezra.
 * A Score Info.
 */
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = -7299136942167451458L;
    private String name;
    private int score;

    /**
     * A constructor for ScoreInfo.
     * @param name the name of the scorer.
     * @param score the score of the scorer.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the scorer's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the scorer's score.
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        String format = "{name: %s, score: %d}";
        return String.format(format, this.name, this.score);
    }
 }