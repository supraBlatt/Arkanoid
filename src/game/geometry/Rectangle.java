package game.geometry;
/**
 * @author Yuval Ezra
 *  A Rectangle.
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft upper left corner
     * @param width width
     * @param height height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified
     * line.
     *
     * @param line line to find intersections with
     * @return a list of the intersections between this rectangle and the given
     * line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        double thisX = this.upperLeft.getX();
        double thisY = this.upperLeft.getY();

        Point upperRight = new Point(thisX + this.width, thisY);
        Point bottomRight = new Point(thisX + this.width, thisY + this.height);
        Point bottomLeft = new Point(thisX, thisY + this.height);

        java.util.List<Line> borders = new java.util.ArrayList<>(4);
        borders.add(new Line(bottomRight, bottomLeft));
        borders.add(new Line(upperRight, this.upperLeft));
        borders.add(new Line(bottomLeft, this.upperLeft));
        borders.add(new Line(bottomRight, upperRight));

        java.util.List<Point> intersections = new java.util.LinkedList<Point>();

        // Go over the borders and find intersections with 'line'
        for (Line border : borders) {
            Point intersection = border.intersectionWith(line);
            if (intersection != null && !intersections.contains(intersection)) {
                intersections.add(intersection);
            }
        }
        return intersections;
    }

    /**
     * @return the width and height of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the Rectangle's height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return  the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}
