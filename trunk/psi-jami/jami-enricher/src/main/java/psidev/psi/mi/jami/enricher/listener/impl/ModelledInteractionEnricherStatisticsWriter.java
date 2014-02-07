package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
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
public class ModelledInteractionEnricherStatisticsWriter<I extends ModelledInteraction>
        extends InteractionEnricherStatisticsWriter<I>
        implements ModelledInteractionEnricherListener<I> {


    private static final String FILE_NAME = "modelled_Interaction";
    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ModelledInteractionEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ModelledInteractionEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ModelledInteractionEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public ModelledInteractionEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onAddedCooperativeEffect(I interaction, CooperativeEffect added) {
        checkObject(interaction);
        incrementAdditionCount();
    }

    public void onRemovedCooperativeEffect(I interaction, CooperativeEffect removed) {

    }

    public void onAddedInteractionEvidence(I interaction, InteractionEvidence added) {
        checkObject(interaction);
        incrementAdditionCount();
    }

    public void onRemovedInteractionEvidence(I interaction, InteractionEvidence removed) {
        checkObject(interaction);
        incrementRemovedCount();
    }

    public void onSourceUpdate(I interaction, Source oldSource) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onAddedConfidence(I o, Confidence added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedConfidence(I o, Confidence removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedParameter(I o, Parameter added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedParameter(I o, Parameter removed) {
        checkObject(o);
        incrementRemovedCount();
    }


    // ================================================================
}
