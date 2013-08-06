package psidev.psi.mi.jami.enricher.impl.interaction.listener;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class InteractionEnricherStatisticsWriter
        extends StatisticsWriter<Interaction>
        implements InteractionEnricherListener {


    private static final String OBJECT = "Interaction";

    public InteractionEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, OBJECT);
    }

    public InteractionEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, OBJECT);
    }

    public InteractionEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, OBJECT);
    }

    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message){
        onObjectEnriched(interaction , status , message);
    }
}
