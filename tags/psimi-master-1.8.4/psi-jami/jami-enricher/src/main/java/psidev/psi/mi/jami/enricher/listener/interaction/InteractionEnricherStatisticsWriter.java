package psidev.psi.mi.jami.enricher.listener.interaction;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 * A statistics logger which records changes made by the enricher.
 * Each addition, removal or update is counted and, upon the completion of the enrichment of the object,
 * is logged in either a file of successes or failures depending on the enrichmentStatus.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class InteractionEnricherStatisticsWriter
        extends StatisticsWriter<Interaction>
        implements InteractionEnricherListener {


    private static final String jamiObject = "Interaction";
    /**
     * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter() throws IOException {
        super(jamiObject);
    }

    /**
     * Creates the files from the provided seed file name with 'success' and 'failure' appended.
     * @param fileName          The seed to base the names of the files on.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, jamiObject);
    }

    /**
     * Uses the provided names to create the files for successful and failed enrichment logging.
     * @param successFileName   The exact name for the file to log successful enrichments in
     * @param failureFileName   The exact name for the file to log failed enrichments in
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, jamiObject);
    }

    /**
     * Uses the exact files provided to log successful and failed enrichments.
     * @param successFile       The file to log successful enrichments in
     * @param failureFile       The file to log failed enrichments in.
     * @throws IOException      Thrown if a problem is encountered with file location.
     */
    public InteractionEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, jamiObject);
    }


    // ================================================================


    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message){
        onObjectEnriched(interaction , status , message);
    }
}
