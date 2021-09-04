package kit.pse.hgv.controller.commandProcessor;


import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.GraphElementDeleteCommand;
import kit.pse.hgv.controller.commandController.commands.MoveNodeCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

public class GraphCommandProcessorTest {

    private static GraphCommandProcessor graphCommandProcessor;
    private static CommandController commandController;

    @BeforeClass
    public static void setup() {
        commandController = CommandController.getInstance();
        graphCommandProcessor = new GraphCommandProcessor();
    }

    @Before
    public void clearQb() {
        commandController.getCommandQ().clear();
    }

    @After
    public void clearQ() {
        commandController.getCommandQ().clear();
    }

    @Test
    public void testAddEdge() {
        graphCommandProcessor.addEdge(1, 2, 3);
        assertTrue(commandController.getCommandQ().poll() instanceof CreateEdgeCommand);
    }

    @Test
    public void testAddNode() {
        graphCommandProcessor.addNode(1, "1", "1");
        assertTrue(commandController.getCommandQ().poll() instanceof CreateNodeCommand);
    }

    @Test(expected = NumberFormatException.class)
    public void testAddNodeFailure() {
        graphCommandProcessor.addNode(1, "NoNumber", "2");
    }

    @Test
    public void testMoveNode() {
        graphCommandProcessor.moveNode(1, "2", "2");
        assertTrue(commandController.getCommandQ().poll() instanceof MoveNodeCommand);
    }

    @Test(expected = NumberFormatException.class)
    public void testMoveNodeFailure() {
        graphCommandProcessor.moveNode(1, "NoNumber", "2");
    }

    @Test
    public void testDeleteElement() {
        graphCommandProcessor.deleteElement(1);
        assertTrue(commandController.getCommandQ().poll() instanceof GraphElementDeleteCommand);
    }

    @AfterClass
    public static void free() {
        graphCommandProcessor = null;
        commandController = null;
    }
}
