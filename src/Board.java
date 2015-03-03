import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;
import java.util.Stack;

/**
 * Created by Сергей on 14.12.13.
 */
public class Board {

    private int[][] blocks;
    private int moves;
    private Board parentBoard;

    public Board(int[][] blocks) {
        this.blocks = blocks;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {

        int result = 0;

        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                if (column == blocks.length - 1 && row == blocks.length - 1) {
                    if (blocks[row][column] != 0) {
                        result++;
                    }
                } else if (blocks[row][column] != (column + 1 + row * blocks.length)) {
                    if (blocks[row][column] != 0) {
                        result++;
                    }
                }
            }
        }

        return result + moves;
    }

    public int manhattan() {

        int result = 0;
        for (int row = 0; row < blocks.length; row++ ) {
            for (int column = 0; column < blocks.length; column++ ) {
                result += getManDist(row, column);
            }
        }

        return result + moves;
    }


    private int getManDist(int row, int column) {

        int value = blocks[row][column];
        value--;

        int row1, column1;

        if (value == -1) {
            return 0;
        } else {
            row1 = value / blocks.length;
            column1 = value % blocks.length;
        }

        return Math.abs(row - row1) + Math.abs(column - column1);
    }

    public boolean isGoal() {

        int result = 0;
        for (int row = 0; row < blocks.length; row++ ) {
            for (int column = 0; column < blocks.length; column++ ) {
                result += getManDist(row, column);
            }
        }

        return result == 0 ? true : false;
    }


    public Board twin() {
        int[][] blocksExt = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                blocksExt[row][column] = blocks[row][column];
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


    public boolean equals(Object y) {

        if (!(y instanceof Board)) {
            return false;
        }

        int[][] wrapperBlocks = ((Board)y).blocks;

        if (wrapperBlocks.length != blocks.length) {
            return false;
        }

        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                if (wrapperBlocks[row][column] != blocks[row][column]) {
                    return false;
                }
            }
        }

        return true;
    }


    public Iterable<Board> neighbors() {

        Iterable<Board> iterable = new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {

                Iterator<Board> iterator = new Iterator<Board>() {

                    int size = 0;
                    int row = 0;
                    int column = 0;

                    Board[] boards;

                    int place;

                    {
                        for (int i = 0; i < blocks.length; i++) {
                            for (int j = 0; j < blocks.length; j++) {
                                if (blocks[i][j] == 0) {
                                    row = i;
                                    column = j;
                                }
                            }
                        }

                        Stack<Board> stack = new Stack<Board>();

                        for (int i = 0; i < 4; i++) {
                            switch (i) {
                                case 0:
                                    {
                                        Board localBoard = getBoard(row + 1, column, row, column);
                                        if (localBoard != null){
                                            stack.push(localBoard);
                                        }
                                    }
                                    break;
                                case 1:
                                    {
                                        Board localBoard = getBoard(row - 1, column, row, column);
                                        if (localBoard != null){
                                            stack.push(localBoard);
                                        }
                                    }
                                    break;
                                case 2:
                                    {
                                        Board localBoard = getBoard(row, column + 1, row, column);
                                        if (localBoard != null){
                                            stack.push(localBoard);
                                        }
                                    }
                                    break;
                                case 3:
                                    {
                                        Board localBoard = getBoard(row, column - 1, row, column);
                                        if (localBoard != null){
                                            stack.push(localBoard);
                                        }
                                    }
                                    break;
                            }
                        }

                        boards = new Board[stack.size()];
                        size = stack.size();

                        for (int i = 0, j = stack.size(); i < j; i++) {
                            boards[i] = stack.pop();
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return place != size;
                    }

                    @Override
                    public Board next() {
                        return boards[place++];
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };

                return iterator;
            }
        };

        return iterable;
    }

    private Board getBoard(int rowZero, int columnZero, int rowX, int columnX) {

        if (rowZero == -1 || columnZero == -1 || rowZero == blocks.length || columnZero == blocks.length) {
            return null;
        }

        int[][] blocksLocal = new int[blocks.length][blocks.length];

        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                blocksLocal[row][column] = blocks[row][column];
            }
        }

        blocksLocal[rowX][columnX] = blocksLocal[rowZero][columnZero];
        blocksLocal[rowZero][columnZero] = 0;
        Board board = new Board(blocksLocal);

        if (board.equals(this.parentBoard)) {
            return null;
        }

        board.moves = this.moves + 1;
        board.parentBoard = this;
        return board;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(blocks.length + "\n");

        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                sb.append(blocks[row][column] + " ");
            }
            sb.append("\n");
        }

        sb.append(moves + "\n");

        return sb.toString();
    }

}