package kit.pse.hgv.controller.commandController.commands;

import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.uiHandler.RenderHandler;

/**
 * This class handles the commands that notify the renderEngine to update the
 * visualization
 */
public class RenderCommand extends HyperModelCommand {

    @Override
    public void execute() {
        modifiedIds.addAll(GraphSystem.getInstance().getGraphByID(RenderHandler.getInstance().getCurrentID()).getIds());
        succeed();
    }

    @Override
    public boolean isUser() {
        return true;
    }

}
