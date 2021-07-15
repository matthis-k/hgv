package kit.pse.hgv.representation;

public class PolarCoordinate implements Coordinate{

    private double angle;
    private double distance;

    public PolarCoordinate (double angle, double distance) {
        this.angle = angle;
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
    public double euklidianDistance() {
        return 0;
    }
}
