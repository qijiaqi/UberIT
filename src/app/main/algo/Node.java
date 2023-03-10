package app.main.algo;

public class Node implements Comparable<Node> {

    public long id;
    public double distance;

    public Node(long id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(Node node) {

        if (this.distance < node.distance)//replaced with Double.compare
            return -1;

        if (this.distance > node.distance)
            return 1;

        return 0;
    }
}
