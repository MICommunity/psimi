package psidev.psi.mi.jami.enricher.listener.impl.writer;


import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.writer.EnricherStatisticsWriter;
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
public class ParticipantEnricherStatisticsWriter<P extends Entity>
        extends EnricherStatisticsWriter<P>
        implements ParticipantEnricherListener<P> {


    private static final String FILE_NAME = "Participant";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public ParticipantEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onBiologicalRoleUpdate(P participant, CvTerm oldType) {
        checkObject(participant);
        incrementUpdateCount();
    }

    public void onStoichiometryUpdate(P participant, Stoichiometry oldStoichiometry) {
        checkObject(participant);
        incrementUpdateCount();
    }

    public void onAddedCausalRelationship(P participant, CausalRelationship added) {
        checkObject(participant);
        incrementAdditionCount();
    }

    public void onRemovedCausalRelationship(P participant, CausalRelationship removed) {
        checkObject(participant);
        incrementRemovedCount();
    }

    public void onAddedFeature(P participant, Feature added) {
        checkObject(participant);
        incrementAdditionCount();
    }

    public void onRemovedFeature(P participant, Feature removed) {
        checkObject(participant);
        incrementRemovedCount();
    }

    public void onAddedAlias(P o, Alias added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedAlias(P o, Alias removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedAnnotation(P o, Annotation added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedAnnotation(P o, Annotation removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        checkObject(entity);
        incrementUpdateCount();
    }

    public void onAddedXref(P o, Xref added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedXref(P o, Xref removed) {
        checkObject(o);
        incrementRemovedCount();
    }


    // ================================================================
}
