package kit.pse.hgv.controller.CommandController.commands;

import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.SaveGraphCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.*;

public class SaveGraphCommandTest {

    private static SaveGraphCommand saveGraphCommand;

    @BeforeClass
    public static void start() {
        CreateNewGraphCommand createNewGraphCommand = new CreateNewGraphCommand();
        createNewGraphCommand.execute();
    }

    @Before
    public void setup() {
        saveGraphCommand = null;
    }

    @Test
    public void testSaveGraphSuccess() {
        saveGraphCommand = new SaveGraphCommand(1, "./out.graphml");
        saveGraphCommand.execute();
        Assert.assertTrue(saveGraphCommand.getResponse().getBoolean("success"));
    }

    /**
    @Test
    public void testSaveGraphFailure() {
        saveGraphCommand = new SaveGraphCommand(2, "not existent");
        saveGraphCommand.execute();
        System.out.println(saveGraphCommand.getResponse().toString());
        //Assert.assertFalse(saveGraphCommand.getResponse().getBoolean("success"));
    }**/

    @AfterClass
    public static void free() {
        GraphSystem.getInstance().removeGraph(1);
        saveGraphCommand = null;
    }
}
