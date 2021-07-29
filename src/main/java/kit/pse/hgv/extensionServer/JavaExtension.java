package kit.pse.hgv.extensionServer;

import java.io.IOException;

public class JavaExtension implements Extension {
    private String path;
    public JavaExtension(String path) {
        this.path = path;
    }

    @Override
    public void startExtension() {

        try {
            Process p = Runtime.getRuntime().exec("python " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopExtension() {

    }
}
