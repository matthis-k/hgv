package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.App;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.CommandQListener;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.Graph;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.*;
import org.junit.*;

import javax.sound.sampled.Line;
import java.util.*;

public class CalculationTest {
    static int id = 1;
    static GraphSystem graphSystem = GraphSystem.getInstance();
    static Representation representation;
    static DrawManager drawManager;
    static int edgeId = 0;

    @Before
    public void createEnvironment(){
        representation = new NativeRepresentation(0.1, Accuracy.HIGH);
        id = graphSystem.loadGraph("src\\main\\resources\\spiralGraph.graphml");
        drawManager = new DrawManager(id, representation);
    }

    @After
    public void free() {
        representation = null;
        drawManager = null;
        graphSystem.removeGraph(id);
    }


    @Test
    public void drawShortestHighEdge() {
        drawManager.setAccuracy(Accuracy.HIGH);
        List<Drawable> rendered = drawManager.getRenderData();
        for (Drawable drawable : rendered) {
            if (drawable instanceof LineStrip) {
                LineStrip lineStrip = (LineStrip) drawable;
                Iterator<CartesianCoordinate> coordinates = lineStrip.getCoords().iterator();
                Coordinate coord1 = null;
                Coordinate coord2 = null;
                int connected[] = lineStrip.getConnectedNodes();
                double deltaReal = graphSystem.getNodeByID(connected[0]).getCoord()
                        .hyperbolicDistance(graphSystem.getNodeByID(connected[1]).getCoord());
                double deltaAprox = 0;
                if (coordinates.hasNext()) {
                    coord1 = coordinates.next();
                    coord2 = coord1;
                }
                int i = 0;
                while (coordinates.hasNext()) {
                    coord1 = coord2;
                    coord2 = coordinates.next();
                    deltaAprox += coord1.hyperbolicDistance(coord2);
                    if(Math.abs(coord1.hyperbolicDistance(coord2) - (deltaReal /
                            (lineStrip.getCoords().size() - 1))) > 0.1) {
                    }
                    i++;
                }
               Assert.assertTrue(Math.abs(deltaAprox - deltaReal) < 0.1);
            }
        }
    }


    @Test
    public void conversionTest() {

        for (int i = 1; i < 1000; i++) {
            Coordinate coord1 = new CartesianCoordinate(i, 0);
            Coordinate check1 = new PolarCoordinate(0, i);
            Coordinate coord2 = new CartesianCoordinate(0, i);
            Coordinate check2 = new PolarCoordinate(Math.PI / 2, i);

            Coordinate coord3 = new CartesianCoordinate(-i, 0);
            Coordinate check3 = new PolarCoordinate(Math.PI, i);
            Coordinate coord4 = new CartesianCoordinate(0, -i);
            Coordinate check4 = new PolarCoordinate(3 * Math.PI / 2, i);
            String eq = "equals";
            String mir = "mirrored";
            String dmir = "mirrored twice";
            Assert.assertEquals(eq, coord1, check1);
            Assert.assertEquals(eq, coord2, check2);
            Assert.assertEquals(eq, coord3, check3);
            Assert.assertEquals(eq, coord4, check4);
            Assert.assertEquals(mir, coord1.mirroredThroughCenter(), check3);
            Assert.assertEquals(mir, coord2.mirroredThroughCenter(), check4);
            Assert.assertEquals(mir, coord3.mirroredThroughCenter(), check1);
            Assert.assertEquals(mir, coord4.mirroredThroughCenter(), check2);
            Assert.assertEquals(dmir + "coord1", coord1, coord1.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "coord2", coord2, coord2.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "coord3", coord3, coord3.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "coord3", coord4, coord4.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "check1", check1, check1.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "check2", check2, check2.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "check3", check3, check3.mirroredThroughCenter().mirroredThroughCenter());
            Assert.assertEquals(dmir + "check4", check4, check4.mirroredThroughCenter().mirroredThroughCenter());
        }

    }

    @Test
    public void createsAllElements() {
        List<Drawable> rendered = drawManager.getRenderData();
        List<Integer> ids = graphSystem.getIDs(id);
        for(Drawable drawable: rendered) {
            Integer id = drawable.getID();
            Assert.assertTrue(ids.contains(id));
            ids.remove(id);
        }
        Assert.assertTrue(ids.isEmpty());
    }

    @Test
    public void changesRightElements() {
        List<Drawable> firstRender = drawManager.getRenderData();
        List<double[]> renderedCoords = new ArrayList<>();
        for(Drawable drawable : firstRender) {
            double add[] = new double[3];
            if(drawable instanceof CircleNode) {
                add[0] = 0;
                add[1] = ((CircleNode) drawable).getCenter().toPolar().getDistance();
                add[2] = ((CircleNode) drawable).getCenter().toPolar().getAngle();
            } else {
                add[0] = 1;
                add[1] = ((LineStrip) drawable).getConnectedNodes()[0];
                add[2] = ((LineStrip) drawable).getConnectedNodes()[1];
            }
            renderedCoords.add(add);
        }
        List<Integer> ids = graphSystem.getIDs(id);
        List<Integer> changedGraph = new ArrayList<>();
        List<Integer> changedRendered = new ArrayList<>();
        int smallestID = getSmallestID(id);
        for(int i = smallestID; i < ((ids.size() > 11)? smallestID + 11 : smallestID + ids.size()); i++) {
            int elementId = ids.get(i - getSmallestID(id));
            GraphElement element = graphSystem.getGraphElementByID(id, elementId);
            if(element instanceof Node) {
                Node node = (Node) element;
                PolarCoordinate coord = node.getCoord().moveCoordinate((new CartesianCoordinate(1,1)).toPolar()).toPolar();
                ICommand command = new MoveNodeCommand(i,coord);
                command.execute();
                if(i % 2 == 0) changedRendered.add(elementId);
            } else {
                graphSystem.removeElement(elementId);
            }
            changedGraph.add(elementId);

        }

        List<Drawable> secondRender = drawManager.getRenderData(changedRendered);
        for(Integer j: changedGraph){
            int i = j - smallestID;
            if(secondRender.contains(j)) {
                assert !firstRender.get(i).equals(secondRender.get(i));
            } else {
                assert firstRender.get(i).equals(secondRender.get(i));
            }

        }
        for(int i = 0; i < firstRender.size(); i++) {
            if(!changedGraph.contains(i)) {
                try {
                    assert firstRender.get(i).equals(secondRender.get(i));
                } catch (AssertionError e) {
                    if(firstRender.get(i) instanceof CircleNode) {

                    } else {

                    }
                }
            }
        }

    }

