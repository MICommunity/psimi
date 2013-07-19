package psidev.psi.mi.jami.enricher.impl.participant.listener;

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
public class ParticipantEnricherStatisticsWriter
        extends StatisticsWriter<Participant>
        implements ParticipantEnricherListener {


    public ParticipantEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, "Participant");
    }

    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message){
        onObjectEnriched(participant , status , message);
    }
}
