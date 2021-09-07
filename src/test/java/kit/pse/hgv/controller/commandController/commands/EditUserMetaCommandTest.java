package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.EditUserMetaCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.junit.*;

public class EditUserMetaCommandTest {

    private static CreateNodeCommand createNodeCommand;
    private static EditUserMetaCommand editUserMetaCommand;
    private static GraphSystem graphSystem;
    private static int nodeID;
    private static int graphId;

    @BeforeClass
    public static void setup() {
        graphId = GraphSystem.getInstance().newGraph();
        Coordinate coordinate = new CartesianCoordinate(1, 1);
        createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        createNodeCommand.execute();
        nodeID = createNodeCommand.getResponse().getInt("id");
        editUserMetaCommand = null;
        graphSystem = GraphSystem.getInstance();
    }

    @Test
    public void editColorSuccess() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "color", "red");
        editUserMetaCommand.execute();
        Assert.assertTrue(editUserMetaCommand.getResponse().getBoolean("success") && graphSystem.getNodeByID(nodeID).getMetadata("color").equals("red"));
    }

    @Test
    public void editColorFailure() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "color", "none");
        editUserMetaCommand.execute();
        Assert.assertFalse(editUserMetaCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void editPhiSuccess() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "phi", "1");
        editUserMetaCommand.execute();
        Assert.assertTrue(editUserMetaCommand.getResponse().getBoolean("success") && (graphSystem.getNodeByID(nodeID).getCoord().toPolar().getAngle() == 1));
    }

    @Test
    public void editPhiFailure() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "phi", "none");
        editUserMetaCommand.execute();
        Assert.assertFalse(editUserMetaCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void editRSuccess() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "r", "1");
        editUserMetaCommand.execute();
        Assert.assertTrue(editUserMetaCommand.getResponse().getBoolean("success") && (graphSystem.getNodeByID(nodeID).getCoord().toPolar().getDistance() == 1));
    }

    @Test
    public void editRFailure() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "r", "none");
        editUserMetaCommand.execute();
        Assert.assertFalse(editUserMetaCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void editOtherMeta() {
        editUserMetaCommand = new EditUserMetaCommand(nodeID, "someOther", "Meta");
        editUserMetaCommand.execute();
        Assert.assertTrue(editUserMetaCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testNotExistingNode() {
        editUserMetaCommand = new EditUserMetaCommand(-1, "color", "red");
        editUserMetaCommand.execute();
        Assert.assertFalse(editUserMetaCommand.getResponse().getBoolean("success"));
    }

    @After
    public void terminate() {
        editUserMetaCommand = null;
    }

    @AfterClass
    public static void free() {
        graphSystem.removeGraph(graphId);
        createNodeCommand = null;
    }
}
