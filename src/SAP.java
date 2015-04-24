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


        boolean[] markedV = new boolean[G.V()];
        boolean[] markedW = new boolean[G.V()];
        int[] sizeToV = new int[G.V()];
        int[] sizeToW = new int[G.V()];


        Queue<Integer> qV = new Queue<Integer>();
        Queue<Integer> qW = new Queue<Integer>();

        Stack<Integer> sV = new Stack<Integer>();
        Stack<Integer> sW = new Stack<Integer>();

        for (int s : v) {
            markedV[s] = true;
            sV.push(s);
            qV.enqueue(s);
        }
        for (int s : w) {
            markedW[s] = true;
            sW.push(s);
            qW.enqueue(s);
        }

        int dist = 0;
        while (!qV.isEmpty() || !qW.isEmpty()) {

            for (Integer i : sV) {
                if (sW.contains(i)) {
                    return new int[]{i, sizeToV[i] + sizeToW[i]};
                }
            }

            for (Integer i : sW) {
                if (sV.contains(i)) {
                    return new int[]{i, sizeToV[i] + sizeToW[i]};
                }
            }

            dist++;
            if (!qV.isEmpty()) {
                int ver = qV.dequeue();
                for (int verLocal : G.adj(ver)) {
                    if (!markedV[verLocal]) {
                        markedV[verLocal] = true;
                        sizeToV[verLocal] = dist;
                        sV.push(verLocal);
                        qV.enqueue(verLocal);
                    }
                }
            }

            if (!qW.isEmpty()) {
                int ver = qW.dequeue();
                for (int verLocal : G.adj(ver)) {
                    if (!markedW[verLocal]) {
                        markedW[verLocal] = true;
                        sizeToW[verLocal] = dist;
                        sW.push(verLocal);
                        qW.enqueue(verLocal);
                    }
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

        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        System.out.println(sap.ancestor(14, 7));
        System.out.println(sap.length(14, 7));

        System.out.println(G);

    }
}
