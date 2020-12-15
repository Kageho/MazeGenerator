package maze;
/* This class represents an edge*/

public class Edge {
    Node firstNode;
    Node secondNode;
    int weight;

    public Edge(Node firstNode, Node secondNode, int weight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }

    int getWeight() {
        return this.weight;
    }

    Node getFirstNode() {
        return this.firstNode;
    }

    Node getSecondNode() {
        return this.secondNode;
    }
}
