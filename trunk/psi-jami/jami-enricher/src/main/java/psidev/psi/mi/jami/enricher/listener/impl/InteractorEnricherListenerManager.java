package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class InteractorEnricherListenerManager extends EnricherListenerManager<Interactor, InteractorEnricherListener<Interactor>>
    implements InteractorEnricherListener<Interactor>{
        /**
         * A constructor to create a listener manager with no listeners.
         */
        public InteractorEnricherListenerManager(){}

        /**
         * A constructor to initiate a listener manager with as many listeners as required.
         * @param listeners     The listeners to add.
         */
        public InteractorEnricherListenerManager(InteractorEnricherListener... listeners){
            super(listeners);
        }

    public void onShortNameUpdate(Interactor interactor, String oldShortName) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(Interactor interactor, String oldFullName) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(Interactor interactor) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedOrganism(interactor);
        }
    }

    public void onAddedInteractorType(Interactor interactor) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(Interactor interactor, Xref added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(Interactor interactor, Xref removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed) ;
        }
    }

    public void onAddedXref(Interactor interactor, Xref added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(Interactor interactor, Xref removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(Interactor interactor, Alias added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(Interactor interactor, Alias removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(Interactor interactor, Checksum added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(Interactor interactor, Checksum removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedAnnotation(Interactor o, Annotation added) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(Interactor o, Annotation removed) {
        for(InteractorEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
