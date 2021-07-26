package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.CommandQListener;
import kit.pse.hgv.controller.commandController.commands.*;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderEngine implements CommandQListener {

    protected List<Integer> toBeUpdated;
    protected final RenderHandler handler;
    protected DrawManager drawManager;
    protected List<Drawable> displayedGraph;
    protected final int tabID;
    protected final int graphID;
    protected final UserScheduler userScheduler;
    protected final ExtensionScheduler extensionScheduler;

    public RenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        this.tabID = tab;
        this.graphID = graph;
        this.handler = handler;
        this.drawManager = drawManager;
        displayedGraph = new ArrayList<>();
        this.toBeUpdated = new ArrayList<>();
        this.userScheduler = new UserScheduler();
        this.extensionScheduler = new ExtensionScheduler();
    }

    public abstract void firstRender();
    public abstract void rerender();

    public abstract void receiveCommand(Command command);
    public abstract void receiveCommand(FileSystemCommand command);
    public abstract void receiveCommand(MoveCenterCommand command);
    public abstract void receiveCommand(MetaSystemCommand command);
    public abstract void receiveCommand(GraphSystemCommand command);
    public abstract void receiveCommand(LoadGraphCommand command);

}
