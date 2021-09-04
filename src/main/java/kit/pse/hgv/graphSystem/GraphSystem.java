package kit.pse.hgv.graphSystem;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.IllegalGraphOperation;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.representation.Coordinate;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Manages the creation an removal of graphs and elements. Also has getter for
 * graphs and elements (not dependend on the graph!)
 */
public class GraphSystem {
    /** Singleton instance */
    private static GraphSystem instance;

    /** List of graphs that are loaded. */
    private HashMap<Integer, Graph> graphs = new HashMap<>();

    private static int graphIDCounter = 1;

    /**
     * Creates a new element of this class
     */
    private GraphSystem() {
    }

    /**
     * Creates or gets the only existing GraphSystem instance.
     * 
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
        for (Graph graph : graphs.values()) {
            GraphElement element = graph.getElementById(elementID);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    /**
     * This method gets an element by the id. It searches all graphs.
     *
     * @param elementID is the id of the element that should be searched for.
     * @return Returns the element im possible. Could be null if not found.
     */
    public GraphElement getGraphElementByID(int graphID, int elementID) {
        return getGraphByID(graphID).getElementById(elementID);
    }

    /**
     * Gets you the Node with the id, if possible.
     * 
     * @param nodeID is the id of the node you will get.
     * @return Returns the node if found, else null.
     */
    public Node getNodeByID(int nodeID) {
        GraphElement element = getGraphElementByID(nodeID);
        if (element instanceof Node) {
            return (Node) element;
        }
        return null;
    }

    /**
     * Gets you the Node with the id in the graph with the graphID, if possible.
     * 
     * @param nodeID is the id of the node you will get.
     * @return Returns the node if found, else null.
     */
    public Node getNodeByID(int graphID, int nodeID) {
        return getGraphByID(graphID).getNodeById(nodeID);
    }

    /**
     * Gets you the Edge with the id, if possible.
     * 
     * @param edgeID is the id of the edge you will get.
     * @return Returns the edge if found, else null.
     */
    public Edge getEdgeByID(int edgeID) {
        GraphElement element = getGraphElementByID(edgeID);
        if (element instanceof Edge) {
            return (Edge) element;
        }
        return null;
    }

    /**
     * Gets you the Edge with the id in the graph with the graphID, if possible.
     * 
     * @param edgeID is the id of the edge you will get.
     * @return Returns the edge if found, else null.
     */
    public Edge getEdgeByID(int graphID, int edgeID) {
        return getGraphByID(graphID).getEdgeById(edgeID);
    }

    public int newGraph() {
        Graph temp = new Graph();
        int graphID = graphIDCounter++;
        graphs.put(graphID, temp);
        return graphID;
    }

    /**
     * Loads graph from the path to the memory and stores it in GraphSystem.
     *
     * @param path is the pat where the graph should be loaded from.
     * @return Returns the graphID of the loaded graph. The graph can be get by this
     *         id in future.
     */
    public int loadGraph(String path) throws FileNotFoundException, OverflowException, IllegalGraphOperation {
        int graphID = newGraph();
        DataGateway.loadGraph(path, graphID);
        return graphID;
    }

    /**
     * Removes graph with the given id if possible.
     *
     * @param graphID is the id of the graph that should be removed.
     * @return Returns true, when graph was found and deleted, false when not.
     */
    public boolean removeGraph(int graphID) {
        if (graphs.get(graphID) == null) {
            return false;
        }
        graphs.remove(graphID);
        return true;
    }

