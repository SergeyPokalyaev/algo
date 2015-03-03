import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by Sergej.Pokalyaev on 03.03.2015.
 */
public class Board {

    private int[][] blocks;
    private int moveTimes;

    public Board(int[][] blocks) {
        this.blocks = blocks;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int hammingWeight = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (i == blocks.length - 1 && j == blocks.length - 1) {
                    if (blocks[i][j] != 0) {
                        hammingWeight++;
                    }
                } else if (blocks[i][j] != i * blocks.length + j + 1) {
                    hammingWeight++;
                }
            }
        }
        return hammingWeight + moveTimes;
    }

    public int manhattan() {
        int manhattanWeight = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                manhattanWeight += manhattanDistance(i, j);
            }
        }
        return manhattanWeight + moveTimes;
    }

    private int manhattanDistance(int i, int j) {

        int iNorm;
        int jNorm;

        if (blocks[i][j] == 0) {
            iNorm = blocks.length - 1;
            jNorm = iNorm;
        } else {
            iNorm = (blocks[i][j] - 1) / blocks.length;
            jNorm = (blocks[i][j] - 1) % blocks.length;
        }

        return Math.abs(iNorm - i) + Math.abs(jNorm - j);
    }


    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != i * blocks.length + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        int[][] blocksExt = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                blocksExt[i][j] = blocks[i][j];
            }
        }

        if (blocksExt[0][0] == 0 || blocksExt[0][1] == 0) {
            int matrixValue = blocksExt[1][1];
            blocksExt[1][1] = blocksExt[1][0];
            blocksExt[1][0] = matrixValue;
        } else {
            int matrixValue = blocksExt[0][1];
            blocksExt[0][1] = blocksExt[0][0];
            blocksExt[0][0] = matrixValue;
        }

        return new Board(blocksExt);
    }

    public boolean equals(Object that) {

        if (!(that instanceof Board) && ((Board)that).blocks.length != this.blocks.length) {
            return false;
        }

        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks.length; j++) {
                if (this.blocks[i][j] != ((Board)that).blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {

        Bag<Board> boardBag = new Bag<Board>();

        int zeroPositionI;
        int zeroPositionJ;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    zeroPositionI = i;
                    zeroPositionJ = j;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            if () {

            }

        }

        return boardBag;
    }

    private Board swapElementsInBlock() {



        return null;

    }




    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(blocks.length + "\n");

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                sb.append(blocks[i][j] + " ");
            }
            sb.append("\n");
        }

        sb.append(moveTimes + "\n");
        return sb.toString();
    }

    public static void main(String[] args) {

        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(blocks);

        Board b1 = new Board(blocks);

        System.out.println(b.equals(b1));

        System.out.println(b.hamming());
        System.out.println(b.manhattan());


    }

}
