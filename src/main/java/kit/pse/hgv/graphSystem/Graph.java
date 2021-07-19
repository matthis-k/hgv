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

    private List<GraphElement> elements = new ArrayList<GraphElement>();

    /**
     * This methods adds an element to the elements list of the graph.
     *
     * @param ge is the element that should be added.
     */
    protected void addGraphElement(GraphElement ge) {
        elements.add(ge);
    }

    /**
     * This method gets you the element with the given id.
     *
     * @param elementID is the id of the element you want to get.
     * @return Returns the element if found, else null.
     */
    protected GraphElement getElementById(int elementID) {
        for (GraphElement ge : elements) {
            if (elementID == ge.getId()) {
                return ge;
            }
        }
        return null;
    }

    public List<Edge> getEdgesOfNode(Node node) {
        List<Edge> edges = new ArrayList<>();
        for (GraphElement element : elements) {
            if (element instanceof  Edge) {
                Edge edge = (Edge) element;
                if(edge.getNodes()[0].getId() == node.getId() || edge.getNodes()[1].getId() == node.getId()) {
                    edges.add(edge);
                }
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
            if(ge instanceof Node) {
                for(Edge edge : getEdgesOfNode((Node) ge)) {
                    elements.remove(edge);
                }
            }

            elements.remove(ge);
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
        List<Node> nodes = new ArrayList<>();
        for(GraphElement ge : elements) {
            if(ge instanceof Node) {
                nodes.add((Node) ge);
            }
        }
        return nodes;
    }

    /**
     * Gets all edges of the graph.
     *
     * @return Returns a list of edges the graph consists of.
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for(GraphElement ge : elements) {
            if(ge instanceof Edge) {
                edges.add((Edge) ge);
            }
        }
        return edges;
    }

    /**
     * Returns all GraphElements in a list.
     *
     * @return Returns list fo GraphElements
     */
    public List<GraphElement> getGraphElements() { ;
        return elements;
    }
}
