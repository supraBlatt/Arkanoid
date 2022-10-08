package game.levels;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.animation.Animation;
import game.animation.AnimationRunner;
import game.animation.EndScreen;
import game.animation.MenuAnimation;
import game.decorators.KeyPressStoppableAnimation;
import game.highscores.HighScoresAnimation;
import game.highscores.HighScoresTable;
import game.highscores.ScoreInfo;
import game.levels.levelCreators.LevelSet;
import game.levels.levelCreators.LevelSetReader;
import game.menu.Menu;
import game.menu.ShowHiScoresTask;
import game.menu.Task;
import game.misc.Counter;

/**
 * @author Yuval Ezra. A Game Flow.
 */
public class GameFlow {

    private AnimationRunner ar;
    private KeyboardSensor keyboard;
    private Counter lives;
    private Counter score;
    private GUI gui;

    /**
     * A constructor for GameFlow.
     * @param ar the AnimationRunner.
     * @param keyboard the Keyboard
     * @param lives the amount of lives.
     * @param gui the GUI to present the dialog manager on.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor keyboard, int lives, GUI gui) {
        this.ar = ar;
        this.keyboard = keyboard;
        this.lives = new Counter(lives);
        this.score = new Counter(0);
        this.gui = gui;
    }

    /**
     * Runs the levels.
     * @param levels the levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        /*
         * Menu<Task<Void>> menu = this.initializeMenu(); // ... while (true) {
         * this.ar.run(menu); // wait for user selection Task<Void> task =
         * menu.getStatus(); task.run(); }
         */

        boolean win = true;
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboard, this.ar, this.lives, this.score);

            level.initialize();

            while (this.lives.getValue() > 0 && level.getRemainingBlocks().getValue() > 0) {
                level.playOneTurn();
            }

            if (this.lives.getValue() == 0) {
                win = false;
                break;
            }
        }

        File f = new File("highscores.txt");
        HighScoresTable t = HighScoresTable.loadFromFile(f);
        if (t.getRank(score.getValue()) <= t.size()) {
            DialogManager dialog = this.gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            t.add(new ScoreInfo(name, score.getValue()));
            try {
                t.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // displaying an end screen until space is pressed
        KeyPressStoppableAnimation end = new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                new EndScreen(this.score, win));

        // HighScoresAnimation end = new HighScoresAnimation(t, "p", this.keyboard);
        while (!end.shouldStop()) {
            this.ar.run(end);
        }
    }

    /**
     * Initializing the Menu of the game and the GameFlow overall.
     * @param levelSetsPath the File containing the level-sets.
     * @return a new Menu for this Game.
     */
    public Menu<Task<Void>> initializeMenu(String levelSetsPath) {
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(this.keyboard, this.ar);

        // create a new highscores file
        File f = new File("highscores.txt");
        HighScoresTable t = HighScoresTable.loadFromFile(f);
        Animation scores = new KeyPressStoppableAnimation(gui.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(t));

        menu.addSelection("h", "Hi scores", new ShowHiScoresTask(ar, scores));

        Task<Void> quitTask = new Task<Void>() {
            public Void run() {
                gui.close();
                System.exit(1);
                return null;
            }
        };
        menu.addSelection("q", "Quit", quitTask);

        // create the level-set-pick screen
        Menu<Task<Void>> levelScreen = new MenuAnimation<Task<Void>>(gui.getKeyboardSensor(), ar, "Choose a level set");
        InputStreamReader levelRead =
                new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsPath));
        List<LevelSet> levelSets = LevelSetReader.fromReader(levelRead);

        // add the level sets to the selections of 'levelSetScreen'
        for (LevelSet set : levelSets) {
            Task<Void> tempPlay = new Task<Void>() {
                public Void run() {
                    // creating the gameFlow
                    GameFlow.this.runLevels(set.getLvls());
                    try {
                        t.load(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // scores
                    Animation scorez = new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                            KeyboardSensor.SPACE_KEY, new HighScoresAnimation(t));
                    ar.run(scorez);
                    return null;
                }
            };
            levelScreen.addSelection(set.getKey(), set.getDesc(), tempPlay);
        }
        menu.addSubMenu("s", "Play", levelScreen);
        return menu;
    }
}