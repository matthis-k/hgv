package kit.pse.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.*;
import org.apache.commons.math3.analysis.function.Acosh;

import java.util.ArrayList;
import java.util.List;

public class NativeRepresentation implements Representation {

    private Coordinate center = new PolarCoordinate(0, 0);
    private double nodeSize;
    /**
     * this is the number of lines that is used to demonstrate one edge, any value
     * below 1 is invalid, the value 1 is for a direct line
     */
    private Accuracy accuracy;

    public NativeRepresentation(double nodeSize, Accuracy accuracy) {
        this.nodeSize = nodeSize;
        this.accuracy = accuracy;
    }

    @Override
    public CircleNode calculate(Node node, CircleNode circleNode) {
        CartesianCoordinate approximatedCoordinate = node.getCoord().toCartesian();
        if(!node.getCoord().equals(new PolarCoordinate(0,0))) {

            if (!center.equals(new PolarCoordinate(0, 0))) {
                double relativeAngel = node.getCoord().toPolar().getAngle();
                double relativeDistance = node.getCoord().toPolar().getDistance();
                approximatedCoordinate = center.toCartesian();
                double i = 1.0;
                boolean prematureExit = false;
                while (Math.abs(approximatedCoordinate.hyperbolicDistance(center) - relativeDistance) > 0.01 && !prematureExit) {
                    PolarCoordinate vector = new PolarCoordinate(relativeAngel, i);
                    boolean passedPoint = false;
                    while (approximatedCoordinate.hyperbolicDistance(center) > relativeDistance && !prematureExit) {
                        CartesianCoordinate temp = approximatedCoordinate.moveCoordinate(vector.mirroredThroughCenter()).toCartesian();
                        if(!Double.isNaN(temp.toPolar().getDistance()) && !Double.isNaN(temp.toPolar().getAngle())) {
                            approximatedCoordinate = temp;
                        } else {
                            prematureExit = true;
                        }
                        passedPoint = true;
                    }

                    while (approximatedCoordinate.hyperbolicDistance(center) < relativeDistance && !passedPoint && !prematureExit) {
                        CartesianCoordinate temp = approximatedCoordinate.moveCoordinate(vector).toCartesian();
                        if(!Double.isNaN(temp.toPolar().getDistance()) && !Double.isNaN(temp.toPolar().getAngle())) {
                            approximatedCoordinate = temp;
                        } else {
                            prematureExit = true;
                        }
                    }
                    i /= 2;
                }
            }
        } else {
            approximatedCoordinate = center.toCartesian();
        }

        //TODO
        //if(circleNode == null || circleNode.getID() != node.getId()) {
            return new CircleNode(approximatedCoordinate, nodeSize, node.getId(), GraphSystem.getInstance().getColorOfId(node.getId()));
        /*} else {
            circleNode.setCenter(approximatedCoordinate);
            return circleNode;
        }*/
    }

    @Override
    public LineStrip calculate(Edge edge, LineStrip lineStrip,Coordinate po1, Coordinate po2) {
        List<CartesianCoordinate> coordinates = new ArrayList<>();
        Coordinate vector = center.mirroredThroughCenter();
        PolarCoordinate point1 = po1.toPolar();
        PolarCoordinate point2 = po2.toPolar();
        if(point1.getDistance() == 0 || point2.getDistance() == 0 || point1.getAngle() == point2.getAngle()
                || point1.getAngle() == point2.mirroredThroughCenter().getAngle() ||accuracy == Accuracy.DIRECT) {
            coordinates.add(point1.mirroredY().toCartesian());
            coordinates.add(point2.mirroredY().toCartesian());
            return new LineStrip(coordinates, edge.getId(), GraphSystem.getInstance().getColorOfId(edge.getId()), edge.getNodes()[0].getId(),
                    edge.getNodes()[1].getId());
        }
        double angularDistance = point2.getAngle() - point1.getAngle();
        if ((angularDistance > 0.0 && angularDistance < Math.PI) || angularDistance < -Math.PI) {
            PolarCoordinate temp = point1;
            point1 = point2;
            point2 = temp;
        }
        PolarCoordinate point1Temp = null;
        if (accuracy != Accuracy.DIRECT) {
            if (point1.getDistance() > 9) {
                point1Temp = point1;
                point1 = new PolarCoordinate(point1.getAngle(), 9);
            }

            if (point2.getDistance() > 9) {
                coordinates.add(point2.mirroredY().toCartesian());
                point2 = new PolarCoordinate(point2.getAngle(), 9);
            }
        }
        coordinates.addAll(coordinatesForShortestLine(point2, point1));
        if (point1Temp != null) {
            coordinates.add(point1Temp.mirroredY().toCartesian());
        }

        return new LineStrip(coordinates, edge.getId(), GraphSystem.getInstance().getColorOfId(edge.getId()), edge.getNodes()[0].getId(), edge.getNodes()[1].getId());

            //lineStrip.setCoordinates(coordinates);
            //Color color = edge.getMetadata("color") == null || edge.getMetadata("color").equals(lineStrip.getColor()) ? lineStrip.getColor() : Color.web(edge.getMetadata("color"));
            //lineStrip.setColor(color);
            //Node[] nodes = edge.getNodes();
            //int [] ids = {nodes[0].getId(), nodes[1].getId()};
            //lineStrip.setConecting(ids);
            //return  lineStrip;


    }