    @Ignore
    @Test
    public void moveCoordinate() {
        PolarCoordinate coord = new PolarCoordinate(1.0, 1.0);
        CartesianCoordinate newCoord = coord.moveCoordinate(new PolarCoordinate(2,0.1)).toCartesian();
        System.out.println(newCoord + " : " + coord.toCartesian() + ":" + new PolarCoordinate(Math.PI / 2 + 1, 1.0).toCartesian());
    }

    @Test
    public void testMove(){
        for(Integer i : graphSystem.getIDs(id)) {
            if(graphSystem.getGraphElementByID(i) instanceof Node) {
                Node node = graphSystem.getNodeByID(i);
                double phi = node.getCoord().toPolar().getAngle();
                double r = node.getCoord().toPolar().getDistance();
                int id = node.getId();
                ICommand command = new MoveNodeCommand(id, (new CartesianCoordinate(1, 1)).toPolar());
                command.execute();
                double newPhi = graphSystem.getNodeByID(id).getCoord().toPolar().getAngle();
                double newR = graphSystem.getNodeByID(id).getCoord().toPolar().getDistance();
                double dPhi = phi - newPhi < newPhi - phi? phi - newPhi : newPhi - phi;

            }
        }
        int smallestID = getSmallestNodeID(id);
        int largestID = getLargestNodeID(id);
        for(int i = smallestID; i < largestID; i++) {
            int j = i >= largestID ? smallestID : i + 1;
            int[] ids = {i,j};
            Command command = new CreateEdgeCommand(id,ids);
            command.execute();

        }

    }

    @Test
    public void moveCenterTest() {
        double min = 0;
        double max = 9;
        Random r = new Random();
        double r1 = 2 * Math.PI * r.nextDouble();
        double r2 = 9 * r.nextDouble();
        Coordinate center = new PolarCoordinate(r1,r2);
        drawManager.moveCenter(center);
        List<Drawable> render = drawManager.getRenderData();
        for(Drawable drawable: render) {
            if(drawable instanceof CircleNode) {
                assert Math.abs(((CircleNode) drawable).getCenter().hyperbolicDistance(drawManager.getCenter()) -
                        graphSystem.getNodeByID(drawable.getID()).getCoord().toPolar().getDistance()) < 0.01;
            }
        }

    }

    @Test
    public void calculateDirect() {
        drawManager.setAccuracy(Accuracy.DIRECT);
        List<Drawable> drawables = drawManager.getRenderData();
        for(Drawable drawable : drawables) {
            if(drawable instanceof LineStrip) {
                Edge edge = graphSystem.getEdgeByID(drawable.getID());
                LineStrip lineStrip = (LineStrip) drawable;
                assert ((edge.getNodes()[0].getId() == lineStrip.getConnectedNodes()[0] || edge.getNodes()[0].getId() ==
                        lineStrip.getConnectedNodes()[1]) && (edge.getNodes()[1].getId() ==
                        lineStrip.getConnectedNodes()[1] || edge.getNodes()[0].getId() ==
                        lineStrip.getConnectedNodes()[0]));
                Coordinate first = lineStrip.getCoords().get(0).mirroredY();
                Coordinate second = lineStrip.getCoords().get(lineStrip.getCoords().size() - 1).mirroredY();
                assert (first.equals(edge.getNodes()[0].getCoord()) || first.equals(edge.getNodes()[1].getCoord())) && (second.equals(edge.getNodes()[0].getCoord()) || second.equals(edge.getNodes()[1].getCoord()));
            }
        }

    }

    private int getSmallestID(int id) {
        int smallestID = Integer.MAX_VALUE;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i < smallestID) smallestID = i;
        }
        return smallestID;
    }

    private int getLargestID(int id) {
        int largestID = 0;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i > largestID) largestID = i;
        }
        return largestID;
    }

    private int getLargestNodeID(int id) {
        int largestID = 0;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i > largestID && graphSystem.getGraphElementByID(i) instanceof Node) largestID = i;
        }
        return largestID;
    }

    private int getLargestEdgeID(int id) {
        int largestID = 0;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i > largestID && graphSystem.getGraphElementByID(i) instanceof Edge) largestID = i;
        }
        return largestID;
    }

    private int getSmallestNodeID(int id) {
        int smallestID = Integer.MAX_VALUE;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i < smallestID && graphSystem.getGraphElementByID(i) instanceof Node) smallestID = i;
        }
        return smallestID;
    }

    private int getSmallestEdgeID(int id) {
        int smallestID = Integer.MAX_VALUE;
        for(Integer i: graphSystem.getIDs(id)) {
            if(i < smallestID && graphSystem.getGraphElementByID(i) instanceof Edge) smallestID = i;
        }
        return smallestID;
    }


}


