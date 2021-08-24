package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO
 */
public class SendGraphCommand extends ExtensionCommand {
    private int graphId;
    private int clientId;

    public SendGraphCommand(int graphId) {
        this.graphId = graphId;
    }

    @Override
    public void execute() {
        if (GraphSystem.getInstance().getGraphByID(graphId) == null) {
            throw new IllegalArgumentException("No Graph with that Id exists.");
        }
        Graph g = GraphSystem.getInstance().getGraphByID(graphId);
        JSONArray nodes = new JSONArray();
        for (Node node : g.getNodes()) {
            JSONObject jsonNode = new JSONObject();
            jsonNode.put("id", node.getId());
            JSONObject coord = new JSONObject();
            coord.put("phi", node.getCoord().toPolar().getAngle());
            coord.put("r", node.getCoord().toPolar().getDistance());
            jsonNode.put("coordinate", coord);
            nodes.put(jsonNode);
            // TODO: Metadata
        }
        JSONArray edges = new JSONArray();
        for (Edge edge : g.getEdges()) {
            JSONObject jsonEdge = new JSONObject();
            jsonEdge.put("id", edge.getId());
            jsonEdge.put("node1", edge.getNodes()[0].getId());
            jsonEdge.put("node2", edge.getNodes()[1].getId());
            edges.put(jsonEdge);
            // TODO: Metadata
        }
        response.put("success", true);
        response.put("nodes", nodes);
        response.put("edges", edges);
    }

    @Override
    public void undo() {

    }
}
