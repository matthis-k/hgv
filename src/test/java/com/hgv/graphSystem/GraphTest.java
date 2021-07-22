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
    @Before
    public void init() {
        g = GraphSystem.getInstance();
    }


    @Test
    public void accessGraph() {
        g.loadGraph("");
        assertNotNull(g.getGraphElementByID(1));
    }

    @Test
    public void addNode() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        assertNotNull(g.getGraphElementByID(1));
    }

    @Test
    public void addEdge() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        g.addElement(1, new Coordinate());
        int[] nodes = {1,2};
        g.addElement(1, nodes);

        assertNotNull(g.getGraphElementByID(3));
    }

    @Test
    public void removeElement() throws OverflowException {
        g.loadGraph("");
        g.addElement(1, new Coordinate());
        g.removeElement(1);
        assertNull(g.getGraphElementByID(1));
    }

    @Test
    public void removeNodeDelEdges() throws OverflowException {
        g.loadGraph("");
        int id1 = g.addElement(1, new Coordinate());
        int id2 = g.addElement(1, new Coordinate());
        System.out.println(id1);
        System.out.println(id2);
        int[] nodes = {id1, id2};
        int id3 = g.addElement(1, nodes);
        System.out.println(id3);
        g.removeElement(id2);
        System.out.println(g.getGraphElementByID(3));
        assertNull(g.getGraphElementByID(3));
    }

    @After
    public void destroy() {
        g.removeGraph(0);
    }
}
