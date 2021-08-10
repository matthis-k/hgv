package kit.pse.hgv.controller.commandController.scheduler;

import kit.pse.hgv.controller.commandController.commands.ICommand;

import java.util.List;
import java.util.Queue;

public interface IScheduler {

    public List<ICommand> getNextCommand(Queue commandQ);

}
