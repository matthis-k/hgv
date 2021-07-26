package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.*;

import java.io.File;

public class FileSystemCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        //TODO CommandController.getInstance().queueCommand(command);
        
    }

    public void loadGraph(File file){
        String path = file.getPath();
        LoadGraphCommand command = new LoadGraphCommand(path);
        queueCommand(command);
    }

    public void saveGraph(String path, int id){
        SaveGraphCommand command = new SaveGraphCommand(id, path);
        queueCommand(command);
    }

    public void createNewGraph() {
        CreateNewGraphCommand command = new CreateNewGraphCommand();
        queueCommand(command);
    }

    public void shutdown(){
        ShutdownCommand command = new ShutdownCommand();
        queueCommand(command);
    }
}
