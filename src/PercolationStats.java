public class PercolationStats {

    private double N, T, mean, stddev, confidenceLo, confidenceHigh;

    private double[] results;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.T = T;

        results = new double[T];

        for (int t = 0; t < T; t++) {

            Percolation perc = new Percolation(N);

            double mid = 0;

            do {
                int row = StdRandom.uniform(1, N+1);
                int column = StdRandom.uniform(1, N+1);

                if (perc.isOpen(row, column))
                    continue;

                mid++;
                perc.open(row, column);
                perc.isFull(row, column);


            } while (!perc.percolates());

            results[t] = mid / (N * N);
        }

        for (int i = 0; i < T; i++) {
            mean += results[i];
        }

        mean /= T;

        for (int i = 0; i < T; i++) {
            stddev += (results[i] - mean) * (results[i] - mean);
        }

        stddev /= (T - 1);
        stddev = Math.sqrt(stddev);

        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(T);
        confidenceHigh = mean + (1.96 * stddev) / Math.sqrt(T);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHigh;
    }

    public static void main(String[] args) {
        /*
        if (Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <= 0)
            throw new IllegalArgumentException();

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        */
        PercolationStats percolationStats = new PercolationStats(200, 100);
        System.out.println("mean                    =" + percolationStats.mean());
        System.out.println("stddev                  =" + percolationStats.stddev());
        System.out.println("95% confidence interval =" + percolationStats.confidenceLo() + " " + percolationStats.confidenceHi());



    }
}




