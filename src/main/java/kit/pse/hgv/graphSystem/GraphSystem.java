package kit.pse.hgv.graphSystem;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.graphSystem.stub.DataGateway;

import java.util.HashMap;

/**
 * Manages the creation an removal of graphs and elements.
 * Also has getter for graphs and elements (not dependend on the graph!)
 */
public class GraphSystem {
    /** Singleton instance */
    private static GraphSystem instance;

    /** List of graphs that are loaded. */
    private HashMap<Integer, Graph> graphs = new HashMap<>();

    private static int graphIDCounter = 1;

    private GraphSystem() {}

    /**
     * Creates or gets the only existing GraphSystem instance.
     * @return Returns the only instance of the graphsystem.
     */
    public static GraphSystem getInstance() {
        if (instance == null) {
            instance = new GraphSystem();
        }
        return instance;
    }


    /**
     * Gets the graph with the given id.
     *
     * @param graphId is the id of the graph you want to get.
     * @return Returns the graph . Can be null.
     */
    public Graph getGraphByID(int graphId) {
        return graphs.get(graphId);
    }

    /**
     * This method gets an element by the id. It searches all graphs.
     *
     * @param elementID is the id of the element that should be searched for.
     * @return Returns the element im possible. Could be null if not found.
     */
    public GraphElement getGraphElementByID(int elementID) {
        for(Graph graph : graphs.values()) {
            GraphElement element = graph.getElementById(elementID);
            if(element != null) {
                return element;
            }
        }
        return null;
    }


    /**
     * Loads graph from the path to the memory and stores it in GraphSystem.
     *
     * @param path is the pat where the graph should be loaded from.
     * @return Returns the graphID of the loaded graph. The graph can be get by this id in future.
     */
    public int loadGraph(String path) {
        int graphID = graphIDCounter++;
        Graph g = DataGateway.loadGraph(path);
        if(g == null) {
            throw new IllegalArgumentException(GraphSystemMessages.PATH_ERROR.DE());
        }
        graphs.put(graphID, g);
        return graphID;
    }

    /**
     * Removes graph with the given id if possible.
     *
     * @param graphID is the id of the graph that should be removed.
     * @return Returns true, when graph was found and deleted, false when not.
     */
    public boolean removeGraph(int graphID) {
        if(graphs.get(graphID) == null) {
            return false;
        }
        graphs.remove(graphID);
        return true;
    }

    /**
     * Adds a new {@link kit.pse.hgv.graphSystem.element.Edge Edge} to the graph.
     *
     * @param graphID is the id of the graph where the new edge should be stored.
     * @param nodeIDs is an array of 2 node ids which the Edge should be connected to.
     */
    public int addElement(int graphID, int[] nodeIDs) throws OverflowException {

        Node[] nodes = new Node[Edge.MAX_EDGE_NODES];

        nodes[0] = (Node) getGraphElementByID(nodeIDs[0]);
        nodes[1] = (Node) getGraphElementByID(nodeIDs[1]);

        //Param check.
        if(!(nodes[0] instanceof Node) || !(nodes[1] instanceof Node)) {
            throw new IllegalArgumentException(GraphSystemMessages.EDGE_ONLY_WITH_NODES.DE());
        }

        Edge edge = new Edge(nodes);
        graphs.get(graphID).addGraphElement(edge);
        return edge.getId();
    }

    /**
     * Adds a new {@link kit.pse.hgv.graphSystem.element.Node Node} to the graph.
     *
     * @param graphID is the id of the graph where the new edge should be stored.
     * @param coord is the Coordinate of the node should be at.
     */
    public int addElement(int graphID, Coordinate coord) throws OverflowException {
        Node node = new Node(coord);
        graphs.get(graphID).addGraphElement(node);
        return node.getId();
    }

    /**
     * Removes an element out of the graphsystem.
     *
     * @param elementID is the element ID that should be deleted.
     * @return Returns true is successful deleted.
     */
    public boolean removeElement(int elementID) {
        boolean deleted = false;
        for(Graph g : graphs.values()) {
            deleted = g.removeElement(elementID);
        }
        return deleted;
    }
}
