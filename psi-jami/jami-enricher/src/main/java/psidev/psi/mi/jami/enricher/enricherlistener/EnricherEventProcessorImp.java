package psidev.psi.mi.jami.enricher.enricherlistener;

import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.enricherlistener.event.OverwriteEvent;

import java.util.ArrayList;

/**
 * An extension to enrichers which allows other modules to listen
 * for a report at the end of each enrichment.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:12
 */
public abstract class EnricherEventProcessorImp implements EnricherEventProcessor{

    //List of all listeners to report to
    private ArrayList<EnricherListener> enricherListeners = new ArrayList<EnricherListener>();
    //The report of all activity
    protected EnricherEvent enricherEvent = new EnricherEvent();

    /**
     * Adds a new EnricherListener to report to.
     * @param listener  A listener to report to.
     */
    public void addEnricherListener(EnricherListener listener){
        enricherListeners.add(listener);
    }

    /**
     * Removes an EnricherListener
     * @param listener  A listener not to report to.
     */
    public void removeEnricherListener(EnricherListener listener){
        if(enricherListeners.contains(listener)){
            enricherListeners.remove(enricherListeners.indexOf(listener));
        }
    }

    /**
     * Fires the enricherEvent with details of all changes made.
     * @param e The report of all events.
     */
    public void fireEnricherEvent(EnricherEvent e) {
        for (EnricherListener il : enricherListeners) {
            il.enricherEvent(e);
        }
    }

    /**
     * Adds information to the enricherEvent when something has been overwritten,
     * @param e A description of what has been overwritten
     */
    public void addOverwriteEvent(OverwriteEvent e) {
        enricherEvent.addOverwriteEvent(e);
    }

    /**
     * Adds information to the enricherEvent when something has been added.
     * @param e A description of what has been added
     */
    public void addAdditionEvent(AdditionEvent e) {
        enricherEvent.addAdditionEvent(e);
    }

    /**
     * Adds information to the enricherEvent when two fields do not match.
     * @param e A description of what mismatches
     */
    public void addMismatchEvent(MismatchEvent e) {
        enricherEvent.addMismatchEvent(e);
    }
}
