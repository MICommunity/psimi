package psidev.psi.mi.jami.enricher.listener.impl.writer;


import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.*;

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
public class ParticipantEvidenceEnricherStatisticsWriter<P extends ParticipantEvidence>
        extends ParticipantEnricherStatisticsWriter<P>
        implements ParticipantEvidenceEnricherListener<P> {


    private static final String FILE_NAME = "Participant_evidence";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEvidenceEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEvidenceEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEvidenceEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEvidenceEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onExperimentalRoleUpdate(P participant, CvTerm oldType) {
        checkObject(participant);
        incrementUpdateCount();
    }

    public void onExpressedInUpdate(P participant, Organism oldOrganism) {
        checkObject(participant);
        incrementUpdateCount();
    }

    public void onAddedIdentificationMethod(P participant, CvTerm added) {
        checkObject(participant);
        incrementAdditionCount();
    }

    public void onRemovedIdentificationMethod(P participant, CvTerm removed) {

    }

    public void onAddedExperimentalPreparation(P participant, CvTerm added) {
        checkObject(participant);
        incrementAdditionCount();
    }

    public void onRemovedExperimentalPreparation(P participant, CvTerm removed) {
        checkObject(participant);
        incrementRemovedCount();
    }

    public void onAddedConfidence(P o, Confidence added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedConfidence(P o, Confidence removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedParameter(P o, Parameter added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedParameter(P o, Parameter removed) {
        checkObject(o);
        incrementRemovedCount();
    }


    // ================================================================
}
