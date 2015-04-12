import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Сергей on 02.04.2015.
 */
public class WordNet {

    private Map<String, Collection<Integer>> container = new HashMap<String, Collection<Integer>>();
    private Map<Integer, Collection<String>> containerB = new HashMap<Integer, Collection<String>>();
    private Digraph G = null;

    public WordNet(String synsets, String hypernyms) {
        int size = 0;
        try {
            BufferedReader in = new BufferedReader
                    (new FileReader(synsets));
            String str;
            while ((str = in.readLine()) != null) {
                String[] parts = str.split(",");
                for (String noun : Arrays.asList(parts[1].split(" "))) {
                    if (container.containsKey(noun)) {
                        container.get(noun).add(Integer.parseInt(parts[0]));
                    } else {
                        Set<Integer> set = new HashSet<Integer>();
                        set.add(Integer.parseInt(parts[0]));
                        container.put(noun, set);
                    }
                }
                containerB.put(Integer.parseInt(parts[0]), Arrays.asList(parts[1].split(" ")));
                size++;
            }
        } catch (IOException e) {
        }
        G = new Digraph(size);
        try {
            BufferedReader in = new BufferedReader(new FileReader(hypernyms));
            String str;
            while ((str = in.readLine()) != null) {
                String[] hnyms = str.split(",");
                for (int i = 1; i < hnyms.length; i++) {
                    G.addEdge(Integer.parseInt(hnyms[0]), Integer.parseInt(hnyms[i]));
                }
            }
        } catch (IOException e) {
        }
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    public Iterable<String> nouns() {
        return container.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return container.containsKey(word);
    }

    private void validateNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
        if (noun == null) {
            throw new NullPointerException();
        }
    }

    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        SAP sap = new SAP(G);
        return sap.length(container.get(nounA), container.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        SAP sap = new SAP(G);
        String retunStirng = "";
        for (String str : containerB.get(sap.ancestor(container.get(nounA), container.get(nounB)))) {
            retunStirng += str + " ";
        }
        return retunStirng.substring(0, retunStirng.length() - 1);
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        System.out.println(wn.distance("immune_globulin", "immunoglobulin"));
        System.out.println(wn.sap("immune_globulin", "immunoglobulin"));

    }
}