import java.util.*;
import java.util.Stack;

/**
 * Created by Сергей on 30.03.2015.
 */
public class SAP {

    private Digraph G;

    public SAP(Digraph G) {
        this.G = G;
    }

    private int[] bfsForFind(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(G, v);
        boolean[] marked = new boolean[G.V()];
        int[] distTo = new int[G.V()];
        int[] edgeTo = new int[G.V()];
        for (int verLocal = 0; verLocal < G.V(); verLocal++)
            distTo[verLocal] = Integer.MAX_VALUE;

        Queue<Integer> q = new Queue<Integer>();
        for (int s : w) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int ver = q.dequeue();
            for (int verLocal : G.adj(ver)) {
                if (!marked[verLocal]) {
                    edgeTo[verLocal] = ver;
                    distTo[verLocal] = distTo[ver] + 1;
                    marked[verLocal] = true;
                    if (bfdpV.hasPathTo(verLocal)) {
                        return new int[]{verLocal, distTo[verLocal] + bfdpV.distTo(verLocal)};
                    }
                    q.enqueue(verLocal);
                }
            }
        }
        return null;
    }

    public int length(int v, int w) {
        Stack<Integer> vst = new Stack<Integer>();
        Stack<Integer> wst = new Stack<Integer>();
        vst.push(v);
        wst.push(w);
        int[] result = bfsForFind(vst, wst);
        return result == null ? -1 : result[1];
    }

    public int ancestor(int v, int w) {
        Stack<Integer> vst = new Stack<Integer>();
        Stack<Integer> wst = new Stack<Integer>();
        vst.push(v);
        wst.push(w);
        int[] result = bfsForFind(vst, wst);
        return result == null ? -1 : result[0];
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = bfsForFind(v, w);
        return result == null ? -1 : result[1];
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = bfsForFind(v, w);
        return result == null ? -1 : result[0];
    }

    public static void main(String[] args) {

    }
}
