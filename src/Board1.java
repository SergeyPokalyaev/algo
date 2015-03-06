import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Sergej.Pokalyaev on 03.03.2015.
 */
public class Board1 {

    private int[][] blocks;
    private int moveTimes;
    private int parentDirection;

    public Board1(int[][] blocks) {
        this.blocks = copyArray(blocks);
    }

    private Board1(int[][] blocks, int moveTimes, int parentDirection) {
        this.blocks = blocks;
        this.moveTimes = moveTimes;
        this.parentDirection = parentDirection;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int hammingWeight = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                } else if (blocks[i][j] != i * blocks.length + j + 1) {
                    hammingWeight++;
                }
            }
        }
        moveTimes++;
        return hammingWeight;
    }

    public int manhattan() {
        int manhattanWeight = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                } else if (blocks[i][j] != i * blocks.length + j + 1) {
                    manhattanWeight += manhattanDistance(i, j);
                }
            }
        }
        moveTimes++;
        return manhattanWeight;
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
                if (!(i == blocks.length - 1 && j == blocks.length - 1) && (blocks[i][j] != i * blocks.length + j + 1 || blocks[i][j] == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board1 twin() {
        int[][] blocksExt = copyArray(blocks);

        if (blocksExt[0][0] == 0 || blocksExt[0][1] == 0) {
            swapElementsInBlock(blocksExt, 1, 1, 1, 0);
        } else {
            swapElementsInBlock(blocksExt, 0, 1, 0, 0);
        }

        return new Board1(blocksExt);
    }

    public boolean equals(Object that) {

        if (!(that instanceof Board1 && ((Board1) that).blocks.length == this.blocks.length && ((Board1) that).blocks[0].length == this.blocks[0].length)) {
            return false;
        }

        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks.length; j++) {
                if (this.blocks[i][j] != ((Board1) that).blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board1> neighbors() {

        Bag<Board1> Board1Bag = new Bag<Board1>();

        int zeroPositionI = 0;
        int zeroPositionJ = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    zeroPositionI = i;
                    zeroPositionJ = j;
                }
            }
        }

        if (zeroPositionI != 0) {
            if (parentDirection != 2) {
                Board1Bag.add(new Board1(swapElementsInBlock(copyArray(blocks), zeroPositionI - 1, zeroPositionJ, zeroPositionI, zeroPositionJ), moveTimes + 1, 8)); // 8
            }
        }

        if (zeroPositionI != blocks.length - 1) {
            if (parentDirection != 8) {
                Board1Bag.add(new Board1(swapElementsInBlock(copyArray(blocks), zeroPositionI + 1, zeroPositionJ, zeroPositionI, zeroPositionJ), moveTimes + 1, 2)); // 2
            }
        }

        if (zeroPositionJ != 0) {
            if (parentDirection != 6) {
                Board1Bag.add(new Board1(swapElementsInBlock(copyArray(blocks), zeroPositionI, zeroPositionJ - 1, zeroPositionI, zeroPositionJ), moveTimes + 1, 4)); // 4
            }
        }

        if (zeroPositionJ != blocks.length - 1) {
            if (parentDirection != 4) {
                Board1Bag.add(new Board1(swapElementsInBlock(copyArray(blocks), zeroPositionI, zeroPositionJ + 1, zeroPositionI, zeroPositionJ), moveTimes + 1, 6)); // 6
            }
        }






        return Board1Bag;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(blocks.length + "\n");

        for (int i = 0; i < blocks.length; i++) {
            sb.append(" ");
            for (int j = 0; j < blocks.length; j++) {
                sb.append(blocks[i][j] + "  ");
            }
            sb.append("\n");
        }

        //sb.append(moveTimes + "\n");
        return sb.toString();
    }

    public static void main(String[] args) {

        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Board1 b = new Board1(blocks);
        System.out.println(b.isGoal());
        /*
        int[][] blocks = {{1, 2}, {3, 0}};
        Board1 b = new Board1(blocks);
        System.out.println(b.isGoal());
        */
        /*
        int[][] blocks = {{5, 8, 7}, {1, 4, 6}, {3, 0, 2}};
        Board1 b = new Board1(blocks);


        System.out.println(b.hamming());
        System.out.println(b);

        System.out.println(b.equals(b));
        */
        /*
        System.out.println(b.hamming());

        for (Board1 Board1 : b.neighbors()) {
            System.out.println(Board1);
            System.out.println(Board1.manhattan());
        }

        System.out.println("----------------------");


        Iterator<Board1> b1 = b.neighbors().iterator();


        for (Board1 Board1 : b1.next().neighbors()) {
            System.out.println(Board1);
            System.out.println(Board1.manhattan());
        }
        */
    }

    private int[][] swapElementsInBlock(int[][] blocks, int i1, int j1, int i2, int j2) {
        int matrixValue = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = matrixValue;
        return blocks;
    }

    private int[][] copyArray(int[][] blocks) {
        int[][] blocksExt = new int[blocks.length][blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            blocksExt[i] = Arrays.copyOf(blocks[i], blocks.length);
        }
        return blocksExt;
    }

}
