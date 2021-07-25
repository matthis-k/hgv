package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.Command;
import kit.pse.hgv.controller.commandController.commands.*;

public class GraphCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }

    public void addEdge(int graphId, String coordinate1, String coordinate2){

    }

    public void addNode(int graphId, String coordinate){

    }
    
    public void moveNode(int elementId){
        MoveNodeCommand command = new MoveNodeCommand(elementId);
    }

    public void deleteElement(int elementId){
        GraphElementDeleteCommand command = new GraphElementDeleteCommand(elementId);
    }
}
