package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.controller.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.RenderModel.RenderEngine;
import kit.pse.hgv.view.hyperbolicModel.Accuracy;

import java.nio.file.attribute.AttributeView;

public class SetAccuracyCommand extends HyperModelCommand {
    private Accuracy accuracy;
    public SetAccuracyCommand(Accuracy accuracy) {
        this.accuracy = accuracy;
    }
    @Override
    public void execute() {
        RenderEngine.getInstance().getDrawManager().setAccuracy(accuracy);
        modifiedIds.addAll(GraphSystem.getInstance().getAllIds());
        response.put("success", true);
    }

    @Override
    public void undo() {

    }
}
