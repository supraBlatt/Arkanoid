package game.menu;

import game.animation.Animation;

/**
 * @author Yuval Ezra.
 * A menu.
 * @param <T> the value type of the Menu's options.
 */
public interface Menu<T> extends Animation {

    /**
     * Adds a selection to this Menu.
     * @param key the letter that when pressed triggers this Menu's activation.
     * @param message the Menu's "name".
     * @param returnVal the Task that would be done when 'key' is pressed.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return this Menu's status - the task to be done after a key was pressed.
     */
    T getStatus();

    /**
     * Adds a sub-Menu to this Menu.
     * @param key the sub-Menu's key trigger that activates it.
     * @param message the sub-Menu's name.
     * @param subMenu the sub-Menu itself.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
 }
