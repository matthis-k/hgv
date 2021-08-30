package kit.pse.hgv.controller.commandController.scheduler;

import kit.pse.hgv.controller.commandController.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @version 0.2 Not tested yet!
 *
 * This class is a paralell scheduler.
 * Commands got with the getnextCommands method can be executed
 * at the same time without risk to have issues.
 */
public class ParallelScheduler implements IScheduler {

    /**
     * Returns a list of commands. The list can be executed at the same time without interrupts.
     *
     * @param commandQ is the commandQ that should be analysed.
     * @return Returns a List of ICommands whcih can be executed parallel.
     */
    @Override
    public List<ICommand> getNextCommand(Queue<ICommand> commandQ) {

        List<ICommand> scheduled = new ArrayList<>();
        scheduled.add(commandQ.poll());

        for(ICommand c : commandQ) {

            if(c instanceof WorkingAreaCommand) {
                WorkingAreaCommand ac = (WorkingAreaCommand) c;
                if (!hasWorkingMatch(ac, scheduled)) {
                    scheduled.add(c);
                } else {
                    return scheduled;
                }

            //This for future scheduling maybe commands can too be handeled!
            } else if(c instanceof HyperModelCommand) {
                return scheduled;
            } else if(c instanceof FileSystemCommand) {
                return scheduled;
            } else if(c instanceof ExtensionCommand) {
                return scheduled;
            }
        }
        return scheduled;
    }


    /**
     * Controlls that a Command has no overlapping WorkingArea with a given list of GraphElement ids.
     * @param c is the Command.
     * @param workingArea is the given id List.
     * @return Returns true when there is one equal id. False when not.
     */
    private boolean hasWorkingMatch(WorkingAreaCommand c, List<ICommand> workingArea) {
        for (Integer i : c.getWorkingArea()) {
            for (ICommand ic : workingArea) {
                if (ic instanceof WorkingAreaCommand) {
                    for (Integer j : ((WorkingAreaCommand) ic).getWorkingArea()) {
                        if (j == i) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
