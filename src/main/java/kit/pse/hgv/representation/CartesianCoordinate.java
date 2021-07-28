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
        double angle = 0;
        if (x == 0) {
            angle = y > 0 ? Math.PI*0.5 : Math.PI * 1.5;
        } else {
            angle = Math.atan(y/x) + (x < 0 ? Math.PI : 0);
        }
        return new PolarCoordinate(angle, distance);
    }

    @Override
    public double euclideanDistance(Coordinate coordinate) {
        double tempX = x - coordinate.toCartesian().getX();
        double tempY = y - coordinate.toCartesian().getY();
        return Math.sqrt(tempX * tempX + tempY + tempY);
    }

    @Override
    public double hyperbolicDistance(Coordinate coordinate) {
        return toPolar().hyperbolicDistance(coordinate);
    }

    public CartesianCoordinate mirroredThroughCenter() {
        return new CartesianCoordinate(-x,-y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public Coordinate moveCoordinate(Coordinate vector) {
        CartesianCoordinate coordinate = vector.toCartesian();
        return new CartesianCoordinate(x + coordinate.getX(), y + coordinate.getY());
    }

    @Override
    public Coordinate mirroredY() {
        return new CartesianCoordinate(x,-y);
    }

    /*public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }*/

    @Override
    public String toString() {
        return String.format("x: %f, y: %f");
    }
}
