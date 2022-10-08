package game.menu;

/**
 * @author Yuval Ezra.
 * A Selection for menu-purposes.
 * @param <T> the type of the Selection's value.
 */
public class Selection<T> {
    private String key;
    private String desc;
    private T value;

    private Task<T> t;

    /**
     * A constructor for a Selection.
     * @param key the key-char for the Selection.
     * @param desc the description of the Selection.
     * @param value the value the Selection holds.
     */
    public Selection(String key, String desc, T value) {
        this.key = key;
        this.desc = desc;
        this.value = value;
        this.t = null;
    }

    /**
     * A constructor for a Selection.
     * @param key the key-char for the Selection.
     * @param desc the description of the Selection.
     * @param t the Task of this Selection.
     */
    public Selection(String key, String desc, Task<T> t) {
        this.key = key;
        this.desc = desc;
        this.t = t;
        this.value = null;
    }

    /**
     * @return this Selection's key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return this Selection's description.
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * @return this Selection's value.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * @return this Selection's Task.
     */
    public Task<T> getTask() {
        return this.t;
    }
}
