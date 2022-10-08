package game.decorators;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animation.Animation;

/**
 * @author Yuval Ezra.
 * A key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;

    /**
     * A constructor for KeyPressStoppableAnimation.
     * @param sensor the KeyboardSensor.
     * @param key the key that should be pressed to stop the animation.
     * @param animation the Animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }

        this.animation.doOneFrame(d, dt);
    }

    @Override
    public boolean shouldStop() {
        if (this.sensor.isPressed(this.key) && !this.isAlreadyPressed) {
            return true;
        }
        return false;
    }
 }