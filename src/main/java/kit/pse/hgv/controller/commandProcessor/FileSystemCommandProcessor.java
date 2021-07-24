package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.*;

import java.io.File;

public class FileSystemCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }

    //TODO: Du bekommst gleich eine file zurück
    public void loadGraph(File file){
        //LoadGraphCommand command = new LoadGraphCommand(path);
    }

    public void saveGraph(String path, int id){
        SaveGraphCommand command = new SaveGraphCommand(id, path);
    }

    public void createNewGraph() {
        //TODO implement
    }

    public void shutdown(){
        ShutdownCommand command = new ShutdownCommand();
    }
}
