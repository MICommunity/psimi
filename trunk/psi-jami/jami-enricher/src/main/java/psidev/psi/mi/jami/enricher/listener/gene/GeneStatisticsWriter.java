package psidev.psi.mi.jami.enricher.listener.gene;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneStatisticsWriter
        extends StatisticsWriter<Gene>
        implements GeneEnricherListener {


    public static final String jamiObject = "Gene";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public GeneStatisticsWriter() throws IOException {
        super(jamiObject);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneStatisticsWriter(String fileName) throws IOException {
        super(fileName, jamiObject);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, jamiObject);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, jamiObject);
    }


    // ================================================================

    public void onEnrichmentComplete(Gene object, EnrichmentStatus status, String message){
        onObjectEnriched(object , status , message);
    }

    public void onEnsemblUpdate(Gene gene, String oldValue) {
        checkObject(gene);
        updateCount++;
    }

    public void onEnsemblGenomeUpdate(Gene gene, String oldValue) {
        checkObject(gene);
        updateCount++;
    }

    public void onEntrezGeneIdUpdate(Gene gene, String oldValue) {
        checkObject(gene);
        updateCount++;
    }

    public void onRefseqUpdate(Gene gene, String oldValue) {
        checkObject(gene);
        updateCount++;
    }

    public void onShortNameUpdate(Gene gene, String oldShortName) {
        checkObject(gene);
        updateCount++;
    }

    public void onFullNameUpdate(Gene gene, String oldFullName) {
        checkObject(gene);
        updateCount++;
    }

    public void onAddedOrganism(Gene gene) {
        checkObject(gene);
        additionCount++;
    }

    public void onAddedInteractorType(Gene gene) {
        checkObject(gene);
        additionCount++;
    }

    public void onAddedIdentifier(Gene gene, Xref added) {
        checkObject(gene);
        additionCount++;
    }

    public void onRemovedIdentifier(Gene gene, Xref removed) {
        checkObject(gene);
        removedCount++;
    }

    public void onAddedXref(Gene gene, Xref added) {
        checkObject(gene);
        additionCount++;
    }

    public void onRemovedXref(Gene gene, Xref removed) {
        checkObject(gene);
        removedCount++;
    }

    public void onAddedAlias(Gene gene, Alias added) {
        checkObject(gene);
        additionCount++;
    }

    public void onRemovedAlias(Gene gene, Alias removed) {
        checkObject(gene);
        removedCount++;
    }

    public void onAddedChecksum(Gene gene, Checksum added) {
        checkObject(gene);
        additionCount++;
    }

    public void onRemovedChecksum(Gene gene, Checksum removed) {
        checkObject(gene);
        removedCount++;
    }
}
