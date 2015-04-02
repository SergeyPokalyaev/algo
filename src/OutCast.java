/**
 * Created by Sergej.Pokalyaev on 02.04.2015.
 */
public class OutCast {

    private WordNet wordNet;

    public OutCast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outcaseWord = "";
        int distSumMax = 0;
        for (String noun : nouns) {
            int distSum = 0;
            for (String wordNetWord : wordNet.nouns()) {
                int distance = wordNet.distance(wordNetWord, noun);
                if (distance != -1) {
                    distSum += distance;
                }
            }
            if (distSumMax < distSum) {
                outcaseWord = noun;
                distSumMax = distSum;
            }
        }
        return outcaseWord;
    }

    public static void main(String[] args) {}

}
