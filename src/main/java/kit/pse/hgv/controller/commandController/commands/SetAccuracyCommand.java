package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;
import kit.pse.hgv.view.uiHandler.RenderHandler;

public class SetAccuracyCommand extends HyperModelCommand {
    private final Accuracy accuracy;

    public SetAccuracyCommand(String accuracy) {
        this.accuracy = Accuracy.valueOf(accuracy.toUpperCase());
    }

    @Override
    public void execute() {
        RenderHandler.getInstance().updateAccuracy(accuracy);
        modifiedIds.addAll(GraphSystem.getInstance().getAllIds());
    }
}
