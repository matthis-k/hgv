package kit.pse.hgv.extensionServer;

import java.io.IOException;

public class PyScript extends Thread implements Extension {
    private final String path;
    private Process p;

    public PyScript(String path) {
        this.path = path;
        setName("PyScript: " + path);
    }

    @Override
    public void startExtension() {
        start();
    }

    @Override
    public void run() {
        try {
            p = Runtime.getRuntime().exec("python " + path);
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
        if (p != null) {
            p.destroy();
        }
        interrupt();
    }
}
