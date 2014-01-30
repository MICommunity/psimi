package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherListenerManager
        extends InteractorEnricherListenerManager<Gene>
        implements GeneEnricherListener{


    /**
     * A constructor to create a listener manager with no listeners.
     */
    public GeneEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public GeneEnricherListenerManager(GeneEnricherListener... listeners){
        super(listeners);
    }

    public void onShortNameUpdate(Gene interactor, String oldShortName) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(Gene interactor, String oldFullName) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(Gene interactor) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedOrganism(interactor) ;
        }
    }

    public void onAddedInteractorType(Gene interactor) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(Gene interactor, Xref added) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(Gene interactor, Xref removed) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed);
        }
    }

    public void onAddedXref(Gene interactor, Xref added) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(Gene interactor, Xref removed) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(Gene interactor, Alias added) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(Gene interactor, Alias removed) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(Gene interactor, Checksum added) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(Gene interactor, Checksum removed) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedAnnotation(Gene o, Annotation added) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(Gene o, Annotation removed) {
        for(InteractorEnricherListener<Gene> listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }
}
