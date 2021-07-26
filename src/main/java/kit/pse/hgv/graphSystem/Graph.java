package kit.pse.hgv.graphSystem;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;

import java.util.*;

/**
 * This class is for accessing information about an graph. You can get element
 * groups which belong to this particular graph.
 *
 * It also manages intern creation of elements.
 */
public class Graph {

    private HashMap<Integer, Edge> edges = new HashMap<Integer, Edge>();
    private HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();

    /**
     * This methods adds an element to the elements list of the graph.
     *
     * @param node is the element that should be added.
     */
    protected void addGraphElement(Node node) {
        nodes.put(node.getId(), node);
    }

    /**
     * This methods adds an element to the elements list of the graph.
     *
     * @param edge is the element that should be added.
     */
    protected void addGraphElement(Edge edge) {
        if (getNodeById(edge.getNodes()[0].getId()) != null && getNodeById(edge.getNodes()[1].getId()) != null) {
            edges.put(edge.getId(), edge);
        }
    }

    /**
     * This method gets you the element with the given id.
     *
     * @param elementID is the id of the element you want to get.
     * @return Returns the element if found, else null.
     */
    protected GraphElement getElementById(int elementID) {
        if (nodes.get(elementID) != null) {
            return nodes.get(elementID);
        } else {
            return edges.get(elementID);
        }
    }

    public List<Edge> getEdgesOfNode(Node node) {
        List<Edge> res = new ArrayList<>();
        for (Edge edge : edges.values()) {
            if (edge.getNodes()[0].getId() == node.getId() || edge.getNodes()[1].getId() == node.getId()) {
                res.add(edge);
            }
        }
        return res;
    }

    /**
     * This method removes an element with the given id.
     *
     * @param elementID is the id of the element that should be deleted.
     * @return Return true is successfully deleted.
     */
    protected boolean removeElement(int elementID) {
        if (getEdgeById(elementID) != null) {
            edges.remove(elementID);
        } else if (getNodeById(elementID) != null) {

            for (Edge adj : getEdgesOfNode(getNodeById(elementID))) {
                removeElement(adj.getId());
            }
            nodes.remove(elementID);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Gets all nodes of the graph.
     *
     * @return Returns a list of nodes the graph consists of.
     */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Edge getEdgeById(int id) {
        return edges.get(id);
    }

    public Node getNodeById(int id) {
        return nodes.get(id);
    }

    /**
     * Gets all edges of the graph.
     *
     * @return Returns a list of edges the graph consists of.
     */
    public Collection<Edge> getEdges() {
        return edges.values();
    }

    /**
     * Returns all GraphElements in a list.
     *
     * @return Returns list fo GraphElements
     */
    public List<GraphElement> getGraphElements() {
        List<GraphElement> elements = new ArrayList<>();
        elements.addAll(getNodes());
        elements.addAll(getEdges());
        return elements;
    }

    public List<Integer> getIds() {
        List<Integer> res = new Vector<>();
        res.addAll(nodes.keySet());
        res.addAll(edges.keySet());
        return res;
    }
}
