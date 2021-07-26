package kit.pse.hgv.graphSystem.stub;

import kit.pse.hgv.graphSystem.GraphSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class LoadGraphTest {

    @Before
    public void buildEnvironment() {

    }

    @Test
    public void testRegex() {
        String line = "     <node id=\"123\">";
        String pattern = "([\\s|\\t]*<node\\sid=\")(\\d+)(\">)";
        String pattern2 = "[\\s|\\t]*<node\\sid=\"(\\d+)\">";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }

    }


    @Test
    public void loadGraph(){
        String path = "src/test/resources/Vorlage.graphml";
        GraphSystem.getInstance().loadGraph(path);
    }


}
