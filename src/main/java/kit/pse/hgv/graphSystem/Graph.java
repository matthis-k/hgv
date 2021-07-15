package kit.pse.hgv.graphSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class Graph extends IdCreator {
    private HashMap<Integer, Node> nodes = new HashMap<>();
    private HashMap<Integer, Edge> edges = new HashMap<>();

    public Node addGraphElement(Node e) {
        e.setId(getNextId());
        nodes.put(e.getId(), e);
        return e;
    }

    public Edge addGraphElement(Edge e) {
        e.setId(getNextId());
        edges.put(e.getId(), e);
        return e;
    }

    public void removeId(int id) {
        nodes.remove(id);
        edges.remove(id);
    }

    public GraphElement getElementById(int id) {
        GraphElement e = edges.get(id);
        if (e != null) {
            return e;
        } else {
            return nodes.get(id);
        }
    }

    public Vector<Node> getNodes() {
        Vector<Node> res = new Vector<>();
        res.addAll(nodes.values());
        return res;
    }

    public Vector<Edge> getEdges() {
        Vector<Edge> res = new Vector<>();
        res.addAll(edges.values());
        return res;
    }

    public HashSet<Integer> getEdgeIds() {
        HashSet<Integer> res = new HashSet<>();
        res.addAll(edges.keySet());
        return res;
    }

    public HashSet<Integer> getNodeIds() {
        HashSet<Integer> res = new HashSet<>();
        res.addAll(nodes.keySet());
        return res;
    }

    public HashSet<Integer> getIds() {
        HashSet<Integer> res = getNodeIds();
        res.addAll(getEdgeIds());
        return res;
    }

    public HashSet<GraphElement> getGraphElements() {
        HashSet<GraphElement> res = new HashSet<>();
        res.addAll(getNodes());
        res.addAll(getEdges());
        return res;
    }

    public HashSet<GraphElement> getEdges(int id) {
        Node node = nodes.get(id);
        if(node == null) {
            return null;
        }
        HashSet<GraphElement> res = new HashSet<>();
        for (Edge edge: edges.values()) {
            if(edge.getStart().equals(node) || edge.getEnd().equals(node)) {
                res.add(edge);
            }
        }
        return res;
    }

}
