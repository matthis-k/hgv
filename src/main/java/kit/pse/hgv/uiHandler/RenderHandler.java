package kit.pse.hgv.uiHandler;

import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.representation.CircleNode;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.representation.LineStrip;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class RenderHandler implements UIHandler, Initializable {

    @FXML
    private AnchorPane renderPane;
    @FXML
    private Circle renderCircle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void generateGraph() {

        renderPane.getChildren().clear();
        Random random = new Random();
        double radius = 3;

        for(int i = 0; i < 5000; i ++){
            CircleNode start = new CircleNode(new CartesianCoordinate(random.nextInt(1280), random.nextInt(720)), radius, i, Color.CYAN);
            CircleNode end = new CircleNode(new CartesianCoordinate(random.nextInt(1280), random.nextInt(720)), radius, i, Color.CYAN);
            LineStrip line = new LineStrip(start, end, i, Color.BLACK);

            boolean firstNode = false;
            boolean secondNode = false;

            if(Math.sqrt(Math.pow(start.getCenter().getX() - 640, 2) + Math.pow(start.getCenter().getY() - 370, 2)) <= 315) {
                addChild(start);
                firstNode = true;
            }

            if(Math.sqrt(Math.pow(end.getCenter().getX() - 640, 2) + Math.pow(end.getCenter().getY() - 370, 2)) <= 315) {
                addChild(end);
                secondNode = true;
            }

            if(firstNode && secondNode)
                addChild(line);
        }
    }

    public void addChild(Drawable node) {
        node.draw(renderPane);
    }
}
