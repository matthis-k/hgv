package kit.pse.hgv.graphSystem.stub;

import kit.pse.hgv.controller.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.Before;
import org.junit.Test;

public class LoadGraphTest {

    @Before
    public void buildEnvironment() {

    }

    @Test
    public void saveGraph() {
        String path = "src/test/resources/Vorlage.graphml";
        String savePath = "src/test/resources/result.graphml";
        int graphId = GraphSystem.getInstance().loadGraph(path);
        try {
            DataGateway.saveGraph(graphId, savePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void loadGraph(){
        String path = "src/test/resources/Vorlage.graphml";
        GraphSystem.getInstance().loadGraph(path);
    }

}
