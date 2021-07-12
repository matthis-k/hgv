package com.pse.hgv.representation;

public class CartesianCoordinate implements Coordinate {

    private double x;
    private double y;

    public CartesianCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public CartesianCoordinate toCartesian() {
        return null;
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
