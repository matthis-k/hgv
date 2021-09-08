package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles the commands that notify the renderEngine to update the
 * visualization
 */
public class RenderCommand extends HyperModelCommand {

    @Override
    public void execute() {
        succeed();
    }

    @Override
    public boolean isUser() {
        return true;
    }

}
