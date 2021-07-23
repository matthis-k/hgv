package kit.pse.hgv.graphSystem.element;

import kit.pse.hgv.graphSystem.Coordinate;
import kit.pse.hgv.graphSystem.exception.OverflowException;

/**
 * This class represents a node, which is a graphelement.
 * Nodes can be moved.
 * See more to elements of graphs in the {@link GraphElement GraphElement-Class}.
 */
public class Node extends GraphElement {
    private Coordinate coord;

    /**
     * Creates a node with the starting coordinate.
     *
     * @param coord is the coordinate, where the node should be created. Can be edited afterwards.
     * @throws OverflowException if there are to many elements, so the id is overflowing.
     */
    public Node(Coordinate coord) throws OverflowException {
        super();
        this.coord = coord;
    }

    public Coordinate getCoord() {
        return coord;
    }    

    /**
     * Moves the node coordinate to the new given coordinate.
     * @param coord is the coord the node should be moved to.
     */
    public void move(Coordinate coord) {
        this.coord = coord;
    }
}
