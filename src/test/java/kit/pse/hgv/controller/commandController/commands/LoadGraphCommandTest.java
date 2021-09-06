package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.LoadGraphCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

@Ignore
public class LoadGraphCommandTest {

    private static LoadGraphCommand loadGraphCommand;

    @Before
    public void setup() {
        loadGraphCommand = null;
    }

    @Test
    public void testNoPath() {
        loadGraphCommand = new LoadGraphCommand("not existent");
        loadGraphCommand.execute();
        Assert.assertFalse(loadGraphCommand.succeeded());
    }

    @Test
    public void testNonExistentFile() {
        loadGraphCommand = new LoadGraphCommand("./noexistingdir/out.graphml");
        loadGraphCommand.execute();
        Assert.assertFalse(loadGraphCommand.succeeded());
    }

    /**
     * NOTE: needs javafx to be initialized
    @Test
    public void testLoadGraph() {
        loadGraphCommand = new LoadGraphCommand("./src/test/resources/Vorlage.graphml");
        loadGraphCommand.execute();
        Assert.assertTrue(loadGraphCommand.succeeded());
    }
     **/

    @AfterClass
    public static void free() {
        loadGraphCommand = null;
        //GraphSystem.getInstance().removeGraph(1);
    }
}
