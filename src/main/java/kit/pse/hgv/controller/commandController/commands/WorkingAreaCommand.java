package kit.pse.hgv.controller.commandController.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkingAreaCommand extends Command {

    /**
     * Elements which will be changed in execution.
     */
    private final List<Integer> workingArea = new ArrayList<Integer>();

    public List<Integer> getWorkingArea() {
        return workingArea;
    }

    /**
     * Adds an element which will be changed in execution
     * @param elementID element of which should be changed
     */
    protected void extendWorkingArea(int elementID) {
        if (!workingArea.contains(elementID)) {
            workingArea.add(elementID);
        }
    }

}
