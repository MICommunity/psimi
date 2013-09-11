package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
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
public class BioactiveEntityEnricherListenerManager extends EnricherListenerManager<BioactiveEntity, BioactiveEntityEnricherListener>
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

    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onChebiUpdate(bioactiveEntity, oldId);
        }
    }

    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onSmileUpdate(bioactiveEntity, oldSmile);
        }
    }

    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onStandardInchiKeyUpdate(bioactiveEntity, oldKey);
        }
    }

    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onStandardInchiUpdate(bioactiveEntity, oldInchi);
        }
    }

    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedOrganism(interactor);
        }
    }

    public void onAddedInteractorType(BioactiveEntity interactor) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed) ;
        }
    }

    public void onAddedXref(BioactiveEntity interactor, Xref added) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(BioactiveEntity interactor, Xref removed) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(BioactiveEntity interactor, Alias added) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
        for(BioactiveEntityEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
