package kit.pse.hgv.graphSystem;

public class Edge extends GraphElement {
    private Node start;
    private Node end;
    public Edge(Node start, Node end) {
        super();
        this.start = start;
        this.end = end;
    }
    public Node getStart() {
        return start;
    }
    public Node getEnd() {
        return end;
    }
}
