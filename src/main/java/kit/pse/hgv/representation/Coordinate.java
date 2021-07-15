package kit.pse.hgv.representation;

public interface Coordinate {

    public CartesianCoordinate toCartesian();
    public PolarCoordinate toPolar();
    public double euklidianDistance();
}
