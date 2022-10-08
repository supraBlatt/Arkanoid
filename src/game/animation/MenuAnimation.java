package game.animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.menu.Menu;
import game.menu.Selection;

/**
 * @author Yuval Ezra. A menu animation.
 * @param <T> the value type of the Menu's options.
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<Selection<T>> selections;
    private AnimationRunner ar;

    private List<Selection<Menu<T>>> subMenus;
    private KeyboardSensor keyboard;
    private T choice;
    private String title;


    /**
     * A constructor for menuAnimation.
     * @param keyboard the KeyboardSensor.
     * @param ar the animationRunner to run this menu animation on.
     */
    public MenuAnimation(KeyboardSensor keyboard, AnimationRunner ar) {
        this.subMenus = new ArrayList<>();
        this.selections = new ArrayList<>();
        this.keyboard = keyboard;
        this.choice = null;
        this.ar = ar;
        this.title = "Menu";
    }

    /**
     * A constructor for menuAnimation.
     * @param keyboard the KeyboardSensor.
     * @param ar the animationRunner to run this menu animation on.
     * @param title this MenuAnimation's title.
     */
    public MenuAnimation(KeyboardSensor keyboard, AnimationRunner ar, String title) {
        this.selections = new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.keyboard = keyboard;
        this.choice = null;
        this.ar = ar;
        this.title = title;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.choice = null;

        // black background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // drawing the pause-text
        d.setColor(Color.WHITE);

        // final score
        int startY = d.getHeight() / 4;
        int startX = d.getWidth() / 4 + 30;
        d.drawText(startX, startY, this.title, 40);

        for (Selection<T> s : this.selections) {
            startY += 25;
            d.drawText(startX, startY, s.getKey() + " " + s.getDesc(), 20);
        }

        for (Selection<Menu<T>> sel : this.subMenus) {
            startY += 25;
            d.drawText(startX, startY, sel.getKey() + " " + sel.getDesc(), 20);
        }

        // check if a key was pressed
        for (Selection<T> s: this.selections) {
            if (this.keyboard.isPressed(s.getKey())) {
                this.choice = s.getValue();
                break;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        for (Selection<T> s : this.selections) {
            if (this.keyboard.isPressed(s.getKey())) {
                this.choice = s.getValue();
                return true;
            }
        }
        for (Selection<Menu<T>> sel: this.subMenus) {
            if (this.keyboard.isPressed(sel.getKey())) {
                ar.run(sel.getValue());
                this.choice = sel.getValue().getStatus();
                return true;
            }
        }
        return this.choice != null;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<T>(key, message, returnVal));
    }

    @Override
    public T getStatus() {
        T val = this.choice;
        this.choice = null;
        return val;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new Selection<Menu<T>>(key, message, subMenu));
    }
}
