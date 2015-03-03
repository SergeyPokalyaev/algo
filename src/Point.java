import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: spokalyaev
 * Date: 09.12.13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class Point {

    private final int x, y;
    private Point self;

    public Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {

    @Override
    public int compare(Point p1, Point p2) {
            if (self.slopeTo(p1) < self.slopeTo(p2)) {
                return -1;
            } else if (self.slopeTo(p1) == self.slopeTo(p2)) {
                return 0;
            } else {
                return 1;
            }
        }
    };


    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
        self = this;
    }

    public void draw() {
        StdDraw.point(x,y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y || this.y == that.y && this.x < that.x ) {
            return -1;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else  if (this.y == that.y) {
            return 0;
        } else {
            return (double)(that.y - this.y) / (double)(that.x - this.x);
        }
    }
}