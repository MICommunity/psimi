package psidev.psi.mi.jami.enricher.listener.protein;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class ProteinEnricherStatisticsWriter
        extends StatisticsWriter<Protein>
        implements ProteinEnricherListener {


    private static final String jamiObject = "Protein";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter() throws IOException {
        super(jamiObject);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, jamiObject);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, jamiObject);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ProteinEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, jamiObject);
    }


    // ================================================================


    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message){
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
