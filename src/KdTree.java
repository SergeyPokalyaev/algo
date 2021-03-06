

/**
 * Created by Сергей on 28.01.14.
 */
public class KdTree {

    private Node rootNode;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {

        if (rootNode == null) {
            size++;
            rootNode = new Node(p, Boolean.TRUE, null);
        } else {

            Handler handler = new Handler() {
                @Override
                public Node handle(Point2D point, boolean even, Node parent) {

                    if (point.equals(parent.point)) {
                        return null;
                    } else {
                        size++;
                        return new Node(point, even, parent);
                    }
                }

                //Mock method to prevent make syntactic sugar
                @Override
                public Node getNearestNode() {
                    return null;
                }
            };

            rootNode.insert(p, rootNode, handler);
        }

    }

    public boolean contains(Point2D point) {
        return deep(rootNode, point);
    }

    private static boolean deep(Node node, Point2D point) {

        if (node == null) {
            return false;
        }

        if (point.equals(node.point)) {
            return true;
        }

        if (node.even) {
            if (node.point.x() > point.x()) {
                return deep(node.leftNode, point);
            } else {
                return deep(node.rightNode, point);
            }
        } else {
            if (node.point.y() > point.y()) {
                return deep(node.leftNode, point);
            } else {
                return deep(node.rightNode, point);
            }
        }
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {

        SET<Point2D> pointSet = new SET<Point2D>();
        deep(rootNode, pointSet, rect);
        return pointSet;
    }

    public Point2D nearest(Point2D p) {

        Handler handler = new Handler() {

            private Node nearestNode;

            @Override
            public Node handle(Point2D point, boolean even, Node node) {
                nearestNode = length(point, node.point) > length(point, node.parentNode.point) ? node.parentNode : node;
                    return  null;
            }

            @Override
            public Node getNearestNode() {
                return nearestNode;
            }

            private double length(Point2D a, Point2D b) {
                return a.distanceTo(b);
            }
        };

        rootNode.nearestNode(p, rootNode, handler);

        Holder lengthHolder = new Holder(handler.getNearestNode().point.distanceTo(p), handler.getNearestNode());
        deep(rootNode, p, lengthHolder);

        return lengthHolder.getNearestNode().point;
    }

    private class Holder {

        private Double value;
        private Node nearestNode;

        public Holder(Double value, Node nearestNode) {
            this.value = value;
            this.nearestNode = nearestNode;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Node getNearestNode() {
            return nearestNode;
        }

        public void setNearestNode(Node nearestNode) {
            this.nearestNode = nearestNode;
        }
    }


    private static void deep(Node node, Point2D point, Holder holder) {

        if (node == null) {
            return;
        }

        if (node.point.distanceTo(point) < holder.getValue()) {
            holder.setValue(node.point.distanceTo(point));
            holder.setNearestNode(node);
        }

        if (node.even) {

            double delta = node.point.x() - point.x();

            if (delta > 0 && delta > holder.getValue()) {
                deep(node.leftNode, point, holder);
            } else if (delta < 0 && -delta > holder.getValue()) {
                deep(node.rightNode, point, holder);
            } else {
                deep(node.rightNode, point, holder);
                deep(node.leftNode, point, holder);
            }

        } else {

            double delta = node.point.y() - point.y();

            if (delta > 0 && delta > holder.getValue()) {
                deep(node.leftNode, point, holder);
            } else if (delta < 0 && -delta > holder.getValue()) {
                deep(node.rightNode, point, holder);
            } else {
                deep(node.rightNode, point, holder);
                deep(node.leftNode, point, holder);
            }
        }
    }

    private static void deep(Node node, SET<Point2D> pointSet, RectHV rect) {

        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            pointSet.add(node.point);
        }

        if (node.even) {

            if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax()) {
                deep(node.leftNode, pointSet, rect);
                deep(node.rightNode, pointSet, rect);
            } else if (node.point.x() >= rect.xmax()) {
                deep(node.leftNode, pointSet, rect);
            } else if (node.point.x() <= rect.xmin()) {
                deep(node.rightNode, pointSet, rect);
            }

        } else {

            if (node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax()) {
                deep(node.leftNode, pointSet, rect);
                deep(node.rightNode, pointSet, rect);
            } else if (node.point.y() >= rect.ymax()) {
                deep(node.leftNode, pointSet, rect);
            } else if (node.point.y() <= rect.ymin()) {
                deep(node.rightNode, pointSet, rect);
            }
        }
    }



    private class Node {

        private Node leftNode;
        private Node rightNode;
        private Node parentNode;
        private Point2D point;
        private boolean even;

        public Node(Point2D point, boolean even, Node parent) {
            this.parentNode = parent;
            this.point = point;
            this.even = even;
        }

        private void insert(Point2D point, Node node, Handler handler) {
            findPositionHandle(point, handler, node);
        }

        private void nearestNode(Point2D point, Node node, Handler handler) {
            findPositionHandle(point, handler, node);

        }

        private void findPositionHandle(Point2D point, Handler handler, Node node) {

            if (node.even) {
                if (point.x() < node.point.x()) {
                    if (node.leftNode == null) {

                        node.leftNode = handler.handle(point, !node.even, node);
                        //leftNode = new Node(point, !even, this);

                    } else {

                        node.leftNode.insert(point, node.leftNode, handler);

                    }
                } else {
                    if (node.rightNode == null) {

                        node.rightNode = handler.handle(point, !node.even, node);
                        //rightNode = new Node(point, !even, this);

                    } else {
                        node.rightNode.insert(point, node.rightNode, handler);
                    }
                }
            } else {
                if (point.y() < node.point.y()) {
                    if (node.leftNode == null) {

                        node.leftNode = handler.handle(point, !node.even, node);
                        //leftNode = new Node(point, !even, this);

                    } else {
                        node.leftNode.insert(point, node.leftNode, handler);
                    }
                } else {
                    if (node.rightNode == null) {

                        node.rightNode = handler.handle(point, !node.even, node);
                        //rightNode = new Node(point, !even, this);

                    } else {
                        node.rightNode.insert(point, node.rightNode, handler);
                    }
                }
            }
        }
    }

    private interface Handler {
        Node handle(Point2D point, boolean even, Node parent);
        Node getNearestNode();
    }

    public static void main(String[] args) {

        KdTree tree = new KdTree();

        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.7, 0.2));

        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        //tree.insert(new Point2D(0.9, 0.6));
        tree.insert(new Point2D(0.8, 0.9));
        tree.insert(new Point2D(0.0, 0.1));
        System.out.println();

        Iterable<Point2D> it = tree.range(new RectHV(0, 0, 0.0, 0.1));

        System.out.println(it);

        tree.nearest(new Point2D(0.6, 0.9));

        System.out.println("Hello !");

    }

}



