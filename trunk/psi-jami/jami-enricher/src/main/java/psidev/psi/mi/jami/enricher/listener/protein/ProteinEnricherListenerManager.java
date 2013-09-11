package psidev.psi.mi.jami.enricher.listener.protein;


import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

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
public class ProteinEnricherListenerManager
        extends EnricherListenerManager<Protein, ProteinEnricherListener>
        implements ProteinEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ProteinEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ProteinEnricherListenerManager(ProteinEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onUniprotKbUpdate(protein, oldUniprot);
        }
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRefseqUpdate(protein, oldRefseq);
        }
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onGeneNameUpdate(protein, oldGeneName);
        }
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRogidUpdate(protein, oldRogid);
        }
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onSequenceUpdate(protein, oldSequence);
        }
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onShortNameUpdate(protein, oldShortName);
        }
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onFullNameUpdate(protein, oldFullName);
        }
    }

    public void onAddedInteractorType(Protein protein) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedInteractorType(protein);
        }
    }

    public void onAddedOrganism(Protein protein) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedOrganism(protein);
        }
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedIdentifier(protein, added);
        }
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRemovedIdentifier(protein, removed);
        }
    }

    public void onAddedXref(Protein protein, Xref added) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedXref(protein, added);
        }
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRemovedXref(protein, removed);
        }
    }

    public void onAddedAlias(Protein protein, Alias added) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedAlias(protein, added);
        }
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRemovedAlias(protein, removed);
        }
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onAddedChecksum(protein, added);
        }
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        for(ProteinEnricherListener l : getListenersList()){
            l.onRemovedChecksum(protein, removed);
        }
    }
}
