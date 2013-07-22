package psidev.psi.mi.jami.enricher.impl.interaction.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
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



    public InteractionEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, "Interaction");
    }

    public void onInteractionEnriched(Interaction interaction, EnrichmentStatus status, String message){
        onObjectEnriched(interaction , status , message);
    }
}
