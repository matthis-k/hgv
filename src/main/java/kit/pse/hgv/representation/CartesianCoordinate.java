package kit.pse.hgv.representation;

public class CartesianCoordinate implements Coordinate {

    private final double x;
    private final double y;

    /**
     * Creates a new Cartesian Coordinate
     *
     * @param x x-Coordinate of a Cartesian Coordinate
     * @param y y-Coordinate of a Cartesian Coordinate
     */
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
        double distance = Math.sqrt(x * x + y * y);
        double angle = 0;
        if (x == 0) {
            angle = y > 0 ? Math.PI * 0.5 : Math.PI * 1.5;
        } else {
            angle = Math.atan(y / x) + (x < 0 ? Math.PI : 0);
        }
        return new PolarCoordinate(angle, distance);
    }

    @Override
    public double euclideanDistance(Coordinate coordinate) {
        double tempX = x - coordinate.toCartesian().getX();
        double tempY = y - coordinate.toCartesian().getY();
        return Math.sqrt(tempX * tempX + tempY * tempY);
    }

    @Override
    public double hyperbolicDistance(Coordinate coordinate) {
        return toPolar().hyperbolicDistance(coordinate);
    }

    /**
     * Reflects the coordinate over the x- and y-axis
     *
     * @return reflection of the coordinate
     */
    public CartesianCoordinate mirroredThroughCenter() {
        return new CartesianCoordinate(-x, -y);
    }

    /**
     * Returns the x-coordinate of the Cartesian Coordinate
     *
     * @return x-Coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of the Cartesian Coordinate
     *
     * @return y-Coordinate
     */
    public double getY() {
        return this.y;
    }

    @Override
    public Coordinate moveCoordinate(Coordinate vector) {
        CartesianCoordinate coordinate = vector.toCartesian();
        if (coordinate.getX() == 0 && coordinate.getY() == 0) {
            return this;
        }
        return new CartesianCoordinate(x + coordinate.getX(), y + coordinate.getY());
    }

    @Override
    public Coordinate mirroredY() {
        return new CartesianCoordinate(x, -y);
    }

    @Override
    public String toString() {
        return String.format("x: %f, y: %f", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }
        CartesianCoordinate coordinate = ((Coordinate) o).toCartesian();
        double deltaX = Math.abs(x - coordinate.getX());
        double deltaY = Math.abs(y - coordinate.getY());
        double conversionError = 1.0 / 1000000.0;
        boolean res = deltaX < conversionError & deltaY < conversionError;
        return res;
    }
}
