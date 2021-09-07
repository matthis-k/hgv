package kit.pse.hgv.representation;

import org.junit.Test;

public class CoordinateTests {
    @Test
    public void createCoordinate() {
        double x = 10 * Math.random();
        double y = 10 * Math.random();
        Coordinate coordinate = new CartesianCoordinate(x, y);
        assert coordinate.toCartesian().getX() == x;
        assert coordinate.toCartesian().getY() == y;

        double r = 10 * Math.random();
        double phi = Math.PI * 2 * Math.random();
        coordinate = new PolarCoordinate(phi, r);
        assert coordinate.toPolar().getAngle() == phi;
        assert coordinate.toPolar().getDistance() == r;
    }

    @Test
    public void conversionTest() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.0,0);
        PolarCoordinate polarCoordinate = new PolarCoordinate(0, 1.0);
        assert cartesianCoordinate.equals(polarCoordinate);
        assert Math.abs(cartesianCoordinate.toPolar().getDistance() - polarCoordinate.getDistance())<= 0.000001;
        assert Math.abs(cartesianCoordinate.toPolar().getAngle() - polarCoordinate.getAngle()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getX() - cartesianCoordinate.getX()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getY() - cartesianCoordinate.getY()) <= 0.000001;
        cartesianCoordinate = new CartesianCoordinate(0, 1.0);
        polarCoordinate = new PolarCoordinate(Math.PI / 2, 1.0);
        assert cartesianCoordinate.equals(polarCoordinate);
        assert Math.abs(cartesianCoordinate.toPolar().getDistance() - polarCoordinate.getDistance())<= 0.000001;
        assert Math.abs(cartesianCoordinate.toPolar().getAngle() - polarCoordinate.getAngle()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getX() - cartesianCoordinate.getX()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getY() - cartesianCoordinate.getY()) <= 0.000001;
        cartesianCoordinate = new CartesianCoordinate(-1.0, 0);
        polarCoordinate = new PolarCoordinate(Math.PI, 1.0);
        assert cartesianCoordinate.equals(polarCoordinate);
        assert Math.abs(cartesianCoordinate.toPolar().getDistance() - polarCoordinate.getDistance())<= 0.000001;
        assert Math.abs(cartesianCoordinate.toPolar().getAngle() - polarCoordinate.getAngle()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getX() - cartesianCoordinate.getX()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getY() - cartesianCoordinate.getY()) <= 0.000001;
        cartesianCoordinate = new CartesianCoordinate(0, -1.0);
        polarCoordinate = new PolarCoordinate(3 * Math.PI / 2, 1.0);
        assert cartesianCoordinate.equals(polarCoordinate);
        assert Math.abs(cartesianCoordinate.toPolar().getDistance() - polarCoordinate.getDistance())<= 0.000001;
        assert Math.abs(cartesianCoordinate.toPolar().getAngle() - polarCoordinate.getAngle()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getX() - cartesianCoordinate.getX()) <= 0.000001;
        assert Math.abs(polarCoordinate.toCartesian().getY() - cartesianCoordinate.getY()) <= 0.000001;
        assert !polarCoordinate.equals(new String());
        assert !cartesianCoordinate.equals(new String());
    }

    @Test
    public void distanceTest() {
        Coordinate first = new CartesianCoordinate(5,5);
        Coordinate second = new CartesianCoordinate(0,0);
        assert (first.euclideanDistance(second) - Math.sqrt(50)) <= 0.000001;
        first = new PolarCoordinate(0,0);
        assert Math.abs(first.euclideanDistance(second)) <= 0.000001;
        double distance = 10 * Math.random();
        second = new PolarCoordinate(5, distance);
        assert Math.abs(first.hyperbolicDistance(second) - distance) <= 0.00001;

    }

    @Test
    public void moveCoordinate() {
        PolarCoordinate first  = new PolarCoordinate(0,0);
        Coordinate second = new CartesianCoordinate(10, 10);
        Coordinate third = new CartesianCoordinate(20, 20);
        Coordinate res = first.moveCoordinate(second);
        assert res.equals(second);
        res = second.moveCoordinate(first);
        assert res.equals(second);
        assert first.moveCoordinate(first).equals(first);
        assert second.moveCoordinate(second).equals(third);
    }

    @Test
    public void toStringTest() {
        double x = 10 * Math.random();
        double y = 10 * Math.random();
        Coordinate coordinate = new CartesianCoordinate(x,y);
        assert coordinate.toString().equals(String.format("x: %f, y: %f", x, y));
        double r = 10 * Math.random();
        double phi = Math.PI * 2 * Math.random();
        coordinate = new PolarCoordinate(phi, r);
        assert coordinate.toString().equals(String.format("r: %f, phi: %f", r, phi));
    }

}
