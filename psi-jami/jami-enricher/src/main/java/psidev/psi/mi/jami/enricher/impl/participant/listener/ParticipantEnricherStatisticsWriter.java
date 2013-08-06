package psidev.psi.mi.jami.enricher.impl.participant.listener;


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


    private static final String OBJECT = "Participant";

    public ParticipantEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, OBJECT);
    }

    public ParticipantEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, OBJECT);
    }

    public ParticipantEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, OBJECT);
    }


    public void onEnrichmentComplete(Participant participant, EnrichmentStatus status, String message){
        onObjectEnriched(participant , status , message);
    }
}
