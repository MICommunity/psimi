package psidev.psi.mi.enricherlistener;

import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:12
 */
public abstract class EnricherEventProcessorImp implements EnricherEventProcessor{

    private ArrayList<EnricherListener> enricherListeners = new ArrayList<EnricherListener>();

    protected EnricherEvent enricherEvent = new EnricherEvent();

    public void addEnricherListener(EnricherListener listener){
        enricherListeners.add(listener);
    }

    public void removeEnricherListener(EnricherListener listener){
        if(enricherListeners.contains(listener)){
            enricherListeners.remove(enricherListeners.indexOf(listener));
        }
    }

    public void fireEnricherEvent(EnricherEvent e) {
        for (EnricherListener il : enricherListeners) {
            il.enricherEvent(e);
        }
    }

    public void addOverwriteEvent(OverwriteEvent e) {
        enricherEvent.addOverwriteEvent(e);
    }

    public void addAdditionEvent(AdditionEvent e) {
        enricherEvent.addAdditionEvent(e);
    }

    public void addMismatchEvent(MismatchEvent e) {
        enricherEvent.addMismatchEvent(e);
    }
}
