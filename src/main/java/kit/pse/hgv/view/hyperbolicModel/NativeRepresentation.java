package kit.pse.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.GraphElement;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.*;

import java.util.ArrayList;
import java.util.List;

public class NativeRepresentation implements Representation {

    private Coordinate center = new PolarCoordinate(0, 0);
    private double nodeSize = 0.1;
    /**
     * this is the number of lines that is used to demonstrate one edge,
     * any value below 1 is invalid, the value 1 is for a direct line
     */
    private int accuracy = 100;

    public NativeRepresentation() {

    }

    public NativeRepresentation(double nodeSize) {
        this.nodeSize = nodeSize;
    }

    public NativeRepresentation(Coordinate center) {
        this.center = center;
    }

    public NativeRepresentation(int accuracy) {
        this.accuracy = accuracy;
    }

    public NativeRepresentation(int accuracy, Coordinate center) {
        this.accuracy = accuracy;
        this.center = center;
    }

    public NativeRepresentation(double nodeSize, int accuracy) {
        this.nodeSize = nodeSize;
        this.accuracy = accuracy;
    }

    public NativeRepresentation(double nodeSize, Coordinate center) {
        this.nodeSize = nodeSize;
        this.center = center;
    }

    public NativeRepresentation(double nodeSize, int accuracy, Coordinate center) {
        this.nodeSize = nodeSize;
        this.accuracy = accuracy;
        this.center = center;
    }


    @Override
    public CircleNode calculate(Node node) {
        System.out.println("node");
        //TODO Philipp Node.getCoordinate : Coordinate
       /* return new CircleNode(node.getCoordinate().toCartesian(), nodeSize, node.getId(),
                Color.valueOf(node.getMetadata("Color").toUpperCase()));*/
        return null;
    }

    @Override
    public LineStrip calculate(Edge edge) {
        System.out.println("edge");
        //TODO Philipp
        PolarCoordinate firstNode = null; //edge.getNodes()[0].getCoordinate().toPolar();
        firstNode.moveCoordinate(center.mirroredThroughCenter());
        //TODO Philipp
        PolarCoordinate secondNode = null; //edge.getNodes()[1].getCoordinate().toPolar();
        secondNode.moveCoordinate(center.mirroredThroughCenter());
        List<Coordinate> line = new ArrayList<>();
        if (firstNode.getDistance() == 0 || secondNode.getDistance() == 0 || firstNode.getAngle() ==
                secondNode.getAngle()) {
            line.add(firstNode);
            line.add(secondNode);
            return new LineStrip(line, edge.getId(), Color.valueOf(edge.getMetadata("Color").toUpperCase()));
        }
        double angularDistance = firstNode.getAngle() - secondNode.getAngle();
        if ((angularDistance > 0.0 && angularDistance < Math.PI) || (angularDistance < -Math.PI)) {
            PolarCoordinate temp = firstNode;
            firstNode = secondNode;
            secondNode = temp;
        }

        double distance = firstNode.hyperbolicDistance(secondNode);

        double cosGamma2 = 0.0;

        if (Math.sinh(secondNode.getDistance()) * Math.sinh(distance) != 0) {
            cosGamma2 = (((Math.cosh(secondNode.getDistance() * Math.cosh(distance)) -
                    Math.cosh(firstNode.getDistance()) / (Math.sinh(secondNode.getDistance()) * Math.sinh(distance)))));
        }

        for (int i = 0; i <= accuracy; i++) {
            double partial_distance = distance * (i / (double) accuracy);
            double temp = Math.cosh(secondNode.getDistance() * Math.cosh(partial_distance) -
                    (Math.sinh(secondNode.getDistance() * Math.sinh(partial_distance) * cosGamma2)));
            double radius = acosh(temp) != -1 ? acosh(temp) : 0.0;
            temp = Math.sinh(radius) * Math.sinh(secondNode.getDistance()) != 0 ? (Math.cosh(radius) *
                    Math.cosh(secondNode.getDistance()) - Math.cosh(partial_distance)) /
                    (Math.sinh(radius) * Math.sinh(secondNode.getDistance())) : -1.0;
            double gammaPrime = acosh(temp) != -1 ? acosh(temp) : 0.0;
            double phi = secondNode.getAngle() + gammaPrime;
            PolarCoordinate nativeLinePoint = (new PolarCoordinate(phi, radius));
            nativeLinePoint.moveCoordinate(center);
            line.add(nativeLinePoint);
        }

        return new LineStrip(line, edge.getId(), Color.valueOf(edge.getMetadata("Color").toUpperCase()));
    }

    @Override
    public void setCenter(Coordinate center) {
        this.center = center;
    }

   @Override
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
   }

    private double acosh(double x) {
        //TODO
        //check whether x is valid, else return -1(invalid result)
        if (x < 1) {
            return -1;
        }
        double res = Math.log(x + Math.sqrt(x * x - 1));
        //check whether the result is valid if so, return the result, else return -1
        return res < 0 ? -1 : res;
    }
}
