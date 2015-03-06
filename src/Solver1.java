import java.util.*;

/**
 * Created by Сергей on 14.12.13.
 */
public class Solver1 {

    private Queue<Board> queue;
    private boolean solvable;
    private int moves;

    public Solver1(Board initial) {

        Comparator<Board> comparator = new Comparator<Board>() {
            @Override
            public int compare(Board board, Board board2) {

                if (board.hamming() + moves > board2.hamming() + moves) {
                    return 1;
                } else if (board.hamming() + moves < board2.hamming() + moves) {
                    return -1;
                } else {
                    return 0;
                }

                //return board.hamming() + moves > board2.hamming() + moves ? 1 : -1;
                //return board.manhattan() + moves > board2.manhattan() + moves ? 1 : -1;
            }
        };

        MinPQ<Board> minPQ = new MinPQ<Board>(comparator);
        MinPQ<Board> minPQT = new MinPQ<Board>(comparator);

        queue = new Queue<Board>();

        minPQ.insert(initial);
        minPQT.insert(initial.twin());

        Board board;
        Board boardT;
        Iterator<Board> iterator;
        Iterator<Board> iteratorT;

        for (;;) {
            board = minPQ.delMin();
            boardT = minPQT.delMin();

            queue.enqueue(board);

            System.out.println(board);
            System.out.println(boardT);

            if (board.isGoal()){
                solvable = true;
                break;
            }

            if (boardT.isGoal()) {
                break;
            }

            moves++;
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
        return moves;
    }

    public Iterable<Board> solution() {
        return queue;
    }



    public static void main(String[] args) {
        //int[][] block = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        //int[][] block = {{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};
        int[][] block = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};

        Solver1 Solver1 = new Solver1(new Board(block));
        System.out.println(Solver1.moves());
        for (Board b : Solver1.solution()) {
            System.out.println(b);
        }

        System.out.println(Solver1.moves);


    }
}
