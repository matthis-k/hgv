package com.pse.hgv.view.hyperbolicModel;

import com.pse.hgv.representation.*;
import javafx.scene.paint.Color;

public interface Representation {
    LineStrip calculateEdge(Edge edge);

    CircleNode calculate(Node node);
    LineStrip calculate(Edge edge);
    LineStrip calculate(Circle circle);
}
