package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.Coordinate;
import org.json.JSONArray;
import org.junit.*;


public class ExtensionCommandTest {

    private static GraphSystem graphSystem;
    private static int graphId;

    /**
     * Creates a new graph and gets the instance of the graphSystem
     */
    @BeforeClass
    public static void setup() {
        graphId = GraphSystem.getInstance().newGraph();
        graphSystem = GraphSystem.getInstance();
    }

    /**
     * Tests if the SendGraphCommand returns the right edges and nodes and if it returns true when it succeeds
     */
    @Test
    public void testSendGraphSuccess() {
        Coordinate coordinate =  new CartesianCoordinate(1, 1);
        Coordinate secondCoordinate =  new CartesianCoordinate(2, 2);
        CreateNodeCommand createNodeCommand = new CreateNodeCommand(graphId, coordinate);
        CreateNodeCommand createSecondNodeCommand = new CreateNodeCommand(graphId, secondCoordinate);
        createNodeCommand.execute();
        createSecondNodeCommand.execute();
        int[] n = {createNodeCommand.getResponse().getInt("id"), createSecondNodeCommand.getResponse().getInt("id")};
        CreateEdgeCommand createEdgeCommand = new CreateEdgeCommand(graphId, n);
        createEdgeCommand.execute();
        EditUserMetaCommand editUserMetaCommand = new EditUserMetaCommand(n[0], "color", "red");
        editUserMetaCommand.execute();
        SendGraphCommand sendGraphCommand = new SendGraphCommand(graphId);
        sendGraphCommand.execute();
        Assert.assertTrue(sendGraphCommand.getResponse().getBoolean("success"));
        JSONArray nodes = sendGraphCommand.getResponse().getJSONArray("nodes");
        JSONArray edges = sendGraphCommand.getResponse().getJSONArray("edges");
        if (nodes.getJSONObject(0).getInt("id") == n[0]) {
            Assert.assertEquals(nodes.getJSONObject(0).getInt("id"), n[0]);
            Assert.assertEquals(nodes.getJSONObject(0).getJSONObject("coordinate").getDouble("phi"), coordinate.toPolar().getAngle(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(0).getJSONObject("coordinate").getDouble("r"), coordinate.toPolar().getDistance(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(1).getInt("id"), n[1]);
            Assert.assertEquals(nodes.getJSONObject(1).getJSONObject("coordinate").getDouble("phi"), secondCoordinate.toPolar().getAngle(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(1).getJSONObject("coordinate").getDouble("r"), secondCoordinate.toPolar().getDistance(), 0.0);
            Assert.assertEquals(edges.getJSONObject(0).getInt("id"), createEdgeCommand.getResponse().getInt("id"));
            Assert.assertEquals(edges.getJSONObject(0).getInt("node1"), createNodeCommand.getResponse().getInt("id"));
            Assert.assertEquals(edges.getJSONObject(0).getInt("node2"), createSecondNodeCommand.getResponse().getInt("id"));
        } else {
            Assert.assertEquals(nodes.getJSONObject(1).getInt("id"), n[0]);
            Assert.assertEquals(nodes.getJSONObject(1).getJSONObject("coordinate").getDouble("phi"), coordinate.toPolar().getAngle(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(1).getJSONObject("coordinate").getDouble("r"), coordinate.toPolar().getDistance(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(0).getInt("id"), n[1]);
            Assert.assertEquals(nodes.getJSONObject(0).getJSONObject("coordinate").getDouble("phi"), secondCoordinate.toPolar().getAngle(), 0.0);
            Assert.assertEquals(nodes.getJSONObject(0).getJSONObject("coordinate").getDouble("r"), secondCoordinate.toPolar().getDistance(), 0.0);
            Assert.assertEquals(edges.getJSONObject(0).getInt("id"), createEdgeCommand.getResponse().getInt("id"));
            Assert.assertEquals(edges.getJSONObject(0).getInt("node1"), createNodeCommand.getResponse().getInt("id"));
            Assert.assertEquals(edges.getJSONObject(0).getInt("node2"), createSecondNodeCommand.getResponse().getInt("id"));
        }
    }

    /**
     * Tests if an exception is thrown when the graph doesn't exist
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSendGraphFailure() {
        SendGraphCommand sendGraphCommand = new SendGraphCommand(-1);
        sendGraphCommand.execute();
    }

    /**
     * removes the created graph
     */
    @AfterClass
    public static void free() {
        GraphSystem.getInstance().removeGraph(graphId);
    }
}
