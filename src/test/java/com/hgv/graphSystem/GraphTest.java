package com.hgv.graphSystem;

import kit.pse.hgv.graphSystem.Coordinate;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;
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
        graphId = g.loadGraph("");
        assertNotNull(g.getGraphByID(graphId));
    }

    @Test
    public void addNode() throws OverflowException {
        graphId = g.loadGraph("");
        int id = g.addElement(graphId, new Coordinate());
        assertNotNull(g.getGraphElementByID(id));
    }

    @Test
    public void addEdge() throws OverflowException {
        graphId = g.loadGraph("");
        int id1 = g.addElement(graphId, new Coordinate());
        int id2 = g.addElement(graphId, new Coordinate());
        int[] nodes = {id1, id2};
        int id3 = g.addElement(graphId, nodes);

        assertNotNull(g.getGraphElementByID(id3));
    }

    @Test
    public void removeElement() throws OverflowException {
        graphId = g.loadGraph("");
        int id = g.addElement(graphId, new Coordinate());
        g.removeElement(id);
        assertNull(g.getGraphElementByID(id));
    }

    @Test
    public void removeNodeDelEdges() throws OverflowException {
        graphId = g.loadGraph("");
        int id1 = g.addElement(graphId, new Coordinate());
        int id2 = g.addElement(graphId, new Coordinate());
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
