package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.impl.EnricherStatisticsWriter;
import psidev.psi.mi.jami.enricher.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;

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
public class OrganismEnricherStatisticsWriter
        extends EnricherStatisticsWriter<Organism>
        implements OrganismEnricherListener {


    private static final String FILE_NAME = "Organism";
    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public OrganismEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public OrganismEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public OrganismEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public OrganismEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
    }


    // ================================================================

    public void onCommonNameUpdate(Organism organism, String oldCommonName) {
        checkObject(organism);
        incrementUpdateCount();
    }

    public void onScientificNameUpdate(Organism organism, String oldScientificName) {
        checkObject(organism);
        incrementUpdateCount();
    }

    public void onTaxidUpdate(Organism organism, String oldTaxid) {
        checkObject(organism);
        incrementUpdateCount();
    }

    public void onAddedAlias(Organism organism, Alias added) {
        checkObject(organism);
        incrementAdditionCount();
    }

    public void onRemovedAlias(Organism organism, Alias removed) {
        checkObject(organism);
        incrementRemovedCount();
    }
}
