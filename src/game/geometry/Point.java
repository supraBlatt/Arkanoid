package game.geometry;
/**
 * @author Yuval Ezra
 * A point
 */
public class Point {
   private double x;
   private double y;

   /**
    * A Point constructor.
    * @param x the X coordinates of the Point
    * @param y the y coordinates of the Point
    */
   public Point(double x, double y) {
    this.x = x;
    this.y = y;
   }

   /**
    * Finds the distance of this Point to the other Point.
    * @param other the other Point.
    * @return the distance of this Point to the other Point.
    */
   public double distance(Point other) {
    double x1 = this.getX();
    double x2 = other.getX();
    double y1 = this.getY();
    double y2 = other.getY();
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
   }

   /**
    * Checks whether this Point and the other Point are the same.
    * @param other the other Point.
    * @return True if the Points are equal, False otherwise.
    */
   public boolean equals(Point other) {
    return (this.getX() == other.getX()) && (this.getY() == other.getY());
   }

   /**
    * @return the x values of this Point.
    */
   // Return the x and y values of this point
   public double getX() {
    return this.x;
   }

   /**
    * @return the y values of this Point.
    */
   public double getY() {
    return this.y;
   }

   /**
    * Sets this Point's x to newX.
    * @param newX the X to set to the Point.
    */
   public void setX(double newX) {
       this.x = newX;
   }

   /**
    * Sets this Point's y to newY.
    * @param newY the Y to set to the Point.
    */
   public void setY(double newY) {
       this.y = newY;
   }
}
