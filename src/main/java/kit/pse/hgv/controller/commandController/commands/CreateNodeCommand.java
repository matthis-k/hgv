package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import kit.pse.hgv.representation.Coordinate;

public class CreateNodeCommand extends CreateElementCommand {
    private Coordinate coord;
    public CreateNodeCommand(int graphId, Coordinate coord) {
        super(graphId);
        this.coord = coord;
    }

    @Override
    public void execute() {
        try {
            addedId = GraphSystem.getInstance().addElement(graphId, coord);
        } catch (OverflowException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {

    }
}
