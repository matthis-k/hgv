package kit.pse.hgv.controller.commandController;

import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.junit.*;


public class CommandControllerTest {

    private static CommandController commandController;

    /**
     * gets an instance of the command controller and starts it
     */
    @BeforeClass
    public static void setup() {
        commandController = CommandController.getInstance();
        commandController.start();
    }

    /**
     * clears the CommandQueue
     */
    @Before
    public void cleanQ() {
        commandController.getCommandQ().clear();
    }

    /**
     * Tests if the commandController is accessable
     */
    @Test
    public void testAccess() {
        Assert.assertNotNull(commandController = CommandController.getInstance());
    }

    /**
     * tests if the execution of the command controller works correctly
     *
     * @throws InterruptedException because the commandcontroller has to be stopped
     */
    @Test
    public void testExecution() throws InterruptedException {
        Coordinate test = new CartesianCoordinate(1, 2);
        CreateNodeCommand createNodeCommand = new CreateNodeCommand(1, test);
        commandController.queueCommand(createNodeCommand);
        synchronized (this) {
            wait(100);
        }
        Assert.assertTrue(commandController.getCommandQ().isEmpty());
    }

    /**
     * Stops the controller
     */
    @AfterClass
    public static void free() {
        commandController.stopController();
        commandController.interrupt();
        Object lock = new Object();
        synchronized (lock) {
            try {
                lock.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        commandController = null;
    }
}
