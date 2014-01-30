package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

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
public class BioactiveEntityEnricherListenerManager extends InteractorEnricherListenerManager<BioactiveEntity>
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

    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedOrganism(interactor);
        }
    }

    public void onAddedInteractorType(BioactiveEntity interactor) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed) ;
        }
    }

    public void onAddedXref(BioactiveEntity interactor, Xref added) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(BioactiveEntity interactor, Xref removed) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(BioactiveEntity interactor, Alias added) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
        for(InteractorEnricherListener<BioactiveEntity> listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
