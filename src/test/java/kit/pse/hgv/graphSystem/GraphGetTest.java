package kit.pse.hgv.graphSystem;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class GraphGetTest {

    private GraphSystem g;
    private int graphId;
    @Before
    public void init() {
        g = GraphSystem.getInstance();
    }

    @After
    public void destroy() {
        g.removeGraph(graphId);
        graphId = 0;
    }

    @Test
    public void getNodeById() throws OverflowException { //TODO: FAILURE
        Coordinate c = new CartesianCoordinate(0, 0);
        g.newGraph();
        g.addElement(1, c);
        assert(g.getGraphElementByID(1).getId() == 1);
        assert(((Node) g.getGraphElementByID(1)).getCoord().equals(c));
    }

    @Test
    public void getEdgeById() throws OverflowException { //TODO: FAILURE
        Coordinate c0 = new CartesianCoordinate(0, 0);
        Coordinate c1 = new CartesianCoordinate(1, 1);
        g.newGraph();
        g.addElement(1, c0);
        g.addElement(1, c1);
        int[] nds = {1,2};
        g.addElement(1, nds);
        assert(g.getGraphElementByID(3).getId() == 3);
        assert(((Edge) g.getGraphElementByID(3)).getNodes()[0].getId() == 1);
        assert(((Edge) g.getGraphElementByID(3)).getNodes()[1].getId() == 2);
    }

    @Test
    public void getAllIds() throws OverflowException { //TODO: FAILURE
        Coordinate c0 = new CartesianCoordinate(0, 0);
        Coordinate c1 = new CartesianCoordinate(1, 1);
        g.newGraph();
        g.addElement(1, c0);
        g.addElement(1, c1);
        int[] i = {1, 2};
        int[] k = new int[2];
        for(int h = 0; h < g.getAllIds().size(); h++) {
            k[h] = (int) g.getAllIds().toArray()[h];
        }
        assertArrayEquals(k, i);
    }

    //TODO
    @Test
    public void getMetaData() {
        assert(true);
    }


    //TODO
    @Test
    public void setMetaData() {
        assert(true);
    }

    //TODO
    @Test
    public void isInGraph() {
        assert(true);
    }








}
