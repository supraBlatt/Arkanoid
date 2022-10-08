package game.misc;

/**
 * @author Yuval Ezra.
 * A Tuple.
 * @param <X> the left value's type.
 * @param <Y> the right value's type.
 */
public class Tuple<X, Y> {
    private X left;
    private Y right;

    /**
     * An empty constructor for Tuple.
     */
    public Tuple() {
        this.left = null;
        this.right = null;
    }

    /**
     * A constructor for Tuple.
     * @param left the left Value in the Tuple.
     * @param right the right Value in the Tuple.
     */
    public Tuple(X left, Y right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return this Tuple's left value.
     */
    public X getLeft() {
        return this.left;
    }

    /**
     * @return this Tuple's right value.
     */
    public Y getRight() {
        return this.right;
    }

    /**
     * Sets this Tuple's left value to 'l'.
     * @param l this Tuple's new left value.
     */
    public void setLeft(X l) {
        this.left = l;
    }

    /**
     * Sets this Tuple's right value to 'r'.
     * @param r this Tuple's new right value.
     */
    public void setRight(Y r) {
        this.right = r;
    }
}


