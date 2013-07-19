package psidev.psi.mi.jami.enricher.impl.protein.listener;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ProteinEnricherStatisticsWriter
        extends StatisticsWriter<Protein>
        implements ProteinEnricherListener {


    public ProteinEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, "Protein");
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message){
        onObjectEnriched(protein , status , message);
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        checkObject(protein);
        updateCount ++;
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        checkObject(protein);
        updateCount ++;
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        checkObject(protein);
        updateCount ++;
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        checkObject(protein);
        updateCount ++;
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        checkObject(protein);
        updateCount ++;
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        checkObject(protein);
        updateCount ++;
    }

    public void onAddedInteractorType(Protein protein) {
        checkObject(protein);
        additionCount ++;
    }

    public void onAddedOrganism(Protein protein) {
        checkObject(protein);
        additionCount ++;
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedXref(Protein protein, Xref added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedAlias(Protein protein, Alias added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        checkObject(protein);
        removedCount ++;
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        checkObject(protein);
        additionCount ++;
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        checkObject(protein);
        removedCount ++;
    }
}
