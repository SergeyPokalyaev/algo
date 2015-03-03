import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Сергей on 09.12.13.
 */
public class Brute {

    public static void main(String[] args) throws IOException {

        //String fileName = "c:\\temp\\text8.txt";
        String fileName = "c:\\temp\\text6.txt";

        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName));
        BufferedReader buff = new BufferedReader(isr);

        int size = Integer.parseInt(buff.readLine());
        Point[] points = new Point[size];

        for (int i = 0; i < size;i++) {
            String[] pointStr = buff.readLine().trim().split("\\s+");
            points[i] = new Point(Integer.parseInt(pointStr[0]), Integer.parseInt(pointStr[1]));
            //System.out.println(points[i]);
        }

        for (int i = 0; i< size; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    for (int l = k + 1; l< size; l++) {
                        if (points[i].SLOPE_ORDER.compare(points[j], points[k]) == 0
                            && points[i].SLOPE_ORDER.compare(points[k], points[l]) == 0) {
                            System.out.println(points[i] + " -> " + points[j] + " -> " + points[k] + " -> " + points[l]);
                        }
                    }
                }
            }
        }


        //System.out.println(Arrays.asList(points));


    }
}
