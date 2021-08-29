package kit.pse.hgv.controller.CommandController;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.junit.*;

public class CommandControllerTest {

    private static CommandController commandController;

    @BeforeClass
    public static void setup() {
        commandController = null;
    }

    @Test
    public void testAccess() {
        Assert.assertNotNull(commandController = CommandController.getInstance());
    }

    @Test
    public void testExecution() {
        commandController = CommandController.getInstance();
        Coordinate test = new CartesianCoordinate(1, 2);
        CreateNewGraphCommand createNewGraphCommand = new CreateNewGraphCommand();
        CreateNodeCommand createNodeCommand = new CreateNodeCommand(1, test);
        commandController.queueCommand(createNewGraphCommand);
        commandController.queueCommand(createNodeCommand);
        commandController.run();
        commandController.stopController();
        Assert.assertTrue(commandController.getCommandQ().isEmpty());
    }

    @AfterClass
    public static void free() {
        commandController = null;
    }
}
