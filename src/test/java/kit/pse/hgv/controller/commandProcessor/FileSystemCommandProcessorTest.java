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

    @Before
    public void setup() {
        fileSystemCommandProcessor = new FileSystemCommandProcessor();
        commandController = CommandController.getInstance();
        commandController.getCommandQ().clear();
        graphId = -1;
    }

    @Test
    public void testLoadGraph() {
        fileSystemCommandProcessor.loadGraph(new File("test.graphml"));
        assertTrue(commandController.getCommandQ().poll() instanceof LoadGraphCommand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadGraphWrongFile() {
        fileSystemCommandProcessor.loadGraph(new File("test.png"));
    }

    @Test
    public void testSaveGraph() {
        graphId = GraphSystem.getInstance().newGraph();
        fileSystemCommandProcessor.saveGraph("./src/test/resources/out.graphml", graphId);
        assertTrue(commandController.getCommandQ().poll() instanceof SaveGraphCommand);
    }

    @Test
    public void testCreateNewGraph() {
        fileSystemCommandProcessor.createNewGraph();
        assertTrue(commandController.getCommandQ().poll() instanceof CreateNewGraphCommand);
    }

    @Test
    public void testShutdown() {
        fileSystemCommandProcessor.shutdown();
        assertTrue(commandController.getCommandQ().poll() instanceof ShutdownCommand);
    }

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
