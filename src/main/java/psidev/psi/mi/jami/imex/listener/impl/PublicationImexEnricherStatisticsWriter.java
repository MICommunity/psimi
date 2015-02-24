package psidev.psi.mi.jami.imex.listener.impl;

import psidev.psi.mi.jami.bridges.imex.PublicationStatus;
import psidev.psi.mi.jami.enricher.listener.impl.writer.PublicationEnricherStatisticsWriter;
import psidev.psi.mi.jami.imex.listener.PublicationImexEnricherListener;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 */
public class PublicationImexEnricherStatisticsWriter
        extends PublicationEnricherStatisticsWriter implements PublicationImexEnricherListener{

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationImexEnricherStatisticsWriter() throws IOException {
        super();
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationImexEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationImexEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws java.io.IOException      Thrown if a problem is encountered with file location.
     */
    public PublicationImexEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }

    public void onImexIdConflicts(Publication originalPublication, Collection<Xref> conflictingXrefs) {
        super.onEnrichmentError(originalPublication, "The publication "+originalPublication+" has "+conflictingXrefs.size()
                +" IMEx primary references and only one is allowed.", null);
    }

    public void onMissingImexId(Publication publication) {
        super.onEnrichmentError(publication, "The publication "+publication+" does not have any IMEx primary reference and " +
                "cannot be updated.", null);
    }

    public void onCurationDepthUpdated(Publication publication, CurationDepth oldDepth) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onImexAdminGroupUpdated(Publication publication, Source oldSource) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onImexStatusUpdated(Publication publication, PublicationStatus oldStatus) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onImexPublicationIdentifierSynchronized(Publication publication) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onPublicationAlreadyRegisteredInImexCentral(Publication publication, String imex) {
        super.onEnrichmentError(publication, "The publication "+publication+" is already registered in IMEx central with IMEx "+imex, null);
    }

    public void onPublicationRegisteredInImexCentral(Publication publication) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onPublicationWhichCannotBeRegistered(Publication publication) {
        super.onEnrichmentError(publication, "The publication "+publication+" cannot be registered in IMEx central. It does not have a valid pubmed id or " +
                "it is not eligible for IMEx", null);
    }

    public void onPublicationNotEligibleForImex(Publication publication) {
        super.onEnrichmentError(publication, "The publication "+publication+" cannot be registered in IMEx central. It does not have a valid pubmed id or " +
                "it is not eligible for IMEx", null);
    }

    public void onImexIdAssigned(Publication publication, String imex) {
        checkObject(publication);
        incrementUpdateCount();
    }

    public void onImexIdNotRecognized(Publication publication, String imex) {
        super.onEnrichmentError(publication, "The publication "+publication+" does have an IMEx identifier which is not recognized in IMEx central "+imex, null);
    }
}
