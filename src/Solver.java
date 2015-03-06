import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Сергей on 14.12.13.
 */
public class Solver {

    private Queue<Board> queue;
    private boolean solvable;
    private int moves;

    public Solver(Board initial) {

        Comparator<Board> comparator = new Comparator<Board>() {
            @Override
            public int compare(Board Board, Board Board2) {
                return Board.hamming() + moves > Board2.hamming() + moves ? 1 : -1;
                //return Board.manhattan() > Board2.manhattan() ? 1 : -1;
            }
        };

        MinPQ<Board> minPQ = new MinPQ<Board>(comparator);
        MinPQ<Board> minPQT = new MinPQ<Board>(comparator);

        queue = new Queue<Board>();

        minPQ.insert(initial);
        minPQT.insert(initial.twin());

        Board Board;
        Board BoardT;
        Iterator<Board> iterator;
        Iterator<Board> iteratorT;

        for (;;) {
            Board = minPQ.delMin();
            BoardT = minPQT.delMin();

            queue.enqueue(Board);

            System.out.println(Board);
            System.out.println(BoardT);

            if (Board.isGoal()){
                solvable = true;
                break;
            }

            if (BoardT.isGoal()) {
                break;
            }

            iterator = Board.neighbors().iterator();
            moves++;

            while (iterator.hasNext()) {
                minPQ.insert(iterator.next());
            }

            iteratorT = BoardT.neighbors().iterator();
            while (iteratorT.hasNext()) {
                minPQT.insert(iteratorT.next());
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return queue;
    }


    public static void main(String[] args) {
        //int[][] block = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] block = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};

        Solver solver = new Solver(new Board(block));
        System.out.println(solver.moves());


    }
}
