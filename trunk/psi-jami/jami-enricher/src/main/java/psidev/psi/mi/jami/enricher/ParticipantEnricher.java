package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface ParticipantEnricher <T extends Participant> {

    public void enrichParticipant(T participantToEnrich) throws EnricherException;

    public void setParticipantListener(ParticipantEnricherListener listener);
    public ParticipantEnricherListener getParticipantEnricherListener();

    public void setProteinEnricher(ProteinEnricher proteinEnricher);
    public ProteinEnricher getProteinEnricher();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();

    public void setFeatureEnricher(FeatureEnricher featureEnricher);
    public FeatureEnricher getFeatureEnricher();

}
