import java.util.Stack;

/**
 *
 */

/**
 * @author adrian
 *
 */
public class Solver {
    Stack<Board> gameTree;
    private int moves;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        gameTree = new Stack<Board>();
        moves = 0;
        solvable = false;
        SearchNode sn;

        pq.insert(new SearchNode(initial, moves, null));
        pq.insert(new SearchNode(initial.twin(), moves, null));
        moves++;
        // System.out.println(sn);

        while (true) {
            SearchNode searchNode = pq.delMin();
            gameTree.add(searchNode.board());
//      System.out.println("--vvv--");
//      System.out.println(searchNode);
//      System.out.println("--^^^--");

            if (searchNode.board().hamming() == 0) {
                moves = searchNode.moves();
                // TODO detect if twin or real node...
                solvable = true;
                break;
            }
            for (Board b : searchNode.board().neighbors()) {
                if(searchNode.parent() == null || !b.equals(searchNode.parent().board())){
                    pq.insert(new SearchNode(b, moves, searchNode));
                }
            }
            moves++;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return gameTree;
    }



    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode parent;

        public SearchNode(Board board, int moves, SearchNode parent) {
            this.board = board;
            this.moves = moves;
            this.parent = parent;
        }

        public int priority() {
            return board.manhattan()*2 + moves;
        }

        public Board board() {
            return this.board;
        }

        public int moves() {
            return this.moves;
        }

        public SearchNode parent() {
            return this.parent;
        }

        @Override
        public int compareTo(SearchNode sn) {
            if (this.priority() > sn.priority())
                return 1;
            else if (this.priority() < sn.priority())
                return -1;
            else
                return 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Move: " + this.moves);
            sb.append("\npriority = " + this.priority());
            sb.append("\nmanhattan = " + this.board.manhattan());
            sb.append("\nhamming = " + this.board.hamming());
            sb.append("\n" + this.board.toString());
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        //int[][] block = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        //int[][] block = {{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};
        int[][] block = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};

        Solver solver = new Solver(new Board(block));
        System.out.println(solver.moves());
        for (Board b : solver.solution()) {
            System.out.println(b);
        }

        System.out.println(solver.moves);


    }
}
