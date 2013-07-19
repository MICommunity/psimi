package psidev.psi.mi.jami.enricher.impl.protein.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  12/06/13
 */
public class ProteinEnricherListenerManager
        extends EnricherListenerManager<ProteinEnricherListener>
        implements ProteinEnricherListener {


    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        for(ProteinEnricherListener l : listenersList){
            l.onProteinEnriched(protein, status , message);
        }
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        for(ProteinEnricherListener l : listenersList){
            l.onProteinRemapped(protein, oldUniprot);
        }
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        for(ProteinEnricherListener l : listenersList){
            l.onUniprotKbUpdate(protein, oldUniprot);
        }
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        for(ProteinEnricherListener l : listenersList){
            l.onRefseqUpdate(protein, oldRefseq);
        }
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        for(ProteinEnricherListener l : listenersList){
            l.onGeneNameUpdate(protein, oldGeneName);
        }
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        for(ProteinEnricherListener l : listenersList){
            l.onRogidUpdate(protein, oldRogid);
        }
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        for(ProteinEnricherListener l : listenersList){
            l.onSequenceUpdate(protein, oldSequence);
        }
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        for(ProteinEnricherListener l : listenersList){
            l.onShortNameUpdate(protein, oldShortName);
        }
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        for(ProteinEnricherListener l : listenersList){
            l.onFullNameUpdate(protein, oldFullName);
        }
    }

    public void onAddedInteractorType(Protein protein) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedInteractorType(protein);
        }
    }

    public void onAddedOrganism(Protein protein) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedOrganism(protein);
        }
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedIdentifier(protein, added);
        }
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : listenersList){
            l.onRemovedIdentifier(protein, removed);
        }
    }

    public void onAddedXref(Protein protein, Xref added) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedXref(protein, added);
        }
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : listenersList){
            l.onRemovedXref(protein, removed);
        }
    }

    public void onAddedAlias(Protein protein, Alias added) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedAlias(protein, added);
        }
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        for(ProteinEnricherListener l : listenersList){
            l.onRemovedAlias(protein, removed);
        }
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        for(ProteinEnricherListener l : listenersList){
            l.onAddedChecksum(protein, added);
        }
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        for(ProteinEnricherListener l : listenersList){
            l.onRemovedChecksum(protein, removed);
        }
    }
}
