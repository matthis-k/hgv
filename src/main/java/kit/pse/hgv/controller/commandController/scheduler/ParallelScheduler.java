package kit.pse.hgv.controller.commandController.scheduler;

import kit.pse.hgv.controller.commandController.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ParallelScheduler implements IScheduler {

    /**
     * Returns a list of commands. The list can be executed at the same time without interrupts.
     *
     * @param commandQ is the commandQ that should be analysed.
     * @return Returns a List of ICommands whcih can be executed parallel.
     */
    @Override
    public List<ICommand> getNextCommand(Queue<ICommand> commandQ) {

        List<ICommand> scheduled = new ArrayList<>();
        scheduled.add(commandQ.poll());

        //Wir halten mal unabhängig hiervon fest das ihr das command System vertrashed habt... ihr habt nur scheiße priogrammiert...

        // Welcher Code ist save zum gleiczeitig abzuarbeiten?!

        for(ICommand c : commandQ) {
            if(c instanceof GraphSystemCommand ||c instanceof MetaSystemCommand) {
                List<Integer> modifiedIDs;
                //Check: Gleiches Element?
                //TODO: contains ALl muss es alles haben oder reicht 1 ? CHECK : geht nicht so-
                //TODO: getModifiedsIDs
                if(c.getModifiedIds().containsAll(modifiedIDs)) {
                    scheduled.add(c);
                    modifiedIDs.add(); //TODO: Add die benutzte ID.
                } else {
                    return scheduled;
                }
            } else if(c instanceof HyperModelCommand) {
                //Check: MoveCenter und RenderCommand muss einzeln gerechnet werden -> Zeitl. Konsistenz in der Ansicht
                return scheduled;
            } else if(c instanceof FileSystemCommand) {
                //Check: Theoretisch geht es das gleichzeitig zu machen
                return scheduled;
            } else if(c instanceof ExtensionCommand) {
                //Check: Alles einzeln, sonst zeitl Inkosistent.
                return scheduled;
            }
        }
        return scheduled;
        //TODO: Überprüfen ob Commands danach funktionieren.

        //Nebengedanken:
        /*

        Metadaten sind egal.
        Knoten Positionen sind unter sich egal.
        Elemente Erstellen ist egal.
        Elemente Löschen ist egal.
        Gleiches ELEMENT ist nciht egal.



         */


        return null;
    }
}
