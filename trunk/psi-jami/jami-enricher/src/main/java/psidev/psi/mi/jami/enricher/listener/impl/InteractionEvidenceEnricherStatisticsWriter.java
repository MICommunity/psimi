package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
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
public class InteractionEvidenceEnricherStatisticsWriter
        extends InteractionEnricherStatisticsWriter<InteractionEvidence>
        implements InteractionEvidenceEnricherListener {


    private static final String FILE_NAME = "Interaction_evidence";
    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEvidenceEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onExperimentUpdate(InteractionEvidence interaction, Experiment oldExperiment) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onAddedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet added) {
        checkObject(interaction);
        incrementAdditionCount();
    }

    public void onRemovedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet removed) {
        checkObject(interaction);
        incrementRemovedCount();
    }

    public void onInferredPropertyUpdate(InteractionEvidence interaction, boolean oldInferred) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onNegativePropertyUpdate(InteractionEvidence interaction, boolean negative) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onAddedConfidence(InteractionEvidence o, Confidence added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedConfidence(InteractionEvidence o, Confidence removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedParameter(InteractionEvidence o, Parameter added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedParameter(InteractionEvidence o, Parameter removed) {
        checkObject(o);
        incrementRemovedCount();
    }


    // ================================================================
}
