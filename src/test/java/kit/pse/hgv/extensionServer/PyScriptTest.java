package kit.pse.hgv.extensionServer;

import org.junit.Test;

public class PyScriptTest {

    @Test
    public void testScript() throws InterruptedException {
        PyScript script = new PyScript("src/test/resources/client.py");
        script.startExtension();
        synchronized (this) {
            wait(100);
        }
        script.stopExtension();
    }

}