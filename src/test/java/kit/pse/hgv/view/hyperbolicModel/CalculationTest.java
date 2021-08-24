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
        drawManager = new DrawManager(id, representation);
        id = graphSystem.loadGraph("src\\main\\resources\\spiralGraph.graphml");
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
                System.out.println(graphSystem.getNodeByID(connected[0]).getCoord() + ":" + graphSystem.getNodeByID(connected[1]).getCoord());
                while (coordinates.hasNext()) {
                    coord1 = coord2;
                    coord2 = coordinates.next();
                    deltaAprox += coord1.hyperbolicDistance(coord2);
                    if(Math.abs(coord1.hyperbolicDistance(coord2) - (deltaReal /
                            (lineStrip.getCoords().size() - 1))) > 0.1) {
                        System.out.println(coord1.hyperbolicDistance(coord2) + ":" + deltaReal /
                                (lineStrip.getCoords().size() - 1) + ":" + i + ":" + graphSystem.getNodeByID(connected[0]).getCoord() + ":" + graphSystem.getNodeByID(connected[1]).getCoord());
                    }
                    i++;
                }
               Assert.assertTrue(Math.abs(deltaAprox - deltaReal) < 0.1);
            }
        }
    }

    @Test
    public void calculateDirect() {
        drawManager.setAccuracy(Accuracy.DIRECT);
        List<Drawable> rendered = drawManager.getRenderData();
        for (Drawable drawable : rendered) {
            if (drawable instanceof LineStrip) {
                LineStrip lineStrip = (LineStrip) drawable;
                int nodes[] = lineStrip.getConnectedNodes();
                List<CartesianCoordinate> coordinates = lineStrip.getCoords();
                Coordinate coord1 = graphSystem.getNodeByID(nodes[0]).getCoord();
                Coordinate coord2 = graphSystem.getNodeByID(nodes[1]).getCoord();
                boolean goodCoords = (coord1.equals(coordinates.get(0).mirroredY())
                        || coord1.equals(coordinates.get(1).mirroredY()))
                        && (coord2.equals(coordinates.get(0).mirroredY())
                                || coord2.equals(coordinates.get(1).mirroredY()));
                Assert.assertTrue(goodCoords);
                Assert.assertTrue(lineStrip.getCoords().size() == 2);
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
        List<Integer> ids = graphSystem.getIDs(id);
        List<Integer> changedGraph = new ArrayList<>();
        List<Integer> changedRendered = new ArrayList<>();
        for(int i = 1; i < ((ids.size() > 11)? 11 : ids.size()); i++) {
            int elementId = ids.get(i);
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
        List<Drawable> secondRender = drawManager.getRenderData();
        for(Integer i:changedGraph){

            System.out.println(((CircleNode) firstRender.get(i)).getCenter().toPolar() + " : " + ((CircleNode) secondRender.get(i)).getCenter().toPolar() + " : " + graphSystem.getNodeByID(i).getCoord());


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
        for(int i = 0; i < 1000; i++) {
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
                System.out.printf("%f : %f : %f : %f : %f : %f \n", phi, r,  newPhi, newR, (phi + 1) % (Math.PI * 2), r - newR);
            }
        }
        for(int i = 0; i < 50; i++) {
            int[] ids = {i,(i + 1) % 50};
            Command command = new CreateEdgeCommand(0,ids);
            commandController.queueCommand(command);
        }
        System.out.println("test");
    }
}


