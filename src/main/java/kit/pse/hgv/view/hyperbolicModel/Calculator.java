package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Calculator extends Thread{

    private int graphId;
    private HashSet<Integer> ids;
    DrawManager drawManager;

    protected Calculator(DrawManager drawManager, HashSet<Integer> ids, int graphId) {
        this.drawManager = drawManager;
        this.graphId = graphId;
        this.ids = ids;
    }

    @Override
    public void run() {
        for(Integer id : ids) {
            if(GraphSystem.getInstance().getGraphElementByID(graphId, id) == null) {
                drawManager.removeDrawable(id);
                continue;
            }
            Drawable d;
            Node node = GraphSystem.getInstance().getNodeByID(graphId, id);
            if (node != null) {
                CircleNode circleNode = (CircleNode) drawManager.getDrawable(id);
                d = drawManager.getRepresentation().calculate(node, circleNode);
            } else {
                Edge edge = GraphSystem.getInstance().getEdgeByID(graphId, id);
                LineStrip lineStrip = (LineStrip) drawManager.getDrawable(id);
                Coordinate coordinates[] = new Coordinate[2];
                if(lineStrip != null) {
                    coordinates[0] = ((CircleNode)drawManager.getDrawable(lineStrip.getConnectedNodes()[0])).getCenter();
                    coordinates[1] = ((CircleNode)drawManager.getDrawable(lineStrip.getConnectedNodes()[1])).getCenter();
                } else {
                    coordinates[0] = edge.getNodes()[0].getCoord();
                    coordinates[1] = edge.getNodes()[1].getCoord();
                }
                d = drawManager.getRepresentation().calculate(edge, lineStrip, coordinates[0], coordinates[1]);
            }
            drawManager.setRendered(d);
        }
    }
}
