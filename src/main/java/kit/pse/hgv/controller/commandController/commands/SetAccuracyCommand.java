package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;

public class SetAccuracyCommand extends HyperModelCommand {
    private Accuracy accuracy;

    public SetAccuracyCommand(String accuracy) {

        this.accuracy = Accuracy.valueOf(accuracy.toUpperCase());
    }

    @Override
    public void execute() {
        RenderEngine.getInstance().getDrawManager().setAccuracy(accuracy);
        modifiedIds.addAll(GraphSystem.getInstance().getAllIds());
    }

    @Override
    public void undo() {

    }
}
