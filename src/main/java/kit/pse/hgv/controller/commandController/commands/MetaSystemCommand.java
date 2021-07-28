package kit.pse.hgv.controller.commandController.commands;

/**
 * This class handles all commands managing the MetaData
 */
public abstract class MetaSystemCommand extends Command {
    protected int elementId;
    public int getID() { return this.elementId;}
}
