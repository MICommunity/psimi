package psidev.psi.mi.jami.enricher.enricherimplementation.protein.eventprocessor;



import psidev.psi.mi.jami.enricher.enricherimplementation.protein.event.*;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener.ProteinEnricherListener;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 10:50
 */
public abstract class ProteinEventProcessor {

    private ArrayList<ProteinEnricherListener> enricherListeners = new ArrayList<ProteinEnricherListener>();

    protected QueryDetails queryDetails;
    protected UpdatedEvent updatedEvent = new UpdatedEvent();
    protected AddedEvent addedEvent = new AddedEvent();


    /**
     * Adds a new EnricherListener to report to.
     * @param listener  A listener to report to.
     */
    public void addEnricherListener(ProteinEnricherListener listener){
        enricherListeners.add(listener);
    }

    /**
     * Removes an EnricherListener
     * @param listener  A listener not to report to.
     */
    public void removeEnricherListener(ProteinEnricherListener listener){
        if(enricherListeners.contains(listener)){
            enricherListeners.remove(enricherListeners.indexOf(listener));
        }
    }


    public void fireAllReportEvents(){
        fireAdditionReportEvent();
        fireUpdateReportEvent();
    }

    /**
     * ErrorEvents will tend to be fired as soon as they are encountered.
     * @param e
     */
    public void fireErrorEvent(ErrorEvent e){
        for (ProteinEnricherListener il : enricherListeners) {
            il.onErrorEvent(e);
        }
    }

    /**
     *
     * ConflictEvents may be fired as soon as they are encountered.
     * @param e
     */
    public void fireConflictEvent(ConflictEvent e){
        for (ProteinEnricherListener il : enricherListeners) {
            il.onConflictEvent(e);
        }
    }

    public void fireAdditionReportEvent(){
        addedEvent.setQueryDetails(queryDetails);
        for (ProteinEnricherListener il : enricherListeners) {
            il.onAdditionReportEvent(addedEvent);
        }
    }

    public void fireUpdateReportEvent(){
        updatedEvent.setQueryDetails(queryDetails);
        for (ProteinEnricherListener il : enricherListeners) {
            il.onUpdateEventReport(updatedEvent);
        }

    }


}
