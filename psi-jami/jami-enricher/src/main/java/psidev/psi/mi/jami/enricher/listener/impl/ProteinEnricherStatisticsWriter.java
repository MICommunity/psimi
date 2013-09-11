package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.impl.EnricherStatisticsWriter;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ProteinEnricherStatisticsWriter
        extends EnricherStatisticsWriter<Protein>
        implements ProteinEnricherListener {


    private static final String FILE_NAME = "Protein";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================


    public void onUniprotKbUpdate(Protein protein, String oldUniprot) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onRefseqUpdate(Protein protein, String oldRefseq) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onGeneNameUpdate(Protein protein, String oldGeneName) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onRogidUpdate(Protein protein, String oldRogid) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onShortNameUpdate(Protein protein, String oldShortName) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onFullNameUpdate(Protein protein, String oldFullName) {
        checkObject(protein);
        incrementUpdateCount();
    }

    public void onAddedInteractorType(Protein protein) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onAddedOrganism(Protein protein) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onAddedIdentifier(Protein protein, Xref added) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onRemovedIdentifier(Protein protein, Xref removed) {
        checkObject(protein);
        incrementRemovedCount();
    }

    public void onAddedXref(Protein protein, Xref added) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onRemovedXref(Protein protein, Xref removed) {
        checkObject(protein);
        incrementRemovedCount();
    }

    public void onAddedAlias(Protein protein, Alias added) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onRemovedAlias(Protein protein, Alias removed) {
        checkObject(protein);
        incrementRemovedCount();
    }

    public void onAddedChecksum(Protein protein, Checksum added) {
        checkObject(protein);
        incrementAdditionCount();
    }

    public void onRemovedChecksum(Protein protein, Checksum removed) {
        checkObject(protein);
        incrementRemovedCount();
    }
}
