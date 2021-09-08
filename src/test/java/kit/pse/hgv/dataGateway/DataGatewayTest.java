package kit.pse.hgv.dataGateway;

import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.IllegalGraphOperation;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;


public class DataGatewayTest {

    private static DataGateway dataGateway;

    @BeforeClass
    public static void setup(){
        dataGateway = new DataGateway();
    }

    @Before
    public void buildEnvironment() {

    }

    @Test
    public void saveGraph() throws FileNotFoundException, OverflowException, IllegalGraphOperation {
        String path = "src/test/resources/Vorlage.graphml";
        String savePath = "src/test/resources/result";
        int graphId = GraphSystem.getInstance().loadGraph(path);
        try {
            dataGateway.saveGraph(graphId, savePath);
        } catch (Exception e) {
        }
    }


    @Test
    public void loadGraph() throws FileNotFoundException, OverflowException, IllegalGraphOperation {
        String path = "src/test/resources/Vorlage.graphml";
        GraphSystem.getInstance().loadGraph(path);
    }


}
