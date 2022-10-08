package game;

import biuoop.GUI;
import game.animation.AnimationRunner;
import game.levels.GameFlow;
import game.menu.Menu;
import game.menu.Task;

/**
 * @author Yuval Ezra. The main class for the Arkanoid Game.
 */
public class Ass6Game {

    /**
     * The main class that runs the game.
     * @param args nothing.
     */
    public static void main(String[] args) {

        // screen borders
        int wid = 800, len = 600;

        // creating the level basics and Gameflow
        GUI gui = new GUI("Arkanoid", wid, len);
        int framesPerSecond = 60;
        AnimationRunner ar = new AnimationRunner(gui, framesPerSecond);
        int lives = 7;
        GameFlow g = new GameFlow(ar, gui.getKeyboardSensor(), lives, gui);

        // initialize menu and game
        Menu<Task<Void>> menu = g.initializeMenu("level_sets.txt");

        // run the menu
        while (true) {
            ar.run(menu); // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}
