import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Сергей on 14.12.13.
 */
public class Solver {

    private Stack<Board> stack;
    private boolean solvable;

    public Solver(Board initial) {

        Comparator<Board> comparator = new Comparator<Board>() {
            @Override
            public int compare(Board board1, Board board2) {
                return board1.hamming() > board2.hamming() ? 1 : -1;
                //return board1.manhattan() > board2.manhattan() ? 1 : -1;
            }
        };

        MinPQ<Board> minPQ = new MinPQ<Board>(comparator);
        MinPQ<Board> minPQT = new MinPQ<Board>(comparator);

        stack = new Stack<Board>();

        minPQ.insert(initial);
        minPQT.insert(initial.twin());

        Board board;
        Board boardT;
        Iterator<Board> iterator;
        Iterator<Board> iteratorT;

        for (;;) {
            board = minPQ.delMin();
            boardT = minPQT.delMin();

            stack.push(board);

            System.out.println(board);
            System.out.println(boardT);

            if (board.isGoal()){
                solvable = true;
                break;
            }

            if (boardT.isGoal()) {
                break;
            }

            iterator = board.neighbors().iterator();
            while (iterator.hasNext()) {
                minPQ.insert(iterator.next());
            }

            iteratorT = boardT.neighbors().iterator();
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

    public Iterable<Board> solution() {
        return stack;
    }


    public static void main(String[] args) {
        //int[][] block = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] block = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};

        Solver solver = new Solver(new Board(block));
        System.out.println(solver.isSolvable());


    }
}
