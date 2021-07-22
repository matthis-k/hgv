package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.*;

public class GraphCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }

    public void addEdge(int graphId, String coordinate1, String coordinate2){
        GraphElementCreateCommand command = new GraphElementCreateCommand(graphId);
    }

    public void addNode(int graphId, String coordinate){
        GraphElementCreateCommand command = new GraphElementCreateCommand(graphId);
    }
    
    public void moveNode(int elementId){
        GraphElementMoveCommand command = new GraphElementMoveCommand(elementId);
    }

    public void deleteElement(int elementId){
        GraphElementDeleteCommand command = new GraphElementDeleteCommand(elementId);
    }
}
