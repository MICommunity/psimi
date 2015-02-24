package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Manager of interactor listeners
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class InteractorEnricherListenerManager<T extends Interactor> extends EnricherListenerManager<T, InteractorEnricherListener<T>>
    implements InteractorEnricherListener<T>{
        /**
         * A constructor to create a listener manager with no listeners.
         */
        public InteractorEnricherListenerManager(){}

        /**
         * A constructor to initiate a listener manager with as many listeners as required.
         * @param listeners     The listeners to add.
         */
        public InteractorEnricherListenerManager(InteractorEnricherListener<T>... listeners){
            super(listeners);
        }

    public void onShortNameUpdate(T interactor, String oldShortName) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(T interactor, String oldFullName) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onOrganismUpdate(T interactor, Organism org) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onOrganismUpdate(interactor, org);
        }
    }

    public void onInteractorTypeUpdate(T interactor, CvTerm old) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onInteractorTypeUpdate(interactor, old);
        }
    }

    public void onAddedIdentifier(T interactor, Xref added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(T interactor, Xref removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed) ;
        }
    }

    public void onAddedXref(T interactor, Xref added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(T interactor, Xref removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(T interactor, Alias added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(T interactor, Alias removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(T interactor, Checksum added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(T interactor, Checksum removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedAnnotation(T o, Annotation added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(T o, Annotation removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
