package psidev.psi.mi.jami.enricher.listener.impl.writer;


import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.writer.EnricherStatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class InteractionEnricherStatisticsWriter<I extends Interaction>
        extends EnricherStatisticsWriter<I>
        implements InteractionEnricherListener<I> {


    private static final String FILE_NAME = "Interaction";
    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onShortNameUpdate(I interaction, String oldName) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onUpdatedDateUpdate(I interaction, Date oldUpdate) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onCreatedDateUpdate(I interaction, Date oldCreated) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onInteractionTypeUpdate(I interaction, CvTerm oldType) {
        checkObject(interaction);
        incrementUpdateCount();
    }

    public void onAddedParticipant(I interaction, Participant addedParticipant) {
        checkObject(interaction);
        incrementAdditionCount();
    }

    public void onRemovedParticipant(I interaction, Participant removedParticipant) {
        checkObject(interaction);
        incrementRemovedCount();
    }

    public void onAddedAnnotation(I o, Annotation added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedAnnotation(I o, Annotation removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedChecksum(I interactor, Checksum added) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onRemovedChecksum(I interactor, Checksum removed) {
        checkObject(interactor);
        incrementRemovedCount();
    }

    public void onAddedIdentifier(I o, Xref added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedIdentifier(I o, Xref removed) {
        checkObject(o);
        incrementRemovedCount();
    }

    public void onAddedXref(I o, Xref added) {
        checkObject(o);
        incrementAdditionCount();
    }

    public void onRemovedXref(I o, Xref removed) {
        checkObject(o);
        incrementRemovedCount();
    }


    // ================================================================
}
