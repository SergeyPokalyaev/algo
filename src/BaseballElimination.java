import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergej.Pokalyaev on 24.04.2015.
 */
public class BaseballElimination {

    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private int numberOfTeams;
    private String[] teamNames;

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

                String[] elms = line.trim().split("\\s+");
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
        validateInputParamters(team);
        return w[Arrays.asList(teamNames).indexOf(team)];
    }

    public int losses(String team) {
        validateInputParamters(team);
        return l[Arrays.asList(teamNames).indexOf(team)];
    }

    public int remaining(String team) {
        validateInputParamters(team);
        return r[Arrays.asList(teamNames).indexOf(team)];
    }

    public int against(String team1, String team2) {
        validateInputParamters(team2);
        validateInputParamters(team1);

        return g[Arrays.asList(teamNames).indexOf(team1)][Arrays.asList(teamNames).indexOf(team2)];
    }

    public boolean isEliminated(String team) {
        validateInputParamters(team);

        FlowNetwork flowNetwork = createFlowNetwork(team);
        new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        for (FlowEdge e : flowNetwork.adj(0)) {
            if (e.flow() != e.capacity()) {
                return true;
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        validateInputParamters(team);

        List<String> teams = new ArrayList<String>();
        FlowNetwork flowNetwork = createFlowNetwork(team);

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        int startPositionSecondColumn = flowNetwork.V() - numberOfTeams;

        int teamPosition = Arrays.asList(teamNames).indexOf(team);
        for (int i = 0; i < numberOfTeams - 1; i++) {
            if (fordFulkerson.inCut(startPositionSecondColumn + (i == teamPosition ? numberOfTeams - 1 : i))) {
                teams.add(teamNames[(i == teamPosition ? numberOfTeams - 1 : i)]);
            }
        }
        return teams.isEmpty() ? null : teams;
    }

    private FlowNetwork createFlowNetwork(String team) {
        validateInputParamters(team);

        int numberOfVertices = 1 + numberOfTeams + ((numberOfTeams - 1) / 2 ) * (numberOfTeams - 2) + (numberOfTeams - 1) % 2;
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices);

        int teamPosition = Arrays.asList(teamNames).indexOf(team);
        int startPositionSecondColumn = numberOfVertices - numberOfTeams;
        int ver = 1;

        for (int i = 0; i < numberOfTeams - 2; i++) {
            for (int j = i + 1; j <  numberOfTeams - 1; j++) {
                FlowEdge flowEdge = new FlowEdge(0, ver, g[i == teamPosition ? numberOfTeams - 1 : i][j == teamPosition ? numberOfTeams - 1 : j]);
                flowNetwork.addEdge(flowEdge);

                flowEdge = new FlowEdge(ver, startPositionSecondColumn + (i == teamPosition ? numberOfTeams - 1 : i), Integer.MAX_VALUE);
                flowNetwork.addEdge(flowEdge);

                flowEdge = new FlowEdge(ver, startPositionSecondColumn + (j == teamPosition ? numberOfTeams - 1 : j), Integer.MAX_VALUE);
                flowNetwork.addEdge(flowEdge);
                ver++;
            }
        }

        for (int i = 0; i < numberOfTeams - 1; i++) {
            int edgeCapacity = w[teamPosition] + r[teamPosition] - w[(i == teamPosition ? numberOfTeams - 1 : i)];
            if (edgeCapacity > 0) {
                FlowEdge flowEdge = new FlowEdge(startPositionSecondColumn + i, numberOfVertices - 1, edgeCapacity);
                flowNetwork.addEdge(flowEdge);
            }
        }
        return flowNetwork;
    }
    
    private void validateInputParamters(String team) {
        if (!Arrays.asList(teamNames).contains(team)) {
            throw new java.lang.IllegalArgumentException("No such team!");
        }
    }



    public static void main(String[] args) {

        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        /*
        BaseballElimination baseballElimination = new BaseballElimination(args[0]);
        System.out.println(baseballElimination.isEliminated("New_York"));
        System.out.println(baseballElimination.isEliminated("Baltimore"));
        System.out.println(baseballElimination.isEliminated("Boston"));
        System.out.println(baseballElimination.isEliminated("Toronto"));
        System.out.println(baseballElimination.isEliminated("Detroit"));


        System.out.println();

        for (String teamName : baseballElimination.certificateOfElimination("Detroit")) {
            System.out.println(teamName);
        }
        */
    }
}
