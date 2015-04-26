import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Sergej.Pokalyaev on 24.04.2015.
 */
public class BaseballElimination {

    int[] w;
    int[] l;
    int[] r;
    int[][] g;
    int numberOfTeams;
    String[] teamNames;

    private FlowNetwork flowNetwork;

    public BaseballElimination(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            line = reader.readLine();
            numberOfTeams = Integer.parseInt(line);

            w = new int[numberOfTeams];
            l = new int[numberOfTeams];
            r = new int[numberOfTeams];
            g = new int[numberOfTeams][numberOfTeams];
            teamNames = new String[numberOfTeams];

            for (int i = 0; i < numberOfTeams; i++) {

                line = reader.readLine();

                String[] elms = line.split("\\s+");
                teamNames[i] = elms[0];
                w[i] = Integer.parseInt(elms[1]);
                l[i] = Integer.parseInt(elms[2]);
                r[i] = Integer.parseInt(elms[3]);

                for (int j = 0; j < numberOfTeams; j++) {
                    g[i][j] = Integer.parseInt(elms[j + 4]);
                }
            }
        } catch (IOException ex) {
        }
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        return Arrays.asList(teamNames);
    }

    public int wins(String team) {
        return r[Arrays.asList(w).indexOf(team)];
    }

    public int losses(String team) {
        return r[Arrays.asList(l).indexOf(team)];
    }

    public int remaing(String team) {
        return r[Arrays.asList(teamNames).indexOf(team)];
    }

    public int against(String team1, String team2) {
        return g[Arrays.asList(teamNames).indexOf(team1)][Arrays.asList(teamNames).indexOf(team2)];
    }

    public boolean isEliminated(String team) {
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        return null;

    }

    private void initFlowNet() {

        int numberOfVertices = 1 + numberOfTeams + ((numberOfTeams - 1) / 2 ) * (numberOfTeams - 2) + (numberOfTeams - 1) % 2;
        flowNetwork = new FlowNetwork(numberOfVertices);

    }


    public static void main(String[] args) {

        BaseballElimination baseballElimination = new BaseballElimination(args[0]);
        //System.out.println(baseballElimination.remaing("Atlanta"));

        baseballElimination.initFlowNet();



        //flowNetwork = new FlowNetwork();



    }



}
