package kit.pse.hgv.view.hyperbolicModel;

import kit.pse.hgv.graphSystem.element.Edge;
import kit.pse.hgv.graphSystem.element.Node;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Coordinate;
import kit.pse.hgv.representation.LineStrip;

public interface Representation {

    /**
     * This method is used to get the Drawable object that represents the given
     * node.
     * 
     * @param node the Node to be represented
     * @return the Drawable object representing the Node
     */
    CircleNode calculate(Node node);

    /**
     * This method calculates the way the Edge should be represented, depending on
     * the current accuracy in the representation.
     * 
     * @param edge the Edge to be represented
     * @return The Drawable object representing the Edge
     */
    LineStrip calculate(Edge edge);

    void setAccuracy(Accuracy accuracy);

    void setCenter(Coordinate center);

    Accuracy getAccuracy();
}
