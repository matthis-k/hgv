package kit.pse.hgv.representation;

public interface Coordinate {

    public CartesianCoordinate toCartesian();
    public PolarCoordinate toPolar();
    public double euclideanDistance(Coordinate coordinate);
    public double hyperbolicDistance(Coordinate coordinate);
    public Coordinate mirroredThroughCenter();
    public Coordinate mirroredY();
    public Coordinate moveCoordinate(Coordinate vector);
    @Override
    public String toString();
}
