package kit.pse.hgv.controller.commandProcessor;


import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.GraphElementDeleteCommand;
import kit.pse.hgv.controller.commandController.commands.MoveNodeCommand;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphCommandProcessorTest {

    private static GraphCommandProcessor graphCommandProcessor;

    @BeforeClass
    public static void setup() {
        graphCommandProcessor = new GraphCommandProcessor();
    }

    @Test
    public void testAddEdge() {
        graphCommandProcessor.addEdge(1, 2, 3);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof CreateEdgeCommand);
    }

    @Test
    public void testAddNode() {
        graphCommandProcessor.addNode(1, "1", "1");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof CreateNodeCommand);
    }

    @Test(expected = NumberFormatException.class)
    public void testAddNodeFailure() {
        graphCommandProcessor.addNode(1, "NoNumber", "2");
    }

    @Test
    public void testMoveNode() {
        graphCommandProcessor.moveNode(1, "2", "2");
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof MoveNodeCommand);
    }

    @Test(expected = NumberFormatException.class)
    public void testMoveNodeFailure() {
        graphCommandProcessor.moveNode(1, "NoNumber", "2");
    }

    @Test
    public void testDeleteElement() {
        graphCommandProcessor.deleteElement(1);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof GraphElementDeleteCommand);
    }

    @AfterClass
    public static void free() {
        graphCommandProcessor = null;
    }
}
