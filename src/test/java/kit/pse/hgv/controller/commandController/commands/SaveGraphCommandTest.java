package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

public class SaveGraphCommandTest {

    private static SaveGraphCommand saveGraphCommand;
    private static int graphId;

    /**
     * creates a new Graph for the tests
     */
    @BeforeClass
    public static void start() {
        graphId = GraphSystem.getInstance().newGraph();
    }

    /**
     * clears the saveGraphCommand
     */
    @Before
    public void setup() {
        saveGraphCommand = null;
    }

    /**
     * Tests if the SaveGraphCommand works correctly and if it returns true then it suceeds
     */
    @Test
    public void testSaveGraphSuccess() {
        saveGraphCommand = new SaveGraphCommand(graphId, "./src/test/resources/out");
        saveGraphCommand.execute();
        Assert.assertTrue(saveGraphCommand.getResponse().getBoolean("success"));
    }

    /**
     * removes the created graph and clears the saveGraphCommand
     */
    @AfterClass
    public static void free() {
        GraphSystem.getInstance().removeGraph(graphId);
        saveGraphCommand = null;
    }
}
