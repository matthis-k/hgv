package kit.pse.hgv.extensionServer;

import java.io.IOException;

public class PyScript implements Extension {
    private String path;
    public PyScript(String path) {
        this.path = path;
    }
    @Override
    public void startExtension() {
        try {
            Process p = Runtime.getRuntime().exec("python " + path);
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopExtension() {

    }
}
