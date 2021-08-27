package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;

import java.util.List;

public class Calculator extends Thread{

    private int graphId;
    private List<Integer> ids;
    DrawManager drawManager;

    protected Calculator(DrawManager drawManager, List<Integer> ids, int graphId) {
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
                Node temp[] = edge.getNodes();
                d = drawManager.getRepresentation().calculate(edge, lineStrip, temp[0].getCoord(), temp[1].getCoord());
            }
            drawManager.setRendered(d);
        }
    }
}
