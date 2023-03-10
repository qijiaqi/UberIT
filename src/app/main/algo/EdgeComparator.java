package app.main.algo;

import java.util.Comparator;

class EdgeComparator implements Comparator<Edge> {
    public int compare(Edge e1, Edge e2) {
        if (e1.length < e2.length)
            return -1;
        else if (e1.length > e2.length)
            return 1;
        return 0;
    }
}