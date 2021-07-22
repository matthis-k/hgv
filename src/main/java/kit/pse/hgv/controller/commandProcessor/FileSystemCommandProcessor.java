package kit.pse.hgv.controller.commandProcessor;

import kit.pse.hgv.controller.commandController.commands.*;

public class FileSystemCommandProcessor implements CommandProcessor{

    @Override
    public void queueCommand(Command command) {
        // TODO Auto-generated method stub
        
    }
    
    public void loadGraph(String path){
        LoadGraphCommand command = new LoadGraphCommand(path);
    }

    public void saveGraph(String path, int id){
        SaveGraphCommand command = new SaveGraphCommand(id, path);
    }

    public void shutdown(){
        ShutdownCommand command = new ShutdownCommand();
    }
}
