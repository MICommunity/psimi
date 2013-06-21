package psidev.psi.mi.jami.enricher.impl.protein.listener;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 12/06/13
 * Time: 14:44
 */
public class ProteinEnricherCounter implements ProteinEnricherListener {

    String exit_status = null;

    int update_count = 0;
    int removed_count = 0;
    int added_count = 0;

    public int getAdded(){return added_count;}
    public int getRemoved(){return removed_count;}
    public int getUpdated(){return update_count;}

    public String getStatus(){return exit_status;}

    public void onProteinEnriched(Protein protein, String status) {
        exit_status = status;
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        //todo
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        update_count++;
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        update_count++;
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        update_count++;
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        update_count++;
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        update_count++;
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        update_count++;
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        update_count++;
    }

    public void onAddedInteractorType(Protein protein) {
        added_count++;
    }

    public void onAddedOrganism(Protein protein) {
        added_count++;
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        added_count++;
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        removed_count++;
    }

    public void onAddedXref(Protein protein, Xref added) {
        added_count++;
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        removed_count++;
    }

    public void onAddedAlias(Protein protein, Alias added) {
        added_count++;
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        removed_count++;
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        added_count++;
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        removed_count++;
    }
}
