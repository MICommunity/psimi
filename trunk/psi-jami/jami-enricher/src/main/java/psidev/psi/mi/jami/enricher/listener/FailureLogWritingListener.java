package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class FailureLogWritingListener extends LogWritingListener
    implements ProteinEnricherListener, CvTermEnricherListener , OrganismEnricherListener{


    public FailureLogWritingListener(File outputFile) throws IOException {
        super(outputFile);
    }

    public void writeFailure(){

    }


    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onOrganismEnriched(Organism organism, EnrichmentStatus status, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onCommonNameUpdate(Organism organism, String oldCommonName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTaxidUpdate(Organism organism, String oldTaxid) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedAlias(Organism organism, Alias added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedAlias(Organism organism, Alias removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedInteractorType(Protein protein) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedOrganism(Protein protein) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedXref(Protein protein, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedAlias(Protein protein, Alias added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
