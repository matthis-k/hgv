package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.representation.CartesianCoordinate;
import kit.pse.hgv.controller.commandController.commands.*;

public class GraphCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        //TODO CommandController.getInstance().queueCommand(command);
    }

    public void addEdge(int graphId, int id1, int id2){
        int[] nodeIds = new int[2];
        nodeIds[1] = id1;
        nodeIds[2] = id2;
        CreateEdgeCommand command = new CreateEdgeCommand(graphId, nodeIds);
        queueCommand(command);
    }

    public void addNode(int graphId, String coordinate){
        //TODO
    }
    
    public void moveNode(int elementId, String coordinateAsString){
        //TODO how implemented in the UI?
        //Double x;
        //Double y;
        //CartesianCoordinate coordinate = new CartesianCoordinate(x, y);
        //MoveNodeCommand command = new MoveNodeCommand(elementId, coordinate);
        //queueCommand(command);
    }

    public void deleteElement(int elementId){
        GraphElementDeleteCommand command = new GraphElementDeleteCommand(elementId);
        queueCommand(command);
    }
}
