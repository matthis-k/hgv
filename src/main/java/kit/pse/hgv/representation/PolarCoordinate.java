package kit.pse.hgv.representation;

public class PolarCoordinate implements Coordinate{

    public static final double MAX_ANGLE = 2 * Math.PI;
    private double angle;
    private double distance;

    public PolarCoordinate (double angle, double distance) {
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
    
    @Override
    public double hyperbolicDistance(Coordinate coordinate) {
        double angle1 = coordinate.toPolar().getAngle();
        double distance1 = coordinate.toPolar().getDistance();
        double deltaAngle = Math.min(Math.abs(angle - angle1), MAX_ANGLE - Math.abs(angle - angle1));
        //check for division by -1, when it occurs set to -1(invalid distance)
        double hyperbolicDistance = Math.cos(deltaAngle) < 1 ? distance + distance1 -
                Math.log(2/(1-Math.cos(deltaAngle))) : -1;
        return hyperbolicDistance;
    }

    public Coordinate moveCoordinate(Coordinate vector) {
        return toCartesian().moveCoordinate(vector);
    }

    @Override
    public Coordinate mirroredY() {
        return new PolarCoordinate(Math.PI * 2 - angle, distance);
    }

    public PolarCoordinate mirroredThroughCenter() {
        return new PolarCoordinate(angle + Math.PI, distance);
    }

    public double getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("r: %f, phi: %f", distance, angle);
    }
}
