package game.levels.levelCreators;

import java.util.ArrayList;
import java.util.List;

import game.levels.LevelInformation;

/**
 * @author Yuval Ezra.
 * A level-set.
 */
public class LevelSet {
    private String key;
    private String desc;
    private List<LevelInformation> lvls;

    /**
     * An empty constructor for LevelSet.
     */
    public LevelSet() {
       this.key = "";
       this.desc = "";
       this.lvls = new ArrayList<LevelInformation>();
    }

    /**
     * A constructor for LevelSet.
     * @param key the level-set's 'key'.
     * @param desc the level-set's name.
     * @param lvls the level-set's LevelInformations.
     */
    public LevelSet(String key, String desc, List<LevelInformation> lvls) {
        this.key = key;
        this.desc = desc;
        this.lvls = lvls;
    }

    /**
     * @return this level-sets's key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return this level-sets's name.
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * @return this level-sets's levels.
     */
    public List<LevelInformation> getLvls() {
        return this.lvls;
    }

    /**
     * Sets this level-set's key to 'k'.
     * @param k this level-set's new key.
     */
    public void setKey(String k) {
        this.key = k;
    }

    /**
     * Sets this level-set's desc to 'd'.
     * @param d this level-set's new d.
     */
    public void setDesc(String d) {
        this.desc = d;
    }

    /**
     * Sets this level-set's levels to 'l'.
     * @param l this level-set's new level list.
     */
    public void setLvls(List<LevelInformation> l) {
        this.lvls = l;
    }

    /**
     * @return a String representation of this LevelSet.
     */
    public String toString() {
        return this.key + " " + this.desc;
    }
}
