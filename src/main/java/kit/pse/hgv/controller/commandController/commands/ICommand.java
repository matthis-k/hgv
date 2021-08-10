package kit.pse.hgv.controller.commandController.commands;

import org.json.JSONObject;

import java.util.Set;

public interface ICommand {
    public void execute();
    public void undo();

    /**
     * TODO FÜR ALLE HIER MÜSSEN BESCHREIBUNGEN DA SEIN!!!!! WAS MACHT DIESE METHODE!=?!?!?!?!?!?!?!?!
     * @return
     */
    public Set<Integer> getModifiedIds();
    public boolean isUser();
    public JSONObject getResponse();
    public int getClientId();
    public void setClientId(int id);
}
