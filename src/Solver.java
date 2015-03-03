import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Сергей on 14.12.13.
 */
public class Solver {

    private Stack<Board1> stack;
    private boolean solvable;

    public Solver(Board1 initial) {

        Comparator<Board1> comparator = new Comparator<Board1>() {
            @Override
            public int compare(Board1 board1, Board1 board2) {
                return board1.hamming() > board2.hamming() ? 1 : -1;
                //return board1.manhattan() > board2.manhattan() ? 1 : -1;
            }
        };

        MinPQ<Board1> minPQ = new MinPQ<Board1>(comparator);
        MinPQ<Board1> minPQT = new MinPQ<Board1>(comparator);

        stack = new Stack<Board1>();

        minPQ.insert(initial);
        minPQT.insert(initial.twin());

        Board1 board1;
        Board1 board1T;
        Iterator<Board1> iterator;
        Iterator<Board1> iteratorT;

        for (;;) {
            board1 = minPQ.delMin();
            board1T = minPQT.delMin();

            stack.push(board1);

            System.out.println(board1);
            System.out.println(board1T);

            if (board1.isGoal()){
                solvable = true;
                break;
            }

            if (board1T.isGoal()) {
                break;
            }

            iterator = board1.neighbors().iterator();
            while (iterator.hasNext()) {
                minPQ.insert(iterator.next());
            }

            iteratorT = board1T.neighbors().iterator();
            while (iteratorT.hasNext()) {
                minPQT.insert(iteratorT.next());
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return stack.size();
    }

    public Iterable<Board1> solution() {
        return stack;
    }


    public static void main(String[] args) {
        //int[][] block = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] block = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};

        Solver solver = new Solver(new Board1(block));
        System.out.println(solver.isSolvable());


    }
}
