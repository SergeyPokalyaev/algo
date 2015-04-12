import java.awt.*;

/**
 * Created by Сергей on 11.04.2015.
 */
public class SeamCarver {

    private Picture picture;
    private double[][] energy;
    private static int MAX_COLOR_VALUE = 255;


    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.energy = calculateEnergy(picture);
    }

    private double[][] calculateEnergy(Picture picture) {
        double[][] energy = new double[picture.height()][picture.width()];
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                if (i == 0 || j ==0 || i == picture.height() - 1 || j == picture.width() - 1) {
                    energy[i][j] = 3 * Math.pow(MAX_COLOR_VALUE, 2);
                } else {
                    Color minusX = picture.get(j - 1, i);
                    Color plusX = picture.get(j + 1, i);
                    Color minusY = picture.get(j, i - 1);
                    Color plusY = picture.get(j, i + 1);
                    energy[i][j] = Math.pow(minusX.getRed() - plusX.getRed(), 2)
                            + Math.pow(minusX.getGreen() - plusX.getGreen(), 2)
                            + Math.pow(minusX.getBlue() - plusX.getBlue(), 2)
                            + Math.pow(minusY.getRed() - plusY.getRed(), 2)
                            + Math.pow(minusY.getGreen() - plusY.getGreen(), 2)
                            + Math.pow(minusY.getBlue() - plusY.getBlue(), 2);
                }
            }
        }
        return energy;
    }

    public static void main(String[] args) {
        Picture pic = new Picture("C:\\temp\\6x5.png");
        SeamCarver SeamCarver = new SeamCarver(pic);
        for (int i = 0; i < SeamCarver.energy.length; i++) {
            for (int j = 0; j < SeamCarver.energy[0].length; j++) {
                System.out.print(SeamCarver.energy[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("--------------------");
        SeamCarver.findHorizontalSeam();
        System.out.println("--------------------");



        /*
        System.out.println("--------------------");
        for (int i : SeamCarver.findVerticalSeam()) {
            System.out.println(i);
        }
        System.out.println("--------------------");
        for (int i : SeamCarver.findHorizontalSeam()) {
            System.out.println(i);
        }
        */
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        return energy[y][x];
    }

    public int[] findHorizontalSeam() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(picture.height() * picture.width() + 2);

        for (int j = 0; j < picture.width() - 1; j++) {
            for (int i = 0; i < picture.height(); i++) {
                DirectedEdge edge = new DirectedEdge(picture.width() * i + j, picture.width() * i + j + 1, energy[i][j + 1]);
                G.addEdge(edge);
                if (i != 0) {
                    edge = new DirectedEdge(picture.width() * i + j, (picture.width() * (i - 1)) + j + 1, energy[i - 1][j + 1]);
                    G.addEdge(edge);
                }
                if (i != picture.height() - 1) {
                    edge = new DirectedEdge(picture.width() * i + j, (picture.width() * (i + 1)) + j + 1, energy[i + 1][j + 1]);
                    G.addEdge(edge);
                }
            }
        }

        for (int i = 0; i < picture.height(); i++) {
            DirectedEdge edge = new DirectedEdge(picture.height() * picture.width(), picture.width() * i, energy[i][0]);
            G.addEdge(edge);
            edge = new DirectedEdge(picture.width() * i + picture.width() - 1, picture.height() * picture.width() + 1, 0);
            G.addEdge(edge);
        }

        //DijkstraSP sp = new DijkstraSP(G, picture.height() * picture.width());
        //AcyclicSP sp = new AcyclicSP(G, picture.height() * picture.width());
        BellmanFordSP sp = new BellmanFordSP(G, picture.height() * picture.width());

        int[] result = new int[picture.width()];
        int i = 0;
        for (DirectedEdge edge : sp.pathTo(picture.height() * picture.width() + 1)) {
            result[i] = edge.to() / picture.width();
            i++;
            if (i == picture.width()) {
                break;
            }
        }
        return result;
    }

    public int[] findVerticalSeam() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(picture.height() * picture.width() + 2);

        for (int i = 0; i < picture.height() - 1; i++) {
            for (int j = 0; j < picture.width(); j++) {
                DirectedEdge edge = new DirectedEdge(picture.width() * i + j, picture.width() * (i + 1) + j, energy[i + 1][j]);
                G.addEdge(edge);
                if (j != 0) {
                    edge = new DirectedEdge(picture.width() * i + j, picture.width() * (i + 1) + j - 1, energy[i + 1][j - 1]);
                    G.addEdge(edge);
                }
                if (j != picture.width() - 1) {
                    edge = new DirectedEdge(picture.width() * i + j, picture.width() * (i + 1) + j + 1, energy[i + 1][j + 1]);
                    G.addEdge(edge);
                }
            }
        }

        for (int j = 0; j < picture.width(); j++) {
            DirectedEdge edge = new DirectedEdge(picture.height() * picture.width(), j, energy[0][j]);
            G.addEdge(edge);
            edge = new DirectedEdge((picture.height() - 1) * picture.width() + j, picture.height() * picture.width() + 1, 0);
            G.addEdge(edge);
        }

        //DijkstraSP sp = new DijkstraSP(G, picture.height() * picture.width());
        //AcyclicSP sp = new AcyclicSP(G, picture.height() * picture.width());
        BellmanFordSP sp = new BellmanFordSP(G, picture.height() * picture.width());

        int[] result = new int[picture.height()];
        int i = 0;
        for (DirectedEdge edge : sp.pathTo(picture.height() * picture.width() + 1)) {
            result[i] = edge.to() % picture.width();
            i++;
            if (i == picture.height()) {
                break;
            }
        }
        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length > picture.width()) {
            throw new RuntimeException();
        }
        Picture newPic = new Picture(picture.width(), picture.height() - 1);
        Color[][] colors = new Color[picture.height() - 1][picture.width()];
        for (int j = 0; j < newPic.width(); j++) {
           int deltaI = 0;
           for (int i = 0; i < newPic.height(); i++) {
              if (seam[j] == i) {
                 deltaI++;
              }
              colors[i][j] = picture.get(j, i + deltaI);
           }
        }
        for (int i = 0; i < newPic.height(); i++) {
           for (int j = 0; j < newPic.width(); j++) {
              newPic.set(j, i, colors[i][j]);
           }
        }
        picture = newPic;
        energy = calculateEnergy(newPic);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length > picture.height()) {
            throw new RuntimeException();
        }
        Picture newPic = new Picture(picture.width() - 1, picture.height());
        Color[][] colors = new Color[picture.height()][picture.width() - 1];
        for (int i = 0; i < newPic.height(); i++) {
            int deltaJ = 0;
            for (int j = 0; j < newPic.width(); j++) {
                if (seam[i] == j) {
                    deltaJ++;
                }
                colors[i][j] = picture.get(j + deltaJ, i);
            }
        }
        for (int i = 0; i < newPic.height(); i++) {
            for (int j = 0; j < newPic.width(); j++) {
                newPic.set(j, i, colors[i][j]);
            }
        }
        picture = newPic;
        energy = calculateEnergy(newPic);
    }
}
