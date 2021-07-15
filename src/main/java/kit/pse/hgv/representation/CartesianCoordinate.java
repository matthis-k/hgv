package kit.pse.hgv.representation;

public class CartesianCoordinate implements Coordinate {

    private double x;
    private double y;

    public CartesianCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public CartesianCoordinate toCartesian() {
        return this;
    }

    @Override
    public PolarCoordinate toPolar() {
        double distance = Math.sqrt(x*x + y*y);
        double angle = Math.atan(y/x);
        return new PolarCoordinate(angle, distance);
    }

    @Override
    public double euklidianDistance() {
        return 0;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
