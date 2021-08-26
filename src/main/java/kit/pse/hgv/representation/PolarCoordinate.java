package kit.pse.hgv.representation;

import org.apache.commons.math3.analysis.function.Acosh;

public class PolarCoordinate implements Coordinate{

    public static final double MAX_ANGLE = 2 * Math.PI;
    private double angle;
    private double distance;

    /**
     * Creates a new polar Coordinate
     * 
     * @param angle
     * @param distance
     */
    public PolarCoordinate(double angle, double distance) {
        this.angle = angle % MAX_ANGLE;
        while (this.angle < 0) {
            this.angle += MAX_ANGLE;
        }
        this.distance = distance;
    }

    @Override
    public CartesianCoordinate toCartesian() {
        double x = Math.cos(angle) * distance;
        double y = Math.sin(angle) * distance;
        return new CartesianCoordinate(x, y);
    }

    @Override
    public PolarCoordinate toPolar() {
        return this;
    }

    @Override
    public double euclideanDistance(Coordinate coordinate) {
        return toCartesian().euclideanDistance(coordinate);
    }

    public double getAngularDistance(Coordinate coordinate) {
        double angle1 = coordinate.toPolar().getAngle();
        double res = Math.min(Math.abs(angle - angle1), MAX_ANGLE - Math.abs(angle - angle1));
        return res;
    }

    @Override
    public double hyperbolicDistance(Coordinate coordinate) {
        if(equals(coordinate)) return 0.0;
        double distance1 = coordinate.toPolar().getDistance();
        double deltaAngle = getAngularDistance(coordinate);
        Acosh acosh = new Acosh();
        double temp = Math.cosh(distance) * Math.cosh(distance1) - Math.sinh(distance) * Math.sinh(distance1) * Math.cos(deltaAngle);
        double hyperbolicDistance = acosh.value(temp);
        return hyperbolicDistance;
    }

    /**
     * moves the coordinate with the vector
     *
     * @param vector where the coordinate should be moved
     * @return moved coordinate
     */
    public Coordinate moveCoordinate(Coordinate vector) {
        if(vector.toPolar().getDistance() == 0) {
            return this;
        }
        CartesianCoordinate cartesianVector = vector.toCartesian();
        return cartesianVector.moveCoordinate(this);
    }

    @Override
    public Coordinate mirroredY() {
        return new PolarCoordinate(Math.PI * 2 - angle, distance);
    }

    /**
     * Reflects the coordinate over the x- and y-axis
     *
     * @return Reflection of the coordinate
     */
    public PolarCoordinate mirroredThroughCenter() {
        return new PolarCoordinate(angle + Math.PI, distance);
    }

    /**
     * returns the angle of the given polar coordinate
     *
     * @return angle of the given polar coordinate
     */
    public double getAngle() {
        return angle;
    }

    /**
     * returns the distance of the given polar coordinate
     *
     * @return distance of the given polar coordinate
     */
    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("r: %f, phi: %f", distance, angle);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Coordinate)) {
            return false;
        }
        PolarCoordinate coordinate = ((Coordinate) o).toPolar();
        double deltaPhi = Math.min(Math.abs(angle - coordinate.getAngle()), MAX_ANGLE - Math.abs(angle - coordinate.getAngle()));
        double deltaR = Math.abs(distance - coordinate.getDistance());
        double conversionError = 1.0 / 1000000.0;
        boolean res = deltaPhi < conversionError & deltaR < conversionError;
        return res;
    }
}
