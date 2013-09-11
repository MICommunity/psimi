package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.*;
import psidev.psi.mi.jami.enricher.listener.impl.EnricherStatisticsWriter;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.io.*;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherStatisticsWriter
        extends EnricherStatisticsWriter<CvTerm>
        implements CvTermEnricherListener {

    private static final String FILE_NAME = "cv_term";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public CvTermEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public CvTermEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public CvTermEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public CvTermEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================


    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        checkObject(cv);
        incrementUpdateCount();
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        checkObject(cv);
        incrementUpdateCount();
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        checkObject(cv);
        incrementUpdateCount();
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        checkObject(cv);
        incrementUpdateCount();
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        checkObject(cv);
        incrementUpdateCount();
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        checkObject(cv);
        incrementAdditionCount();
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        checkObject(cv);
        incrementRemovedCount();
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        checkObject(cv);
        incrementAdditionCount();
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        checkObject(cv);
        incrementRemovedCount();
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        checkObject(cv);
        incrementAdditionCount();
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        checkObject(cv);
        incrementRemovedCount();
    }
}
