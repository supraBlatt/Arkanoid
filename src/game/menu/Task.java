package game.menu;

/**
 * @author Yuval Ezra.
 * A Task.
 * @param <T> the task's value type.
 */
public interface Task<T> {

    /**
     * @return the Task to run.
     */
    T run();
 }