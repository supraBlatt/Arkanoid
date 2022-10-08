package game.collections;

import game.geometry.Line;
import game.geometry.Point;
import game.objects.collidable.Collidable;
import game.objects.collidable.CollisionInfo;

/**
 * @author Yuval Ezra
 * A game environment.
 */
public class GameEnvironment {

    private java.util.List<Collidable> obstacles;

    /**
     * A GameEnvironemnt constructor.
     */
    public GameEnvironment() {
        this.obstacles = new java.util.LinkedList<Collidable>();
    }

    /**
     * Another GameEnvironment constructor.
     * @param obstacles the obstacles to add to this GameEnvironment.
     */
    public GameEnvironment(java.util.List<Collidable> obstacles) {
        this.obstacles = new java.util.LinkedList<Collidable>();
        this.obstacles.addAll(obstacles);
    }

    /**
     * Add the given Collidable to the environment.
     * @param c the new Collidable to add for the GameEnvironment.
     */
    public void addCollidable(Collidable c) {
        this.obstacles.add(c);
    }

    /**
     * Removes Collidable c from this GameEnvironment.
     * @param c the Collidable to remove from this GameEnvironment.
     */
    public void removeCollidable(Collidable c) {
        this.obstacles.remove(c);
    }

    /**
     * @return this GameEnvironemnt's obstacles.
     */
    public java.util.List<Collidable> getObstacles() {
        return this.obstacles;
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * @param trajectory the moving object's trajectory.
     * @return If this object will not collide with any of the Collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollision = null;
        Collidable closestCollidable = null;

        // Go over all obstacles
        for (Collidable c : this.obstacles) {
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            // If there's an intersection between the obstacle and the trajectory
            if (collision != null) {

                // If there's no closest Collidable, make this one the new closest
                if (closestCollision == null) {
                    closestCollidable = c;
                    closestCollision = collision;
                } else if (trajectory.start().distance(collision) < trajectory.start().distance(closestCollision)) {

                    // And if there is already one, and that obstacle's distance from the start of the line is shorter
                    // make it the new closest Collidable.
                    closestCollidable = c;
                    closestCollision = collision;
                }
            }
        }
        if (closestCollision == null) {
            return null;
        }
        CollisionInfo info = new CollisionInfo(closestCollision, closestCollidable);
        return info;
    }

    /**
     * @param trajectory the Object's trajectory
     * @return A list of CollisionInfos, containing the Collidables the trajectory
     * would cross.
     */
    public java.util.List<CollisionInfo> getClosestCollisions(Line trajectory) {
        java.util.List<CollisionInfo> collisions = new java.util.LinkedList<>();
        double minDistance = trajectory.length();

        // Go over all obstacles
        for (Collidable c : this.obstacles) {
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            // If there's an intersection between the obstacle and the trajectory
            if (collision != null) {

                // If it's the new closest obstacle, clear the list and add it
                if (trajectory.start().distance(collision) < minDistance) {
                    minDistance = trajectory.start().distance(collision);
                    collisions.clear();
                    collisions.add(new CollisionInfo(collision, c));
                } else if (trajectory.start().distance(collision) == minDistance) {

                    // If its distance is equal to to other obstacles, add it to the list.
                    collisions.add(new CollisionInfo(collision, c));
                }
            }
        }
        if (collisions.size() == 0) {
            return null;
        }
        return collisions;
    }
}
