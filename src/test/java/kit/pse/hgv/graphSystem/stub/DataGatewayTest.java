package kit.pse.hgv.graphSystem.stub;

import kit.pse.hgv.controller.dataGateway.DataGateway;
import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    public void saveGraph() {
        String path = "src/test/resources/Vorlage.graphml";
        String savePath = "src/test/resources/result.graphml";
        int graphId = GraphSystem.getInstance().loadGraph(path);
        try {
            dataGateway.saveGraph(graphId, savePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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

    @Test
    public void loadGraph(){
        String path = "src/test/resources/Vorlage.graphml";
        GraphSystem.getInstance().loadGraph(path);
    }

}
