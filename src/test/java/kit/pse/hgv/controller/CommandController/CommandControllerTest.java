package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.junit.*;


public class CommandControllerTest {

    private static CommandController commandController;

    @BeforeClass
    public static void setup() {
        commandController = CommandController.getInstance();
        commandController.start();
    }

    @Before
    public void cleanQ() {
        commandController.getCommandQ().clear();
    }

    @Test
    public void testAccess() {
        Assert.assertNotNull(commandController = CommandController.getInstance());
    }

    @Test
    public void testExecution() throws InterruptedException {
        Coordinate test = new CartesianCoordinate(1, 2);
        CreateNewGraphCommand createNewGraphCommand = new CreateNewGraphCommand();
        CreateNodeCommand createNodeCommand = new CreateNodeCommand(1, test);
        commandController.queueCommand(createNewGraphCommand);
        commandController.queueCommand(createNodeCommand);
        synchronized (this) {
            wait(100);
        }
        Assert.assertTrue(commandController.getCommandQ().isEmpty());
    }

    @After
    public void deleteGraph() {
        GraphSystem.getInstance().removeGraph(1);
    }

    @AfterClass
    public static void free() {
        commandController.stopController();
        commandController = null;
    }
}
