import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Stack;

/**
 * Created by Сергей on 10.12.13.
 */
public class Fast {

    public static void main(String[] args) throws IOException {

        String fileName = "c:\\temp\\text8.txt";
        //String fileName = "c:\\temp\\t.txt";

        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
        BufferedReader buff = new BufferedReader(isr);

        int size = Integer.parseInt(buff.readLine());
        Point[] points = new Point[size];

        for (int i = 0; i < size;i++) {
            String[] pointStr = buff.readLine().trim().split("\\s+");
            points[i] = new Point(Integer.parseInt(pointStr[0]), Integer.parseInt(pointStr[1]));
        }

        Stack<Double> tang = new Stack<Double>();

        for (int i = 1; i< size; i++) {

            Point[] pointsAux = points.clone();
            Point point = points[0];
            pointsAux[0] = pointsAux[i];
            pointsAux[i] = point;

            Arrays.sort(pointsAux, 1, pointsAux.length, pointsAux[0].SLOPE_ORDER);

            int slopeNum = 2;

            for (int j=2; j <= pointsAux.length; j++) {

                if (j != pointsAux.length && pointsAux[j].slopeTo(pointsAux[0]) == pointsAux[j-1].slopeTo(pointsAux[0]) && !tang.contains(pointsAux[j].slopeTo(pointsAux[0]))) {
                    slopeNum++;
                } else {
                    if (slopeNum > 3) {

                        tang.add(pointsAux[j-1].slopeTo(pointsAux[0]));

                        System.out.print(pointsAux[0]);

                        for (int k = j - slopeNum + 1; k < j; k++) {
                            System.out.print(" -> " + pointsAux[k]);
                        }

                        System.out.println();

                    }
                    slopeNum = 2;
                }

            }

        }

    }
}
