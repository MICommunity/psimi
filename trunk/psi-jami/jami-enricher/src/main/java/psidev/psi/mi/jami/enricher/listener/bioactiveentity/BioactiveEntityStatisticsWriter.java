package psidev.psi.mi.jami.enricher.listener.bioactiveentity;

import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public class BioactiveEntityStatisticsWriter
        extends StatisticsWriter<BioactiveEntity>
        implements BioactiveEntityEnricherListener {

    public static final String FILE_NAME = "bioactive_entity";

    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public BioactiveEntityStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public BioactiveEntityStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public BioactiveEntityStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public BioactiveEntityStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================

    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) {
        checkObject(bioactiveEntity);
        incrementUpdateCount();
    }

    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
        checkObject(bioactiveEntity);
        incrementUpdateCount();
    }

    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) {
        checkObject(bioactiveEntity);
        incrementUpdateCount();
    }

    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) {
        checkObject(bioactiveEntity);
        incrementUpdateCount();
    }


    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        checkObject(interactor);
        incrementUpdateCount();
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        checkObject(interactor);
        incrementUpdateCount();
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onAddedInteractorType(BioactiveEntity interactor) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) {
        checkObject(interactor);
        incrementRemovedCount();
    }

    public void onAddedXref(BioactiveEntity interactor, Xref added) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onRemovedXref(BioactiveEntity interactor, Xref removed) {
        checkObject(interactor);
        incrementRemovedCount();
    }

    public void onAddedAlias(BioactiveEntity interactor, Alias added) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) {
        checkObject(interactor);
        incrementRemovedCount();
    }

    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) {
        checkObject(interactor);
        incrementAdditionCount();
    }

    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) {
        checkObject(interactor);
        incrementRemovedCount();
    }
}
