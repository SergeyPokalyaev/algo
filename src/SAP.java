import java.util.*;
import java.util.Stack;

/**
 * Created by Сергей on 30.03.2015.
 */
public class SAP {

    private Digraph G;

    public SAP(Digraph G) {
        this.G = new Digraph(G.V());
        for (int i = 0; i < G.V(); i++) {
            for (int adjVer : G.adj(i)) {
                this.G.addEdge(i, adjVer);
            }
        }
    }

    private int[] bfsForFind(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        for (Integer i : v) {
            if (i < 0 || i >= G.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        for (Integer i : w) {
            if (i < 0 || i >= G.V()) {
                throw new IndexOutOfBoundsException();
            }
        }


        int[] sizeToV = new int[G.V()];
        int[] sizeToW = new int[G.V()];


        Queue<Integer> qV = new Queue<Integer>();
        Queue<Integer> qW = new Queue<Integer>();

        Stack<Integer> sV = new Stack<Integer>();
        Stack<Integer> sW = new Stack<Integer>();


        for (int i = 0; i < G.V(); i++) {
            sizeToV[i] = -1;
            sizeToW[i] = -1;
        }


        for (int s : v) {
            sizeToV[s] = 0;
            sV.push(s);
            qV.enqueue(s);
        }
        for (int s : w) {
            sizeToW[s] = 0;
            sW.push(s);
            qW.enqueue(s);
        }

        int dist = 0;
        while (!qV.isEmpty() || !qW.isEmpty()) {

            dist++;
            if (!qV.isEmpty()) {
                Queue<Integer> qLocal = new Queue<Integer>();
                for (int ver : qV) {
                    for (int verLocal : G.adj(ver)) {
                        if (sizeToV[verLocal] == -1) {
                            sizeToV[verLocal] = dist;
                            sV.push(verLocal);
                            qLocal.enqueue(verLocal);
                        }
                    }
                }
                qV = qLocal;
            }

            if (!qW.isEmpty()) {
                Queue<Integer> qLocal = new Queue<Integer>();
                for (int ver : qW) {
                    for (int verLocal : G.adj(ver)) {
                        if (sizeToW[verLocal] == -1) {
                            sizeToW[verLocal] = dist;
                            sW.push(verLocal);
                            qLocal.enqueue(verLocal);
                        }
                    }
                }
                qW = qLocal;
            }



        }
        int[] result = new int[]{0, Integer.MAX_VALUE};
        for (Integer i : sV) {
            if (sW.contains(i) && result[1] > sizeToV[i] + sizeToW[i]) {
                result = new int[]{i, sizeToV[i] + sizeToW[i]};
            }
        }
        if (result[1] != Integer.MAX_VALUE) {
            return result;
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

        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        //System.out.println(G);
        System.out.println(sap.ancestor(6, 9));
        System.out.println(sap.length(6, 9));

    }
}
