package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.IllegalGraphOperation;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Collection;

public class CompositeTest {
    private static int graphId;

    /**
     * The BeforeMethod initializes a new Graph
     *
     * @throws FileNotFoundException if the file couldn't be found
     * @throws OverflowException if the graph is too big
     * @throws IllegalGraphOperation if the graph is not valid
     */
    @Before
    public  void init() throws FileNotFoundException, OverflowException, IllegalGraphOperation {
        graphId = GraphSystem.getInstance().loadGraph("src/test/resources/Vorlage.graphml");
    }

    /**
     * Tests if the CompositeCommand is successful and if it executes all commands in it
     */
    @Test
    public void compositeSuccess() {
        CommandComposite c = new CommandComposite();
        Collection<Node> nodes = GraphSystem.getInstance().getGraphByID(graphId).getNodes();
        for (Node n : nodes) {
            c.addCommand(new GraphElementDeleteCommand(n.getId(), graphId));
        }
        c.execute();
        assertTrue(c.succeeded());
        assertTrue(GraphSystem.getInstance().getIDs(graphId).isEmpty());
    }

    /**
     * Tests if the CompositeCommand returns false when one of the Commands in it return false
     */
    @Test
    public void compositeFailure() {
        CommandComposite c = new CommandComposite();
        c.addCommand(new GraphElementDeleteCommand(-1, graphId));
        c.execute();
        assertFalse(c.succeeded());
    }

    /**
     * The AfterMethod removes the initialized graph
     */
    @After
    public void free() {
        GraphSystem.getInstance().removeGraph(graphId);
    }
}
