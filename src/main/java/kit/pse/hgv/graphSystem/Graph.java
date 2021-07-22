package kit.pse.hgv.graphSystem;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;

import java.util.*;

/**
 * This class is for accessing information about an graph.
 * You can get element groups which belong to this particular graph.
 *
 * It also manages intern creation of elements.
 */
public class Graph {

    private List<Edge> edges = new ArrayList<Edge>();
    private List<Node> nodes = new ArrayList<Node>();

    /**
     * This methods adds an element to the elements list of the graph.
     *
     * @param node is the element that should be added.
     */
    protected void addGraphElement(Node node) {
        nodes.add(node);
    }

    /**
     * This methods adds an element to the elements list of the graph.
     *
     * @param edge is the element that should be added.
     */
    protected void addGraphElement(Edge edge) {
        edges.add(edge);
    }


    /**
     * This method gets you the element with the given id.
     *
     * @param elementID is the id of the element you want to get.
     * @return Returns the element if found, else null.
     */
    protected GraphElement getElementById(int elementID) {
        for (GraphElement ge : edges) {
            if (elementID == ge.getId()) {
                return ge;
            }
        }
        for (GraphElement ge : nodes) {
            if (elementID == ge.getId()) {
                return ge;
            }
        }
        return null;
    }

    public List<Edge> getEdgesOfNode(Node node) {
        List<Edge> edges = new ArrayList<>();
        for (GraphElement element : edges) {
            Edge edge = (Edge) element;
            if(edge.getNodes()[0].equals(node) | edge.getNodes()[1].equals(node)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    /**
     * This method removes an element with the given id.
     *
     * @param elementID is the id of the element that should be deleted.
     * @return Return true is successfully deleted.
     */
    protected boolean removeElement(int elementID) {
        GraphElement ge = getElementById(elementID);
        if(ge != null) {
            if(nodes.contains(ge)) {
                for(Edge edge : getEdgesOfNode((Node) ge)) {
                    edges.remove(edge);
                }
            }
            nodes.remove(ge);
            return true;
        }
        return false;
    }

    /**
     * Gets all nodes of the graph.
     *
     * @return Returns a list of nodes the graph consists of.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Gets all edges of the graph.
     *
     * @return Returns a list of edges the graph consists of.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns all GraphElements in a list.
     *
     * @return Returns list fo GraphElements
     */
    public List<GraphElement> getGraphElements() {
        List<GraphElement> elements = new ArrayList<>();
        elements.addAll(nodes);
        elements.addAll(edges);
        return elements;
    }
}
