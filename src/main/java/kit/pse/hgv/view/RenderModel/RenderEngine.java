package kit.pse.hgv.view.RenderModel;

import kit.pse.hgv.controller.commandController.CommandQListener;
import kit.pse.hgv.view.uiHandler.RenderHandler;
import kit.pse.hgv.representation.Drawable;
import kit.pse.hgv.view.hyperbolicModel.DrawManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class manages the RenderSystem. It decides when to rerender.
 */
public abstract class RenderEngine implements CommandQListener {

    protected Set<Integer> toBeUpdated;
    protected final RenderHandler handler;
    protected DrawManager drawManager;
    protected List<Drawable> displayedGraph;
    protected final int tabID;
    protected final int graphID;
    private static RenderEngine instance;

    public RenderEngine(int tab, int graph, DrawManager drawManager, RenderHandler handler) {
        this.tabID = tab;
        this.graphID = graph;
        this.handler = handler;
        this.drawManager = drawManager;
        displayedGraph = new ArrayList<>();
        this.toBeUpdated = new HashSet<>();
        instance = this;
    }

    /**
     * Creates or gets the only existing TabManager instance.
     * 
     * @return Returns the only instance of the graphsystem.
     */
    public static RenderEngine getInstance() {
        return instance;
    }

    public DrawManager getDrawManager() {
        return drawManager;
    }

    public abstract void render();

}
