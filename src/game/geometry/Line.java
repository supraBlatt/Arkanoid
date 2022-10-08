package game.geometry;
/**
 * @author Yuval Ezra
 * A Line.
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * A constructor for a Line.
     * @param start the start of the Line
     * @param end the end of the Line
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * A constructor for a Line.
     * @param x1 start's x
     * @param y1 start's y
     * @param x2 end's x
     * @param y2 end's y
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return the length of the Line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * @return the middle point of the Line
     */
    public Point middle() {
        double newX = (this.start.getX() + this.end.getX()) / 2;
        double newY = (this.start.getY() + this.end.getY()) / 2;
        Point mid = new Point(newX, newY);
        return mid;
    }

    /**
     * @return the start point of the Line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the Line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks whether this Line and another Line intersect.
     * @param other another Line to check this Line's
     * intersection with.
     * @return True if the lines intersect, False otherwise.
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * Finds the intersecion point of this Line with another Line.
     * @param other another Line to check this Line's
     * intersection with
     * @return the Point of intersection of the lines. Null if
     * non-existent.
     */
    public Point intersectionWith(Line other) {
        double thisX1 = this.start.getX();
        double thisY1 = this.start.getY();
        double thisX2 = this.end.getX();
        double thisY2 = this.end.getY();

        double otherX1 = other.start.getX();
        double otherY1 = other.start.getY();
        double otherX2 = other.end.getX();
        double otherY2 = other.end.getY();

        // y = mx + b
        double m1, m2;
        double b1, b2;

        double newX, newY;
        Point intersection = null;

        // both are perpendicular to the X axis
        if (thisX1 == thisX2 && otherX1 == otherX2) {
            return null;
        }

        // this line is perpendicular to the X axis
        if (thisX1 == thisX2) {
            m2 = this.slope(otherX1, otherY1, otherX2, otherY2);
            b2 = this.b(otherX1, otherY1, otherX2, otherY2);

            /*
             * other: y = m2 * x + b2; newX [intersection]: y(thisX1) = m2 * thisX1 + b2
             */
            newX = thisX1;
            newY = m2 * thisX1 + b2;
        } else if (otherX1 == otherX2) {
            // the other line is perpendicular to the X axis
            m1 = this.slope(thisX1, thisY1, thisX2, thisY2);
            b1 = this.b(thisX1, thisY1, thisX2, thisY2);
            newX = otherX1;
            newY = m1 * otherX1 + b1;
        } else {

            // both lines are not perpendicular to the X axis
            m1 = this.slope(thisX1, thisY1, thisX2, thisY2);
            m2 = this.slope(otherX1, otherY1, otherX2, otherY2);
            b1 = this.b(thisX1, thisY1, thisX2, thisY2);
            b2 = this.b(otherX1, otherY1, otherX2, otherY2);

            // same line or parallel. No intersection (not regarding intersection at the
            // edge)
            if (m1 == m2) {
                return null;
            }

            newX = this.intersectionX(m1, b1, m2, b2);
            newY = m1 * newX + b1;
        }
        boolean inXRange1 = this.between(newX, thisX1, thisX2);
        boolean inYRange1 = this.between(newY, thisY1, thisY2);
        boolean inXRange2 = this.between(newX, otherX1, otherX2);
        boolean inYRange2 = this.between(newY, otherY1, otherY2);
        // if the intersection point in between both start-end points, there is one
        if (inXRange1 && inYRange1 && inXRange2 && inYRange2) {
            intersection = new Point(newX, newY);
        }
        return intersection;
    }

    /**
     * Finds the slope of a given line.
     * @param x1 start's x
     * @param y1 start's y
     * @param x2 end's x
     * @param y2 end's y
     * @return the slope of the Line that goes
     * through (x1, y1) and (x2, y2).
     */
    public double slope(double x1, double y1, double x2, double y2) {
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * Finds the 'b' in y = m * x + b of the given Line.
     * @param x1 start's x
     * @param y1 start's y
     * @param x2 end's x
     * @param y2 end's y
     * @return the 'b' of the Line that goes
     * through (x1, y1) and (x2, y2).
     */
    public double b(double x1, double y1, double x2, double y2) {
        /*
         * y = m * x + b y1 = m * x1 + b y1 - m * x1 = b
         */
        double m = slope(x1, y1, x2, y2);
        return y1 - m * x1;
    }

    /**
     * Finds the 2 line's intersection point's X coordinate.
     * @param m1 the first line's slope
     * @param b1 the first line's 'b'
     * @param m2 the second line's slope
     * @param b2 the second line's 'b'
     * @return the X coordinates of the intersection between the lines.
     */
    public double intersectionX(double m1, double b1, double m2, double b2) {
        /*
         * y = m1 * x + b1 y = m2 * x + b2
         * intersection:
         * m1 * x + b1 = m2 * x + b2
         * (m1 - m2) * x = b2 - b1
         * x = (b2 - b1) / (m1 - m2)
         */
        return (b2 - b1) / (m1 - m2);
    }

    /**
     * Checks if 'a' is between 'b' and 'c'.
     * @param a first number to check.
     * @param b second number to check.
     * @param c third number to check.
     * @return true if a is between b and c. False otherwise.
     */
    public boolean between(double a, double b, double c) {
        // returns true if a is between b and c
        return (b >= a && a >= c) || (c >= a && a >= b);
    }

    /**
     * Checks if this line is the same as another given line.
     * @param other the other line.
     * @return True if both lines are the same Line. False otherwise.
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start()) && this.end.equals(other.end());
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect the rectangle to return the closest intersection with
     * @return the closest intersection to this line's start with 'rect'.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersections = rect.intersectionPoints(this);
        Point closest = null;
        double minDistance = this.length();
        for (Point inter : intersections) {

            // For each intersection with the line, check if it's closer to the start
            // of the line than the others.
            if (this.start.distance(inter) <= minDistance) {
                minDistance = this.start.distance(inter);
                closest = inter;
            }
        }
        return closest;
    }
}