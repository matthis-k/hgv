package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileSystemCommandProcessorTest {

    private static FileSystemCommandProcessor fileSystemCommandProcessor;
    private static int graphId;
    private static CommandController commandController;

    /**
     * Initializes all necessary classes for the tests
     */
    @Before
    public void setup() {
        fileSystemCommandProcessor = new FileSystemCommandProcessor();
        commandController = CommandController.getInstance();
        commandController.getCommandQ().clear();
        graphId = -1;
    }

    /**
     * Tests if the LoadGraphCommand is created and queued if the loadGraph method is called from the UI
     */
    @Test
    public void testLoadGraph() {
        fileSystemCommandProcessor.loadGraph(new File("test.graphml"));
        assertTrue(commandController.getCommandQ().poll() instanceof LoadGraphCommand);
    }

    /**
     * Tests if the loadGraph Method throws an IllegalArgumentException if the file is not in the .graphml format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLoadGraphWrongFile() {
        fileSystemCommandProcessor.loadGraph(new File("test.png"));
    }

    /**
     * Tests if the SaveGraphCommand is created and queued if the saveGraph method is called from the UI
     */
    @Test
    public void testSaveGraph() {
        graphId = GraphSystem.getInstance().newGraph();
        fileSystemCommandProcessor.saveGraph("./src/test/resources/out", graphId);
        assertTrue(commandController.getCommandQ().poll() instanceof SaveGraphCommand);
    }

    /**
     * Tests if the CreateNewGraph is created and queued if the createNewGraph method is called from the UI
     */
    @Test
    public void testCreateNewGraph() {
        fileSystemCommandProcessor.createNewGraph();
        assertTrue(commandController.getCommandQ().poll() instanceof CreateNewGraphCommand);
    }

    /**
     * Tests if the ShutdownCommand is created and queued if the shutdown method is called from the UI
     */
    @Test
    public void testShutdown() {
        fileSystemCommandProcessor.shutdown();
        assertTrue(commandController.getCommandQ().poll() instanceof ShutdownCommand);
    }

    /**
     * Clears the necessary classes from the tests
     */
    @After
    public void free() {
        fileSystemCommandProcessor = null;
        if(!(graphId == -1)) {
            GraphSystem.getInstance().removeGraph(graphId);
        }
        commandController.getCommandQ().clear();
        commandController = null;
    }
}
