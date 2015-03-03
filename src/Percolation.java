/**
 * Created by Сергей on 08.12.13.
 */
public class Percolation {

    private final int[] matrix;
    private final int[] size;
    private final int N;

    public Percolation(int N) {
        this.N = N;
        this.matrix = new int[N * N + 2];
        for (int i = 0; i < N * N; i++) {
            matrix[i] = -1;
        }
        matrix[N * N] = N * N;
        matrix[N * N + 1] = N * N + 1;
        size = new int[N * N + 2];
        for (int i = 0; i < N * N; i++) {
            size[i] = 1;
        }
    }

    public boolean isOpen(int row, int column) {
        if (row > N || column > N || row <= 0 || column <= 0)
            throw new IndexOutOfBoundsException();
        row--;
        column--;
        return isOpenPr(row, column);
    }

    private boolean isOpenPr(int row, int column) {
        return matrix[row * N + column] == -1 ? false : true;
    }

    public boolean isFull(int row, int column) {
        if (row > N || column > N || row <= 0 || column <= 0)
            throw new IndexOutOfBoundsException();
        row--;
        column--;
        if (!isOpenPr(row, column)) {
            return false;
        }
        return isFullPr(row, column);
    }

    private boolean isFullPr(int row, int column) {
        return find(row, column) == find(N, 0);
    }

    public void open(int row, int column) {
        if (row > N || column > N || row <= 0 || column <= 0)
            throw new IndexOutOfBoundsException();
        row--;
        column--;

        if (matrix[row * N + column] != -1)
            return;
        matrix[row * N + column] = row * N + column;

        if (row == 0) {
            unionPr(row, column, N, 0);
            if (isOpenPr(row + 1, column))
                unionPr(row, column, row + 1, column);
        } else if (row == N - 1) {
            unionPr(N, 1, row, column);
            if (isOpenPr(row - 1, column))
                unionPr(row, column, row - 1, column);
        } else {
            if (isOpenPr(row - 1, column))
                unionPr(row, column, row - 1, column);
            if (isOpenPr(row + 1, column))
                unionPr(row, column, row + 1, column);
        }

        if (column == 0) {
            if (isOpenPr(row, column + 1))
                unionPr(row, column, row, column + 1);
        } else if (column == N - 1) {
            if (isOpenPr(row, column - 1))
                unionPr(row, column, row, column - 1);
        } else {
            if (isOpenPr(row, column - 1))
                unionPr(row, column, row, column - 1);
            if (isOpenPr(row, column + 1))
                unionPr(row, column, row, column + 1);
        }
    }

    public boolean percolates() {
        return  find(N, 0) == find(N, 1);
    }

    private int find(int row, int column) {

        if (matrix[row * N + column] == row * N + column) {
            return row * N + column;
        } else {
            return find (matrix[row * N + column] / N, matrix[row * N + column] % N);
        }
    }

    private void unionPr(int row1, int column1, int row2, int column2) {
        int indL = find(row1, column1);
        int indR = find(row2, column2);

        if (indL == indR) {
            return;
        }

        if (size[indL] < size[indR]) {
            matrix[indL] = indR;
            size[indL] += size[indR];
        } else {
            matrix[indR] = indL;
            size[indR] += size[indL];
        }
    }

    public static void main(String[] args) {

        Percolation perc = new Percolation(5);

        perc.isFull(1,1);



    }



}
