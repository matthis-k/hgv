package kit.pse.hgv.graphSystem.element;

import kit.pse.hgv.graphSystem.GraphSystemMessages;
import kit.pse.hgv.graphSystem.exception.OverflowException;

/**
 * Edge is an element of the graph representing the connection between 2 nodes.
 * It has a unique id and 2 connecting nodes.
 */
public class Edge extends GraphElement {

    public static final int MAX_EDGE_NODES = 2;
    private Node[] nodes = new Node[MAX_EDGE_NODES];

    /**
     * Create edge with the 2 nodes as an array. The Element will automaticly have
     * an unique id.
     *
     * @param nodes array which contains the nodes the edge should be connected to.
     * @throws OverflowException        When there are to many elements and the id
     *                                  is overflowing.
     * @throws IllegalArgumentException When the array with the edges is less or
     *                                  higher than two.
     */
    public Edge(Node[] nodes) throws OverflowException, IllegalArgumentException {
        super();
        if (nodes.length != MAX_EDGE_NODES) {
            throw new IllegalArgumentException(GraphSystemMessages.MAX_NODES_EDGE.DE());
        }
        this.nodes = nodes;
    }

    /**
     * Gets youthe nodes which the edge is connected to.
     * 
     * @return Returns an array of length 2 with the nodes.
     */
    public Node[] getNodes() {
        return nodes;
    }

    public boolean connectsNodes(int[] connectingNodes) {
        if(connectingNodes.length != 2) return false;
        int firstId = nodes[0].getId();
        int secondId = nodes[1].getId();
        boolean res = ((firstId == connectingNodes[0]) || (firstId == connectingNodes[1])) && ((secondId == connectingNodes[0]) || (secondId == connectingNodes[1]));
        return res;
    }
}
