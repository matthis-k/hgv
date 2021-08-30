package kit.pse.hgv.dataGateway;

import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
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
    public void saveGraph() throws FileNotFoundException, OverflowException {
        String path = "src/test/resources/Vorlage.graphml";
        String savePath = "src/test/resources/result.graphml";
        int graphId = GraphSystem.getInstance().loadGraph(path);
        try {
            dataGateway.saveGraph(graphId, savePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    @Test
    public void addLastGraphPath() {
        String path = "src/test/resources/Vorlage.graphml";
        dataGateway.addlastOpened(path);
        File lastOpened = new File("src/main/resources/lastOpenedFile.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(lastOpened));
            assertEquals(reader.readLine(), path);
        } catch (IOException e) {

        }
    }

    @Test
    public void getLastGraphs() {
        String path = "src/test/resources/Vorlage.graphml";
        dataGateway.addlastOpened(path);
        List lastOpened = dataGateway.getlastOpenedGraphs();
        assertEquals(lastOpened.get(0), path);
    }
    */

    @Test
    public void loadGraph() throws FileNotFoundException, OverflowException {
        String path = "src/test/resources/Vorlage.graphml";
        GraphSystem.getInstance().loadGraph(path);
    }

    //TODO: fix element Id
    @Ignore
    @Test
    public void saveLoadedGraph() throws IOException, OverflowException {
        int id = GraphSystem.getInstance().loadGraph("src/main/resources/spiralGraph.graphml");
        GraphSystem.getInstance().getGraphElementByID(id, 51).setMetadata("weight", "3.14159");
        DataGateway.saveGraph(id, "src/test/resources/testOutput");
        File file1 = new File("src/main/resources/spiralGraph.graphml");
        File file2 = new File("src/main/resources/testOutput");

    }

}
