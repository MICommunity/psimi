package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.*;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  12/06/13
 */
public class PolymerEnricherListenerManager<P extends Polymer>
        extends InteractorEnricherListenerManager<P>
        implements PolymerEnricherListener<P> {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public PolymerEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public PolymerEnricherListenerManager(InteractorEnricherListener<P>... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onSequenceUpdate(P protein, String oldSequence) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            if (l instanceof PolymerChangeListener){
                ((PolymerChangeListener)l).onSequenceUpdate(protein, oldSequence);
            }
        }
    }

    public void onShortNameUpdate(P protein, String oldShortName) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onShortNameUpdate(protein, oldShortName);
        }
    }

    public void onFullNameUpdate(P protein, String oldFullName) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onFullNameUpdate(protein, oldFullName);
        }
    }

    public void onAddedInteractorType(P protein) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedInteractorType(protein);
        }
    }

    public void onAddedOrganism(P protein) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedOrganism(protein);
        }
    }

    public void onAddedIdentifier(P protein, Xref added) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedIdentifier(protein, added);
        }
    }

    public void onRemovedIdentifier(P protein, Xref removed) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onRemovedIdentifier(protein, removed);
        }
    }

    public void onAddedXref(P protein, Xref added) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedXref(protein, added);
        }
    }

    public void onRemovedXref(P protein, Xref removed) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onRemovedXref(protein, removed);
        }
    }

    public void onAddedAlias(P protein, Alias added) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedAlias(protein, added);
        }
    }

    public void onRemovedAlias(P protein, Alias removed) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onRemovedAlias(protein, removed);
        }
    }

    public void onAddedChecksum(P protein, Checksum added) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onAddedChecksum(protein, added);
        }
    }

    public void onRemovedChecksum(P protein, Checksum removed) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            l.onRemovedChecksum(protein, removed);
        }
    }

    public void onAddedAnnotation(P o, Annotation added) {
        for(InteractorEnricherListener<P> listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(P o, Annotation removed) {
        for(InteractorEnricherListener<P> listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
