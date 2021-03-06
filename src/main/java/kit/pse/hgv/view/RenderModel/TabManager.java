package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.commands.ICommand;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.HashMap;

public class TabManager {

    private final HashMap<Integer, RenderEngine> tabs;

    public TabManager() {
        this.tabs = new HashMap<>();
    }

    public TabManager(int tabID, RenderEngine engine) {
        this.tabs = new HashMap<>();
        tabs.put(tabID, engine);
    }

    public DrawManager findDrawManager(int tabID) {
        return null;
    }

    public void addTab(int tabID, RenderEngine engine) { // was wenn gleiche enginge für verschiedene tabs?
        if (!tabs.containsKey(tabID))
            tabs.put(tabID, engine);
    }

    public void removeTab(int tabID) {
        tabs.remove(tabID);
    }

    public void notifyEngine(int tabID, ICommand ICommand) {
        tabs.get(tabID).onNotify(ICommand);
    }

}
