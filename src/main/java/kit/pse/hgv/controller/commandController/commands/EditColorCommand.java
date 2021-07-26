package kit.pse.hgv.controller.commandController.commands;
import javafx.scene.paint.Color;

public class EditColorCommand extends MetaSystemCommand{
    private Color color;

    public EditColorCommand(int elementId, Color color){
        this.color = color;
        this.elementId = elementId;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }
    
}
