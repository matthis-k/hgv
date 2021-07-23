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
    private Accuracy accuracy = Accuracy.DIRECT;

    public NativeRepresentation() {

    }

    public NativeRepresentation(double nodeSize) {
        this.nodeSize = nodeSize;
    }

    public NativeRepresentation(Coordinate center) {
        this.center = center;
    }

    public NativeRepresentation(Accuracy accuracy) {
        this.accuracy = accuracy;
    }

    public NativeRepresentation(Accuracy accuracy, Coordinate center) {
        this.accuracy = accuracy;
        this.center = center;
    }

    public NativeRepresentation(double nodeSize, Accuracy accuracy) {
        this.nodeSize = nodeSize;
        this.accuracy = accuracy;
    }

    public NativeRepresentation(double nodeSize, Coordinate center) {
        this.nodeSize = nodeSize;
        this.center = center;
    }

    public NativeRepresentation(double nodeSize, Accuracy accuracy, Coordinate center) {
        this.nodeSize = nodeSize;
        this.accuracy = accuracy;
        this.center = center;
    }


    @Override
    public CircleNode calculate(Node node) {
        System.out.println("node");
        //TODO Philipp Node.getCoordinate : Coordinate
        return new CircleNode(node.getCoordinate().toCartesian(), nodeSize, node.getId(),
                Color.valueOf(node.getMetadata("Color").toUpperCase()));
    }

    @Override
    public LineStrip calculate(Edge edge) {
        System.out.println("edge");
        //TODO Philipp
        PolarCoordinate firstNode = edge.getNodes()[0].getCoordinate().toPolar();
        firstNode.moveCoordinate(center.mirroredThroughCenter());
        //TODO Philipp
        PolarCoordinate secondNode = edge.getNodes()[1].getCoordinate().toPolar();
        secondNode.moveCoordinate(center.mirroredThroughCenter());
        List<Coordinate> line = new ArrayList<>();
        if (firstNode.getDistance() == 0 || secondNode.getDistance() == 0 || firstNode.getAngle() ==
                secondNode.getAngle() || accuracy.getAccuracy() == 1) {
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

        for (int i = 0; i < accuracy.getAccuracy(); i++) {
            double partial_distance = distance * (i / (double) accuracy.getAccuracy());
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

    private List<Double> distribution(double rad1, double rad2) {
        int factor = 100 / accuracy.getAccuracy();
        List<Double> res = new ArrayList<>();
        for(int i = 0; i < accuracy.getAccuracy(); i++) {
            res.add(distributionValue(rad1,rad2,i * factor));
        }
        return res;
    }

    private double distributionValue(double rad1, double rad2, int part) {
        if(rad2 == 0 || rad1 == 0) {
            return 1/(part+1);
        }
        double moveCenter = rad1/rad2 > 1 ? -50*(1+rad2/rad1): 50*(1+rad1/rad2);
        double toPower = (part/50.0) + moveCenter;
        double res = (0.5*Math.pow(toPower,3)) + 0.5;
        return res;
    }

    @Override
    public void setCenter(Coordinate center) {
        this.center = center;
    }

   @Override
    public void setAccuracy(Accuracy accuracy) {
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
