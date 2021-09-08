package kit.pse.hgv;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import kit.pse.hgv.controller.commandController.CommandController;
import kit.pse.hgv.controller.commandController.commands.ShutdownCommand;
import kit.pse.hgv.extensionServer.ExtensionServer;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        CommandController cmdController = CommandController.getInstance();
        cmdController.start();
        ExtensionServer server = ExtensionServer.getInstance();
        server.start();
        scene = new Scene(loadFXML("MainView"), 1280, 720);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("HGV");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/hyperbolicthomas.png")));
        Timer timer = new Timer();
        timer.start();
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            CommandController.getInstance().queueCommand(new ShutdownCommand());
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private class Timer extends AnimationTimer {

        private final long[] frameTimes = new long[1000];
        private int frameTimeIndex = 0 ;
        private boolean arrayFilled = false;

        @Override
        public void handle(long now) {
            long oldFrameTime = frameTimes[frameTimeIndex] ;
            frameTimes[frameTimeIndex] = now ;
            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
            if (frameTimeIndex == 0) {
                arrayFilled = true ;
            }
            if (arrayFilled) {
                long elapsedNanos = now - oldFrameTime ;
                long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                System.out.println("Current frame rate: " + frameRate);
            }
        }
    };
}

