package app.main.algo;

public class Edge {
    public int depart;
    public int arrive;
    public double length;

    public Edge(int depart, int arrive, double length) {
        this.depart = depart;
        this.arrive = arrive;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "depart=" + depart +
                ", arrive=" + arrive +
                ", length=" + length +
                '}';
    }
}