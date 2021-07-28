package kit.pse.hgv.view.hyperbolicModel;

import javafx.scene.paint.Color;
import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.LineStrip;
import kit.pse.hgv.representation.PolarCoordinate;
import org.apache.commons.math3.analysis.function.Acosh;

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
        //TODO Philipp Node.getCoordinate : Coordinate
        return new CircleNode(node.getCoord().toCartesian(), nodeSize, node.getId(),
                null);
    }

    @Override
    public LineStrip calculate(Edge edge) {
        List<Coordinate> coordinates = new ArrayList<>();
        double renderDetail = accuracy.getAccuracy();
        PolarCoordinate point1 = edge.getNodes()[0].getCoord().toPolar();
        PolarCoordinate point2 = edge.getNodes()[1].getCoord().toPolar();
        if(point1.getDistance() == 0 || point2.getDistance() == 0 || point1.getAngle() == point2.getAngle()
                || point1.getAngle() == point2.mirroredThroughCenter().getAngle()) {
            coordinates.add(point1);
            coordinates.add(point2);
            Color color = edge.getMetadata("color") != null? Color.web(edge.getMetadata("color")) : Color.BLACK;
            return new LineStrip(coordinates, edge.getId(), color);
        }

        double angularDistance = point2.getAngle() - point1.getAngle();
        if((angularDistance >0.0 && angularDistance < Math.PI) || angularDistance < -Math.PI) {
            PolarCoordinate temp = point1;
            point1 = point2;
            point2 = temp;
        }
        double p1r = point1.getDistance();
        double p2r = point2.getDistance();
        double p2phi = point2.getAngle();

        double distance = point1.hyperbolicDistance(point2);

        double cosGamma2 = 0.0;

        if(Math.sinh(p2r) * Math.sinh(distance) != 0) {
            double first = Math.cosh(p2r) * Math.cosh(distance);
            first -= Math.cosh(p1r);
            double second = Math.sinh(p2r) * Math.sinh(distance);
            cosGamma2 = first/second;
        }
        if(cosGamma2 == Double.NaN) {
            cosGamma2 = 0;
        }
        Acosh acosh = new Acosh();
        for(int i = 0; i <= renderDetail; i++) {
            double partialDistance = distance * (i / renderDetail);
            double r = 0.0;
            r = acosh.value((Math.cosh(p2r) * Math.cosh(partialDistance) - (Math.sinh(p2r) * Math.sinh(partialDistance) * cosGamma2)));
            if(Double.isNaN(r)) {
                r = 0;
            }
            double gammaPrime = 0.0;
            if(Math.sinh((r) * Math.sinh(p2r)) != 0) {
                double first = Math.cosh(r) * Math.cosh(p2r);
                first -= Math.cosh(partialDistance);
                double second = Math.sinh(r) * Math.sinh(p2r);
                double temp = first / second;
                gammaPrime = Math.acos(temp);
            }
            if(Double.isNaN(gammaPrime)) {
                gammaPrime = 0;
            }
            double phi = p2phi+gammaPrime;
            PolarCoordinate nativeLinePoint = new PolarCoordinate(phi,r);
            coordinates.add(nativeLinePoint);
        }

    coordinates.add(point1);
        Color color = edge.getMetadata("color") != null ? Color.web(edge.getMetadata("color")) : Color.BLACK;
        return new LineStrip(coordinates,edge.getId(), color);

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
}
