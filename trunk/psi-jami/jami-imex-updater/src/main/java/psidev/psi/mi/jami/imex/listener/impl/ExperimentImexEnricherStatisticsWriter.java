package psidev.psi.mi.jami.imex.listener.impl;

import psidev.psi.mi.jami.enricher.listener.impl.writer.ExperimentEnricherStatisticsWriter;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.imex.listener.ExperimentImexEnricherListener;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 */
public class ExperimentImexEnricherStatisticsWriter
        extends ExperimentEnricherStatisticsWriter
        implements ExperimentImexEnricherListener {

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentImexEnricherStatisticsWriter() throws IOException {
        super();
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentImexEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentImexEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ExperimentImexEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onImexIdConflicts(Experiment originalExperiment, Collection<Xref> conflictingXrefs) {
        super.onEnrichmentError(originalExperiment, "The experiment "+originalExperiment+" has "+conflictingXrefs.size()+" IMEx primary references and only one" +
                "is allowed", null);
    }

    public void onImexIdAssigned(Experiment experiment, String imex) {
        checkObject(imex);
        incrementUpdateCount();
    }
}
