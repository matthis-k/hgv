package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.MoveNodeCommand;
import kit.pse.hgv.controller.commandController.commands.GraphElementDeleteCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.PolarCoordinate;
import org.junit.*;

public class GraphSystemCommandTest {

    private static CreateNodeCommand createNodeCommand;
    private static CreateEdgeCommand createEdgeCommand;
    private static MoveNodeCommand moveNodeCommand;
    private static GraphElementDeleteCommand graphElementDeleteCommand;
    private static Coordinate coordinate;
    private static int graphId;

    @Before
    public void startup() {
        graphId = GraphSystem.getInstance().newGraph();
    }

    @Test
    public void testCreateEdgeSuccess() {
        coordinate = new CartesianCoordinate(1, 1);
        Coordinate coordinateSecond = new CartesianCoordinate(2, 2);
        createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        createNodeCommand.execute();
        CreateNodeCommand createSecondNodeCommand = new CreateNodeCommand(graphId, coordinateSecond);
        createSecondNodeCommand.execute();
        int[] nodeIds = {createNodeCommand.getResponse().getInt("id"), createSecondNodeCommand.getResponse().getInt("id")};
        createEdgeCommand = new CreateEdgeCommand(graphId, nodeIds);
        createEdgeCommand.execute();
        Assert.assertTrue(createEdgeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testCreateEdgeFailure() {
        int[] nodeIds = {14, 19};
        createEdgeCommand = new CreateEdgeCommand(graphId, nodeIds);
        createEdgeCommand.execute();
        Assert.assertFalse(createEdgeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testCreateNodeSuccess() {
        coordinate = new CartesianCoordinate(1, 2);
        createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        createNodeCommand.execute();
        Assert.assertTrue(createNodeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testCreateNodeFailure() {
        coordinate = new CartesianCoordinate(4, 10);
        createNodeCommand = new CreateNodeCommand(graphId+1, coordinate);
        createNodeCommand.execute();
        Assert.assertFalse(createNodeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testMoveNodeSuccess() {
        coordinate = new CartesianCoordinate(3, 4);
        createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        createNodeCommand.execute();
        PolarCoordinate coordinateToMove = new PolarCoordinate(2, 5);
        moveNodeCommand = new MoveNodeCommand(createNodeCommand.getResponse().getInt("id"), coordinateToMove);
        moveNodeCommand.execute();
        Assert.assertTrue(moveNodeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testMoveNodeFailure() {
        PolarCoordinate coordinateToMove = new PolarCoordinate(2, 5);
        moveNodeCommand = new MoveNodeCommand(-1, coordinateToMove);
        moveNodeCommand.execute();
        Assert.assertFalse(moveNodeCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testDeleteNodeSuccess() {
        Coordinate coordinate = new CartesianCoordinate(2, 6);
        createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        createNodeCommand.execute();
        graphElementDeleteCommand = new GraphElementDeleteCommand(createNodeCommand.getResponse().getInt("id"));
        graphElementDeleteCommand.execute();
        Assert.assertTrue(graphElementDeleteCommand.getResponse().getBoolean("success"));
    }

    @Test
    public void testDeleteNodeFailure() {
        graphElementDeleteCommand = new GraphElementDeleteCommand(-1);
        graphElementDeleteCommand.execute();
        Assert.assertFalse(graphElementDeleteCommand.getResponse().getBoolean("success"));

    }

    @After
    public void terminate() {
        graphElementDeleteCommand = null;
        moveNodeCommand = null;
        createEdgeCommand = null;
        createNodeCommand = null;
        coordinate = null;
        GraphSystem.getInstance().removeGraph(graphId);
    }

    @AfterClass
    public static void free() {
    }
}
