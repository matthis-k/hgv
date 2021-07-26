package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;

public class EditUserMetaCommand extends MetaSystemCommand{
    private double angle;
    private double distance;

    public EditUserMetaCommand(double angle, double distance, int elementId){
        this.angle = angle;
        this.distance = distance;
        this.elementId = elementId;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getDistance() {
        return this.distance;
    }

    @Override
    public void execute() {
        GraphSystem.getInstance().getGraphElementByID(elementId).setMetadata(metaKey, metaVal);
        
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
        
    }

}
