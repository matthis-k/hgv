package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileSystemCommandProcessorTest {

    private static FileSystemCommandProcessor fileSystemCommandProcessor;

    @Before
    public void setup() {
        fileSystemCommandProcessor = new FileSystemCommandProcessor();
        CommandController.getInstance().getCommandQ().clear();
    }

    @Test
    public void testLoadGraph() {
        fileSystemCommandProcessor.loadGraph(new File("test.graphml"));
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof LoadGraphCommand);
    }

    @Test
    public void testSaveGraph() {
        fileSystemCommandProcessor.saveGraph("./src/test/resources/out.graphml", 1);
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof SaveGraphCommand);
    }

    @Test
    public void testCreateNewGraph() {
        fileSystemCommandProcessor.createNewGraph();
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof CreateNewGraphCommand);
    }

    @Test
    public void testShutdown() {
        fileSystemCommandProcessor.shutdown();
        assertTrue(CommandController.getInstance().getCommandQ().poll() instanceof ShutdownCommand);
    }

    @After
    public void free() {
        fileSystemCommandProcessor = null;
        CommandController.getInstance().getCommandQ().clear();
    }
}