    /**
     * Adds a new {@link kit.pse.hgv.graphSystem.element.Edge Edge} to the graph.
     *
     * @param graphID is the id of the graph where the new edge should be stored.
     * @param nodeIDs is an array of 2 node ids which the Edge should be connected
     *                to.
     */
    public int addElement(int graphID, int[] nodeIDs) throws OverflowException, IllegalGraphOperation {
        if (nodeIDs.length != 2) {
            throw new IllegalArgumentException(GraphSystemMessages.EDGE_ONLY_WITH_NODES.DE());
        }
        if(getGraphByID(graphID) == null) {
            throw new IllegalGraphOperation(GraphSystemMessages.NO_GRAPH.DE());
        }
        Node[] nodes = new Node[2];

        //For mistakes in using.
        if(getNodeByID(nodeIDs[0]) == null || getNodeByID(nodeIDs[1]) == null) {
            throw new IllegalGraphOperation(GraphSystemMessages.NODE_MISSING.DE());
        }
        nodes[0] = getNodeByID(nodeIDs[0]);
        nodes[1] = getNodeByID(nodeIDs[1]);

        Edge edge = new Edge(nodes);
        graphs.get(graphID).addGraphElement(edge);
        return edge.getId();
    }

    /**
     * Adds a new {@link kit.pse.hgv.graphSystem.element.Node Node} to the graph.
     *
     * @param graphID is the id of the graph where the new edge should be stored.
     * @param coord   is the Coordinate of the node should be at.
     */
    public int addElement(int graphID, Coordinate coord) throws OverflowException, IllegalGraphOperation {
        if(getGraphByID(graphID) == null) {
            throw new IllegalGraphOperation(GraphSystemMessages.NO_GRAPH.DE());
        }
        Node node = new Node(coord);
        graphs.get(graphID).addGraphElement(node);
        return node.getId();
    }

    /**
     * Removes an element out of the graphsystem.
     *
     * @param elementID is the element ID that should be deleted.
     * @return A List of all element ids that were deleted.
     */
    public List<Integer> removeElement(int elementID) {
        List<Integer> deleted = new Vector<>();
        for (Graph g : graphs.values()) {
            deleted.addAll(g.removeElement(elementID));
        }
        return deleted;
    }

    public int getGraphOfNode(int nodeId) {
        int graphId = -1;
        for (int key : graphs.keySet()) {
            if (graphs.get(key).getNodeById(nodeId) != null) {
                graphId = key;
                break;
            }
        }
        return graphId;
    }

    public List<Integer> getEdgeIdsOfNode(int nodeId) {
        List<Integer> edgeIds = new Vector<>();
        int graphId = getGraphOfNode(nodeId);
        if (graphId < 0) {
            return edgeIds;
        }
        for (Edge e : getGraphByID(graphId).getEdgesOfNode(getNodeByID(nodeId))) {
            edgeIds.add(e.getId());
        }
        return edgeIds;

    }

    /**
     * Returns all Metadata Information of each element of the graph
     *
     * @param graphID graphId from the graph that should return all Metadata
     *                Information
     * @return all Metadatas of each element in the graph
     */
    public Collection<String> getAllMetadataByID(int graphID) {
        return getGraphByID(graphID).getAllMetadata();
    }

    /**
     * Returns all existing ids of a given graph
     *
     * @param graphID graphId from the graph that should return all Ids
     * @return all existing ids in the graph
     */
    public HashSet<Integer> getIDs(int graphID) {
        Graph g = getGraphByID(graphID);
        return g == null ? null : g.getIds();
    }

    public boolean isInGraph(int graphId, int id) {
        return getGraphByID(graphId).isInGraph(id);
    }

    public Collection<Integer> getAllIds() {
        Vector<Integer> ids = new Vector<>();
        for (Graph g : graphs.values()) {
            ids.addAll(g.getIds());
        }
        return ids;
    }

    public Color getColorOfId(int id) {
        final Color defaultNode = Color.RED;
        final Color defaultEdge = Color.BLACK;
        GraphElement el = getGraphElementByID(id);
        try {
            return Color.web(el.getMetadata("color"));
        } catch (NullPointerException | IllegalArgumentException e) {
            return el instanceof Node ? defaultNode : defaultEdge;
        }
    }

    public boolean newMetadataDefinition(int graphID, MetadataDefinition metadataDefinition) {
        return graphs.get(graphID).newMetadataDefinition(metadataDefinition);
    }
}
