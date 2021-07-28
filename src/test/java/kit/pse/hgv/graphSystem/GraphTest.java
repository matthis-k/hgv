package kit.pse.hgv.graphSystem;

import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.CartesianCoordinate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class GraphTest {

    private GraphSystem g;
    private int graphId;
    @Before
    public void init() {
        g = GraphSystem.getInstance();
    }


    @Test
    public void accessGraph() {
        graphId = g.newGraph();
        assertNotNull(g.getGraphByID(graphId));
    }

    @Test
    public void addNode() throws OverflowException {
        graphId = g.newGraph();
        int id = g.addElement(graphId, new CartesianCoordinate(0,0));
        assertNotNull(g.getGraphElementByID(id));
    }

    @Test
    public void addEdge() throws OverflowException {
        graphId = g.newGraph();
        int id1 = g.addElement(graphId, new CartesianCoordinate(0,0));
        int id2 = g.addElement(graphId, new CartesianCoordinate(0,0));
        int[] nodes = {id1, id2};
        int id3 = g.addElement(graphId, nodes);

        assertNotNull(g.getGraphElementByID(id3));
    }

    @Test
    public void removeElement() throws OverflowException {
        graphId = g.newGraph();
        int id = g.addElement(graphId, new CartesianCoordinate(0,0));
        g.removeElement(id);
        assertNull(g.getGraphElementByID(id));
    }

    @Test
    public void removeNodeDelEdges() throws OverflowException {
        graphId = g.newGraph();
        int id1 = g.addElement(graphId, new CartesianCoordinate(0,0));
        int id2 = g.addElement(graphId, new CartesianCoordinate(0,0));
        int[] nodes = {id1, id2};
        int id3 = g.addElement(graphId, nodes);
        g.removeElement(id2);
        assertNull(g.getGraphElementByID(id3));
    }

    @After
    public void destroy() {
        g.removeGraph(graphId);
        graphId = 0;
    }
}
