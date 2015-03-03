/**
 * Created by Сергей on 27.01.14.
 */
public class PointSET {

    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D point : pointSet) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {

        SET<Point2D> points = new SET<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        Point2D result = pointSet.max();

        for (Point2D point : pointSet) {

            if (p.distanceTo(result) > p.distanceTo(point)) {
                result = point;
            }
        }
        return result;
    }





    public static void main(String[] args) {
        System.out.println("Hello world!!!");

        Point2D p1 = new Point2D(2, 2);
        Point2D p2 = new Point2D(0, 0);
        Point2D p3 = new Point2D(-1, -1);
        Point2D p4 = new Point2D(10, 10);
        Point2D p5 = new Point2D(4, 3);
        Point2D p6 = new Point2D(23, 56);

        PointSET pointSET = new PointSET();

        pointSET.insert(p1);
        pointSET.insert(p2);
        pointSET.insert(p3);
        pointSET.insert(p4);
        pointSET.insert(p5);
        pointSET.insert(p6);


        System.out.println(pointSET.contains(new Point2D(23, 56)));

        //pointSET.contains(p1);


        RectHV rectHV = new RectHV(1, 1, 3, 3);


        for (Point2D p : pointSET.range(rectHV)) {
            System.out.println(p);
        }


        System.out.println(pointSET.nearest(new Point2D(0, 0)));

    }


}