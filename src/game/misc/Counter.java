package game.misc;
/**
 * @author Yuval Ezra
 * A simple counter.
 */
public class Counter {

    private int count;

    /**
     * A constructor for a Counter.
     * @param count the current count.
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * Adds 'number' to the current count.
     * @param number the number to add to the
     * current count.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Subtracts 'number' from the current count.
     * @param number the number to substract from the
     * current count.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}