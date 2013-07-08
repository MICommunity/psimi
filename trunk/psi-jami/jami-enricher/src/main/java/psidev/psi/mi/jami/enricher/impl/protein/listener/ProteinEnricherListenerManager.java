package psidev.psi.mi.jami.enricher.impl.protein.listener;

import psidev.psi.mi.jami.enricher.util.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/06/13
 * Time: 17:00
 */
public class ProteinEnricherListenerManager implements ProteinEnricherListener {

    private ArrayList<ProteinEnricherListener> proteinEnricherListeners = new ArrayList<ProteinEnricherListener>();

    public void addProteinEnricherListener(ProteinEnricherListener listener){
        proteinEnricherListeners.add(listener);
    }

    public void removeProteinEnricherListener(ProteinEnricherListener listener){
        proteinEnricherListeners.remove(listener);
    }

    //===================

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onProteinEnriched(protein, status , message);
        }
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onProteinRemapped(protein, oldUniprot);
        }
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onUniprotKbUpdate(protein, oldUniprot);
        }
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRefseqUpdate(protein, oldRefseq);
        }
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onGeneNameUpdate(protein, oldGeneName);
        }
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRogidUpdate(protein, oldRogid);
        }
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onSequenceUpdate(protein, oldSequence);
        }
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onShortNameUpdate(protein, oldShortName);
        }
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onFullNameUpdate(protein, oldFullName);
        }
    }

    public void onAddedInteractorType(Protein protein) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedInteractorType(protein);
        }
    }

    public void onAddedOrganism(Protein protein) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedOrganism(protein);
        }
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedIdentifier(protein, added);
        }
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRemovedIdentifier(protein, removed);
        }
    }

    public void onAddedXref(Protein protein, Xref added) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedXref(protein, added);
        }
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRemovedXref(protein, removed);
        }
    }

    public void onAddedAlias(Protein protein, Alias added) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedAlias(protein, added);
        }
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRemovedAlias(protein, removed);
        }
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onAddedChecksum(protein, added);
        }
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        for(ProteinEnricherListener l : proteinEnricherListeners){
            l.onRemovedChecksum(protein, removed);
        }
    }
}
