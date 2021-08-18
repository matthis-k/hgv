package kit.pse.hgv.extensionServer;

import org.junit.Test;

public class PyScriptTest {

    @Test
    public void testScript() {
        PyScript script = new PyScript("src/test/resources/script.py");
        script.startExtension();
        script.stopExtension();
    }

}