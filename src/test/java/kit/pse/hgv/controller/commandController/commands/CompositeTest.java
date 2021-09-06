package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

public class CompositeTest {
    private static int graphId;

    @Before
    public  void init() throws FileNotFoundException, OverflowException {
        graphId = GraphSystem.getInstance().loadGraph("src/test/resources/Vorlage.graphml");
    }

    @Test
    public void compositeSuccess() {
        CommandComposite c = new CommandComposite();
        Collection<Node> nodes = GraphSystem.getInstance().getGraphByID(graphId).getNodes();
        for (Node n : nodes) {
            c.addCommand(new GraphElementDeleteCommand(n.getId()));
        }
        c.execute();
        assertTrue(c.succeeded());
        assertTrue(GraphSystem.getInstance().getIDs(graphId).isEmpty());

    }

    @Test
    public void compositeFailure() {
        CommandComposite c = new CommandComposite();
        c.addCommand(new GraphElementDeleteCommand(-1));
        c.execute();
        assertFalse(c.succeeded());

    }
}
