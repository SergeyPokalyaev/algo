import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Sergej.Pokalyaev on 24.04.2015.
 */
public class BaseballElimination {

    int[] w;
    int[] l;
    int[] r;
    int[][] g;
    int teamNumbers;
    String[] teamNames;

    public BaseballElimination(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        //List<String> lines = new ArrayList<String>();
        line = reader.readLine();
        teamNumbers = Integer.parseInt(line);

        w = new int[teamNumbers];
        l = new int[teamNumbers];
        r = new int[teamNumbers];
        g = new int[teamNumbers][teamNumbers];
        teamNames = new String[teamNumbers];

        while ((line = reader.readLine()) != null) {

            String[] elms = line.split(" ");
            elms[]


        }



    }





}
