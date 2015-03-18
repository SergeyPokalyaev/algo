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


        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQT = new MinPQ<SearchNode>();

        queue = new Queue<Board>();

        minPQ.insert(new SearchNode(initial, moves));
        minPQT.insert(new SearchNode(initial.twin(), moves));

        while (true) {
            Board board = minPQ.delMin().board;
            Board boardT = minPQT.delMin().board;

            minPQ = new MinPQ<SearchNode>();
            minPQT = new MinPQ<SearchNode>();

            queue.enqueue(board);

            if (board.isGoal()){
                solvable = true;
                break;
            }
            if (boardT.isGoal()){
                break;
            }

            Iterator<Board> iterator = board.neighbors().iterator();
            Iterator<Board> iteratorT = boardT.neighbors().iterator();
            moves++;

            while (iterator.hasNext()) {
                minPQ.insert(new SearchNode(iterator.next(), moves));
            }

            while (iteratorT.hasNext()) {
                minPQT.insert(new SearchNode(iteratorT.next(), moves));
            }
        }

    }

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private int moves;
        private int priority;

        public SearchNode(Board board, int moves) {
            this.board = board;
            this.moves = moves;
            priority = this.moves + this.board.manhattan();
        }


        @Override
        public int compareTo(SearchNode sn) {

            if (this.priority > sn.priority) {
                return 1;
            } else if (this.priority < sn.priority) {
                return -1;
            } else {
                return 0;
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
    /*
    public static void main(String[] args) {
        // create initial board from file

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    */

    public static void main(String[] args) {
        //int[][] blocks = {{5, 7, 4}, {3, 0, 8}, {1, 6, 2}};
        //int[][] blocks = {{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};
        //int[][] blocks = {{1,  2,  3,  4,  5}, {12,  6,  8,  9, 10 }, {0,  7, 13, 19, 14}, {11, 16, 17, 18, 15 }, {21, 22, 23, 24, 20 }};
        int[][] blocks = {{6,  7,  5, 10, 15, }, {3,  2,  8,  0,  4 }, {1, 12, 20, 13,  9 }, {11, 16, 19, 14, 24  }, {21, 17, 22, 18, 23}};
        //int[][] blocks = {{2, 0}, {1, 3}};

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
