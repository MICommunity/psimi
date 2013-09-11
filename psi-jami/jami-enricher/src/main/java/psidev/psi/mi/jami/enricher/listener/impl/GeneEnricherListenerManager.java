package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.impl.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherListenerManager
        extends EnricherListenerManager<Gene, GeneEnricherListener>
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

    public void onEnsemblUpdate(Gene gene, String oldValue) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onEnsemblUpdate( gene,  oldValue) ;
        }
    }

    public void onEnsemblGenomeUpdate(Gene gene, String oldValue) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onEnsemblGenomeUpdate( gene, oldValue);
        }
    }

    public void onEntrezGeneIdUpdate(Gene gene, String oldValue) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onEntrezGeneIdUpdate( gene, oldValue);
        }
    }

    public void onRefseqUpdate(Gene gene, String oldValue) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onRefseqUpdate( gene, oldValue);
        }
    }

    public void onShortNameUpdate(Gene interactor, String oldShortName) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(interactor, oldShortName);
        }
    }

    public void onFullNameUpdate(Gene interactor, String oldFullName) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onFullNameUpdate(interactor, oldFullName);
        }
    }

    public void onAddedOrganism(Gene interactor) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedOrganism(interactor) ;
        }
    }

    public void onAddedInteractorType(Gene interactor) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedInteractorType(interactor);
        }
    }

    public void onAddedIdentifier(Gene interactor, Xref added) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier( interactor, added);
        }
    }

    public void onRemovedIdentifier(Gene interactor, Xref removed) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(interactor, removed);
        }
    }

    public void onAddedXref(Gene interactor, Xref added) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedXref(interactor, added) ;
        }
    }

    public void onRemovedXref(Gene interactor, Xref removed) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onRemovedXref(interactor, removed) ;
        }
    }

    public void onAddedAlias(Gene interactor, Alias added) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedAlias(interactor, added);
        }
    }

    public void onRemovedAlias(Gene interactor, Alias removed) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onRemovedAlias(interactor, removed);
        }
    }

    public void onAddedChecksum(Gene interactor, Checksum added) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(Gene interactor, Checksum removed) {
        for(GeneEnricherListener listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }
}
