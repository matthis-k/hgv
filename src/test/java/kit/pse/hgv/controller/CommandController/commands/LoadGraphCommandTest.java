package kit.pse.hgv.controller.CommandController.commands;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.LoadGraphCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

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
        Assert.assertFalse(loadGraphCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testNonExistentFile() {
        loadGraphCommand = new LoadGraphCommand("./noexistingdir/out.graphml");
        loadGraphCommand.execute();
        Assert.assertFalse(loadGraphCommand.getResponse().getBoolean("success"));
    }

    /**@Test
    public void testLoadGraph() {
        //TODO: Welche Datei nehmen?
        loadGraphCommand = new LoadGraphCommand("./Vorlage.graphml");
        loadGraphCommand.execute();
        Assert.assertTrue(loadGraphCommand.getResponse().getBoolean("success"));
    }**/

    //TODO: Wie Overflow exception erstellen?

    @AfterClass
    public static void free() {
        loadGraphCommand = null;
        //GraphSystem.getInstance().removeGraph(1);
    }
}
