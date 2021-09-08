package kit.pse.hgv.controller.commandProcessor;


import static org.junit.Assert.assertTrue;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.GraphElementDeleteCommand;
import kit.pse.hgv.controller.commandController.commands.MoveNodeCommand;
import org.junit.*;

public class GraphCommandProcessorTest {

    private static GraphCommandProcessor graphCommandProcessor;
    private static CommandController commandController;

    /**
     * gets the instance of the commandController and creates a new GraphCommandProcessor
     */
    @BeforeClass
    public static void setup() {
        commandController = CommandController.getInstance();
        graphCommandProcessor = new GraphCommandProcessor();
    }

    /**
     * clears the CommandQueue
     */
    @Before
    public void clearQb() {
        commandController.getCommandQ().clear();
    }

    /**
     * clears the CommandQueue
     */
    @After
    public void clearQ() {
        commandController.getCommandQ().clear();
    }

    /**
     * Tests if the CreateEdgeCommand is created and queued if the addEdge method is called from the UI
     */
    @Test
    public void testAddEdge() {
        graphCommandProcessor.addEdge(1, 2, 3);
        assertTrue(commandController.getCommandQ().poll() instanceof CreateEdgeCommand);
    }

    /**
     * Tests if the CreateEdgeCommand is created and queued if the addEdge method is called from the UI
     */
    @Test
    public void testAddEdgeString() {
        graphCommandProcessor.addEdge(1, "2", "3");
        assertTrue(commandController.getCommandQ().poll() instanceof CreateEdgeCommand);
    }

    /**
     * Tests if a NumberFormatException is thrown when an id is not valid
     */
    @Test(expected = NumberFormatException.class)
    public void testAddEdgeStringFailure() {
        graphCommandProcessor.addEdge(1, "notValid", "3");
    }

    /**
     * Tests if the CreateNodeCommand is created and queued if the addNode method is called from the UI
     */
    @Test
    public void testAddNode() {
        graphCommandProcessor.addNode(1, "1", "1");
        assertTrue(commandController.getCommandQ().poll() instanceof CreateNodeCommand);
    }

    /**
     * Tests if a NumberFormatException is thrown when an id is not valid
     */
    @Test(expected = NumberFormatException.class)
    public void testAddNodeFailure() {
        graphCommandProcessor.addNode(1, "NoNumber", "2");
    }

    /**
     * Tests if the MoveNodeCommand is created and queued if the moveNode method is called from the UI
     */
    @Test
    public void testMoveNode() {
        graphCommandProcessor.moveNode(1, "2", "2");
        assertTrue(commandController.getCommandQ().poll() instanceof MoveNodeCommand);
    }

    /**
     * Tests if a NumberFormatException is thrown when the angle or distance are not valid numbers
     */
    @Test(expected = NumberFormatException.class)
    public void testMoveNodeFailure() {
        graphCommandProcessor.moveNode(1, "NoNumber", "2");
    }

    /**
     * Tests if the GraphElementDeleteCommand is created and queued if the deleteElement method is called from the UI
     */
    @Test
    public void testDeleteElement() {
        graphCommandProcessor.deleteElement(1, 1);
        assertTrue(commandController.getCommandQ().poll() instanceof GraphElementDeleteCommand);
    }

    /**
     * Tests if the GraphElementDelete is created and queued if the deleteElement method is called from the UI
     */
    @Test
    public void testDeleteElementString() {
        graphCommandProcessor.deleteElement("1", 1);
        assertTrue(commandController.getCommandQ().poll() instanceof GraphElementDeleteCommand);
    }

    /**
     * Tests if a NumberFormatException is thrown when the elementId is not a valid number
     */
    @Test(expected = NumberFormatException.class)
    public void testDeleteElementStringFailure() {
        graphCommandProcessor.deleteElement("notValid", 1);
    }

    /**
     * Clears the Processor and Controller
     */
    @AfterClass
    public static void free() {
        graphCommandProcessor = null;
        commandController = null;
    }
}
