package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.App;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.CreateEdgeCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNewGraphCommand;
import kit.pse.hgv.controller.commandController.commands.CreateNodeCommand;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.representation.*;
import org.junit.*;

import java.util.Iterator;
import java.util.List;

public class CalculationTest {
    static int id = 1;
    static GraphSystem graphSystem = GraphSystem.getInstance();
    static Representation representation;
    static DrawManager drawManager;
    static int edgeId = 0;

    @BeforeClass
    public static void createEnvironment(){
        representation = new NativeRepresentation(0.1, Accuracy.HIGH);
        drawManager = new DrawManager(id, representation);
        id = graphSystem.loadGraph("src\\test\\resources\\testGraph.graphml");

    }

    @Test
    public void drawShortestEdge() {

        List<Drawable> rendered = drawManager.getRenderData();
        for(Drawable drawable : rendered) {
            if(drawable instanceof LineStrip) {
                LineStrip lineStrip = (LineStrip) drawable;
                Iterator<CartesianCoordinate> coordinates = lineStrip.getCoords().iterator();
                Coordinate coord1 = null;
                Coordinate coord2 = null;
                int connected [] = lineStrip.getConnectedNodes();
                double deltaReal = graphSystem.getNodeByID(connected[0]).getCoord().hyperbolicDistance(graphSystem.getNodeByID(connected[1]).getCoord());
                double deltaAprox = 0;
                if(coordinates.hasNext()) {
                    coord1 = coordinates.next();
                    coord2 = coord1;
                }
                while (coordinates.hasNext()) {
                    deltaAprox += coord1.hyperbolicDistance(coord2);
                    coord1 = coord2;
                    coord2 = coordinates.next();
                    Assert.assertTrue(Math.abs(coord1.hyperbolicDistance(coord2) - (deltaReal / 100.0)) < 0.1);

                }
               Assert.assertTrue(Math.abs(deltaAprox - deltaReal) < 0.1);
            }
        }
    }

    @Test
    public void calculateDirect() {
        drawManager.setAccuracy(Accuracy.DIRECT);
        List<Drawable> rendered = drawManager.getRenderData();
        for(Drawable drawable : rendered) {
            if(drawable instanceof LineStrip) {
                LineStrip lineStrip = (LineStrip) drawable;
                int nodes[] = lineStrip.getConnectedNodes();
                List<CartesianCoordinate> coordinates = lineStrip.getCoords();
                Coordinate coord1 = graphSystem.getNodeByID(nodes[0]).getCoord();
                Coordinate coord2 = graphSystem.getNodeByID(nodes[1]).getCoord();
                boolean goodCoords = (coord1.equals(coordinates.get(0).mirroredY()) || coord1.equals(coordinates.get(1).mirroredY())) &&
                        (coord2.equals(coordinates.get(0).mirroredY()) || coord2.equals(coordinates.get(1).mirroredY()));
                Assert.assertTrue(goodCoords);
                System.out.println(goodCoords + " Coord1 " + coord1.toString() + " Coord2 " + coord2.toString() + " Edge first " + coordinates.get(0).toPolar().mirroredY() + " Edge Second " + coordinates.get(1).toPolar().mirroredY());
                Assert.assertTrue(lineStrip.getCoords().size() == 2);
            }
        }
    }

    @Test
    public void conversionTest() {

        for(int i = 1; i < 1000; i++) {
            Coordinate coord1 = new CartesianCoordinate( i,0);
            Coordinate check1 = new PolarCoordinate(0, i);
            Coordinate coord2 = new CartesianCoordinate(0, i);
            Coordinate check2 = new PolarCoordinate(Math.PI / 2, i);

            Coordinate coord3 = new CartesianCoordinate( -i,0);
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
    public void distanceClose() {
        for(int j = 1; j <50; j++) {
            for(int i = 0; i < 10; i++) {
                PolarCoordinate coord1 = new PolarCoordinate(0,j);
                PolarCoordinate coord2 = new PolarCoordinate((1.0/Math.pow(10,i)), j);
                System.out.printf("distance %d: angular distance %6.3e: distance %6.3e\n", j, coord1.getAngularDistance(coord2), coord1.hyperbolicDistance(coord2));
            }
            System.out.println("###");
        }


    }

    @Test
    public void visualGraphTest() {
        String args[] = new String[1];
        App.main(args);
        CommandController commandController = CommandController.getInstance();
        commandController.queueCommand(new CreateNewGraphCommand());
        for(int i = 0; i < 50; i++) {
            Coordinate coordinate = new PolarCoordinate(i,i);
            Command command = new CreateNodeCommand(0,coordinate);
            commandController.queueCommand(command);
        }
        for(int i = 0; i < 50; i++) {
            int[] ids = {i,(i + 1) % 50};
            Command command = new CreateEdgeCommand(0,ids);
            commandController.queueCommand(command);
        }
        System.out.println("test");
    }

}
