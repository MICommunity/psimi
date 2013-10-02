package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.GeneEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherStatisticsWriter
        extends EnricherStatisticsWriter<Gene>
        implements GeneEnricherListener {


    public static final String FILE_NAME = "Gene";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public GeneEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public GeneEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================

    public void onShortNameUpdate(Gene gene, String oldShortName) {
        checkObject(gene);
        incrementUpdateCount();
    }

    public void onFullNameUpdate(Gene gene, String oldFullName) {
        checkObject(gene);
        incrementUpdateCount();
    }

    public void onAddedOrganism(Gene gene) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onAddedInteractorType(Gene gene) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onAddedIdentifier(Gene gene, Xref added) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onRemovedIdentifier(Gene gene, Xref removed) {
        checkObject(gene);
        incrementRemovedCount();
    }

    public void onAddedXref(Gene gene, Xref added) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onRemovedXref(Gene gene, Xref removed) {
        checkObject(gene);
        incrementRemovedCount();
    }

    public void onAddedAlias(Gene gene, Alias added) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onRemovedAlias(Gene gene, Alias removed) {
        checkObject(gene);
        incrementRemovedCount();
    }

    public void onAddedChecksum(Gene gene, Checksum added) {
        checkObject(gene);
        incrementAdditionCount();
    }

    public void onRemovedChecksum(Gene gene, Checksum removed) {
        checkObject(gene);
        incrementRemovedCount();
    }

    public void onAddedAnnotation(Gene o, Annotation added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedAnnotation(Gene o, Annotation removed) {
        checkObject(o);
        incrementRemovedCount();
    }
}
