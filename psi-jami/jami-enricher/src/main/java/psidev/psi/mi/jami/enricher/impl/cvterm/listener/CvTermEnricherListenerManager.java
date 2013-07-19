package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * An enricher listener manager for CvTerm listeners.
 *
 * Has a list of CvTermListeners and fires their corresponding events when fired in the manager.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public class CvTermEnricherListenerManager
    extends EnricherListenerManager<CvTermEnricherListener>
    implements CvTermEnricherListener{


    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onCvTermEnriched(cvTerm, status, message);
        }
    }

    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onShortNameUpdate( cv,  oldShortName);
        }
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onFullNameUpdate( cv,  oldFullName);
        }
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onMIIdentifierUpdate( cv,  oldMI) ;
        }
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onMODIdentifierUpdate(cv, oldMOD);
        }
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onPARIdentifierUpdate(cv, oldPAR);
        }
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onAddedIdentifier(cv, added);
        }
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onRemovedIdentifier(cv, removed);
        }
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onAddedXref(cv, added);
        }
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onRemovedXref(cv, removed);
        }
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onAddedSynonym(cv, added);
        }
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        for(CvTermEnricherListener listener : listenersList){
            listener.onRemovedSynonym(cv, removed);
        }
    }
}
