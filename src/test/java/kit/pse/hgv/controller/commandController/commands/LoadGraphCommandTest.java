package kit.pse.hgv.controller.commandController.commands;

import org.junit.*;

@Ignore
public class LoadGraphCommandTest {

    private static LoadGraphCommand loadGraphCommand;

    /**
     * clears the loadGraphCommand
     */
    @Before
    public void setup() {
        loadGraphCommand = null;
    }

    /**
     * Tests if the LoadGraphCommand success is false when the path doesn't exist
     */
    @Test
    public void testNoPath() {
        loadGraphCommand = new LoadGraphCommand("not existent");
        loadGraphCommand.execute();
        Assert.assertFalse(loadGraphCommand.succeeded());
    }

    /**
     * Tests if the LoadGraphCommand success is false when the file doesn't exist
     */
    @Test
    public void testNonExistentFile() {
        loadGraphCommand = new LoadGraphCommand("./noexistingdir/out.graphml");
        loadGraphCommand.execute();
        Assert.assertFalse(loadGraphCommand.succeeded());
    }

    /**
     * Clears the laodGraphCommand
     */
    @AfterClass
    public static void free() {
        loadGraphCommand = null;
    }
}
