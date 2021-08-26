package kit.pse.hgv.dataGateway;

import kit.pse.hgv.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import kit.pse.hgv.graphSystem.exception.OverflowException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;


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

}
