package psidev.psi.mi.jami.enricher.listener.bioactiveentity;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Xref;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No contract is given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class BioactiveEntityEnricherListenerManager
        extends EnricherListenerManager<BioactiveEntityEnricherListener>
        implements BioactiveEntityEnricherListener{


    /**
     * A constructor to create a listener manager with no listeners.
     */
    public BioactiveEntityEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public BioactiveEntityEnricherListenerManager(BioactiveEntityEnricherListener... listeners){
        super(listeners);
    }


    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onEnrichmentComplete(object, status, message);
        }
    }

    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onChebiUpdate(bioactiveEntity, oldId);
        }
    }

    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onSmileUpdate(bioactiveEntity, oldSmile);
        }
    }

    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onStandardInchiKeyUpdate(bioactiveEntity, oldKey);
        }
    }

    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onStandardInchiUpdate(bioactiveEntity, oldInchi);
        }
    }

    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedOrganism(interactor);
        }
    }

    public void onAddedInteractorType(BioactiveEntity interactor) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onRemovedIdentifier(interactor, removed) ;
        }
    }

    public void onAddedXref(BioactiveEntity interactor, Xref added) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(BioactiveEntity interactor, Xref removed) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(BioactiveEntity interactor, Alias added) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onRemovedChecksum(interactor, removed);
        }
    }
}
