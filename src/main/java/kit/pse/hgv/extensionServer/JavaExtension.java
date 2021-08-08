package kit.pse.hgv.extensionServer;

import java.io.IOException;

public class JavaExtension extends Thread implements Extension {
    private String path;

    public JavaExtension(String path) {
        this.path = path;
    }

    @Override
    public void startExtension() {
        try {
            Process p = Runtime.getRuntime().exec("java " + path);
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
