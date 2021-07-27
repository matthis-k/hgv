package kit.pse.hgv.controller.commandController.commands;

public abstract  class CreateElementCommand extends GraphSystemCommand {
    protected int addedId = -1;
    protected CreateElementCommand(int graphId) {
        super(graphId);
    }
    public int getAddedId() {
        return addedId;
    }
}
