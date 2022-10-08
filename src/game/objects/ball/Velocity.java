package game.objects.ball;

import game.geometry.Point;

/**
 * @author Yuval Ezra
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * A Velocity constructor.
     * @param dx the dx of the Velocity.
     * @param dy the dy of the Velocity.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns a new Velocity created from a given angle and speed.
     * @param angle the angle of the Velocity.
     * @param speed the speed of the Velocity.
     * @return a new Velocity with the angle 'angle' and speed 'speed'.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        if ((angle / 360) == (int) (angle / 360)) {
            angle = 0;
        }
        double radAngle = Math.toRadians(angle);
        double dx = Math.sin(radAngle) * speed;
        double dy = -Math.cos(radAngle) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Take a Point with position (x,y) and return a new Point
     * with position (x + dx, y + dy).
     * @param p the Point to apply the Velocity to.
     * @return the new point (x + dx, y + dy).
     */
    public Point applyToPoint(Point p) {
        Point newP = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return newP;
    }

    /**
     * @return Velocity's dx.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * @return Velocity's dy.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * @return the square root of (dx^2 + dy^2).
     * The Velocity's overall size.
     */
    public double getSpeed() {
        return Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
    }

    /**
     * Sets the Velocity's DX to 'dX'.
     * @param dX the new DX.
     */
    public void setDX(double dX) {
        this.dx = dX;
    }

    /**
     * Sets the Velocity's DY to 'dY'.
     * @param dY the new DY.
     */
    public void setDY(double dY) {
        this.dy = dY;
    }
}