    private List<CartesianCoordinate> coordinatesForShortestLine(PolarCoordinate point1, PolarCoordinate point2) {
        List<CartesianCoordinate> coordinates = new ArrayList<>();
        double renderDetail = accuracy.getAccuracy();

        double angularDistance = point2.getAngle() - point1.getAngle();
        if ((angularDistance > 0.0 && angularDistance < Math.PI) || angularDistance < -Math.PI) {
            PolarCoordinate temp = point1;
            point1 = point2;
            point2 = temp;
        }
        double p1r = point1.getDistance();
        double p2r = point2.getDistance();
        double p2phi = point2.getAngle();
        coordinates.add(point2.mirroredY().toCartesian());
        // aproximation beacuse calulation fails when points have r > 10
        double distance = point1.hyperbolicDistance(point2);

        double cosGamma2 = 0.0;

        if (Math.sinh(p2r) * Math.sinh(distance) != 0) {
            double first = Math.cosh(p2r) * Math.cosh(distance);
            first -= Math.cosh(p1r);
            double second = Math.sinh(p2r) * Math.sinh(distance);
            cosGamma2 = first / second;
        }
        if (cosGamma2 == Double.NaN) {
            cosGamma2 = 0;
        }
        Acosh acosh = new Acosh();
        double tempr = 0;
        double tempGamma = 0;
        for (int i = 0; i <= renderDetail; i++) {
            double partialDistance = distance * (i / renderDetail);
            double r = 0.0;
            r = acosh.value((Math.cosh(p2r) * Math.cosh(partialDistance)
                    - (Math.sinh(p2r) * Math.sinh(partialDistance) * cosGamma2)));
            if (Double.isNaN(r)) {
                r = 0;
            }
            double gammaPrime = 0.0;
            if (Math.sinh((r) * Math.sinh(p2r)) != 0) {
                double first = Math.cosh(r) * Math.cosh(p2r);
                first -= Math.cosh(partialDistance);
                double second = Math.sinh(r) * Math.sinh(p2r);
                double temp = first / second;
                gammaPrime = Math.acos(temp);
            }
            if (Double.isNaN(gammaPrime)) {
                gammaPrime = 0;

            }
            tempGamma = gammaPrime;
            tempr = r;
            double phi = p2phi + gammaPrime;
            PolarCoordinate nativeLinePoint = new PolarCoordinate(phi, r);
            coordinates.add(nativeLinePoint.mirroredY().toCartesian());
        }
        if(!coordinates.get(coordinates.size() - 1).equals(point1.mirroredY())) {
            coordinates.add(point1.mirroredY().toCartesian());
        }
        return coordinates;
    }

    @Override
    public void setCenter(Coordinate center) {
        this.center = center;
    }

    @Override
    public void setAccuracy(Accuracy accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public Accuracy getAccuracy() {
        return this.accuracy;
    }

    @Override
    public Coordinate getCenter() {
        return this.center;
    }
}
