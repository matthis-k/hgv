package kit.pse.hgv.controller.commandController.commands;

import java.util.Set;

public interface ICommand {
    public void execute();
    public void undo();
    public Set<Integer> getModifiedIds();
    public boolean isUser();
    public int getClientId();
    public void setClientId(int id);
}
