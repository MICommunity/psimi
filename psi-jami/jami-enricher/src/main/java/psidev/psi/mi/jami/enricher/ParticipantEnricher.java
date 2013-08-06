package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface ParticipantEnricher <P extends Participant , F extends Feature> {

    public void enrichParticipant(P participantToEnrich) throws EnricherException;
    public void enrichParticipants(Collection<P> participantsToEnrich) throws EnricherException;




    public void setParticipantListener(ParticipantEnricherListener listener);
    public ParticipantEnricherListener getParticipantEnricherListener();

    public void setProteinEnricher(ProteinEnricher proteinEnricher);
    public ProteinEnricher getProteinEnricher();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();

    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher);
    public FeatureEnricher getFeatureEnricher();

}
