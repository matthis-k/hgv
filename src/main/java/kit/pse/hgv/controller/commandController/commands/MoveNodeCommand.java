package kit.pse.hgv.controller.commandController.commands;

public class MoveNodeCommand extends GraphSystemCommand {
    private int elementId;
    // TODO add coordinate when its available

    public MoveNodeCommand(int elementId){
        this.elementId = elementId;
    }

    @Override
    public void execute(){
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
}
