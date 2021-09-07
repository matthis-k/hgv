package kit.pse.hgv.representation;

public interface Coordinate {

    /**
     * Converts a given Coordinate into a Cartesian Coordinate
     *
     * @return the converted Cartesian Coordinate
     */
    CartesianCoordinate toCartesian();

    /**
     * Converts a given Coordinate into a Polar Coordinate
     *
     * @return the converted Polar Coordinate
     */
    PolarCoordinate toPolar();

    /**
     * Calculates the euclidean distance of a given Coordinate
     *
     * @param coordinate the coordinate where the euclidean distance should be
     *                   calculated
     * @return the calculated euclidean distance
     */
    double euclideanDistance(Coordinate coordinate);

    /**
     * Calculates the hyperbolic distance of a given
     *
     * @param coordinate the coordinate where the hyperbolic distance should be
     *                   calculated
     * @return the calculated hyperbolic distance
     */
    double hyperbolicDistance(Coordinate coordinate);

    /**
     * Calculates the reflection of the Coordinate over the x- and y-axis
     *
     * @return the reflexion of the Coordinate over the x- and y-axis
     */
    Coordinate mirroredThroughCenter();

    /**
     * Calculates the reflection of the Coordinate over the y-axis
     *
     * @return the reflexted coordinate
     */
    Coordinate mirroredY();

    /**
     * Moves the coordinate with the given vector
     *
     * @param vector where the coordinate should be moved
     * @return moved coordinate
     */
    Coordinate moveCoordinate(Coordinate vector);

    @Override
    String toString();
}
