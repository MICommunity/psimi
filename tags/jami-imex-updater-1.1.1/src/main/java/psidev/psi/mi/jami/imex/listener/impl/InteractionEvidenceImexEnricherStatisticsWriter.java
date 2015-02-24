package psidev.psi.mi.jami.imex.listener.impl;


import psidev.psi.mi.jami.enricher.listener.impl.writer.InteractionEvidenceEnricherStatisticsWriter;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.imex.listener.InteractionImexEnricherListener;

import java.io.IOException;
import java.util.Collection;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 */
public class InteractionEvidenceImexEnricherStatisticsWriter
        extends InteractionEvidenceEnricherStatisticsWriter
        implements InteractionImexEnricherListener {

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceImexEnricherStatisticsWriter() throws IOException {
        super();
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceImexEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceImexEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    // ================================================================

    public void onImexIdConflicts(InteractionEvidence interaction, Collection<Xref> conflictingXrefs) {
        super.onEnrichmentError(interaction, "The interaction "+interaction+" has "+conflictingXrefs.size()+" IMEx primary references and only one" +
                "is allowed", null);
    }

    public void onImexIdAssigned(InteractionEvidence interaction, String imex) {
        checkObject(imex);
        incrementUpdateCount();
    }
}
