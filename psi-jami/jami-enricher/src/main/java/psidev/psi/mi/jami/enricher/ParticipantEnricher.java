package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.participant.ParticipantEnricherListener;
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

    /**
     * Enriches a single participant.
     * @param participantToEnrich       The participant to be enriched.
     * @throws EnricherException        Thrown if a fetcher encounters a problem
     */
    public void enrichParticipant(P participantToEnrich) throws EnricherException;

    /**
     * Enriches a collection of participants.
     * @param participantsToEnrich      The participants to be enriched
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichParticipants(Collection<P> participantsToEnrich) throws EnricherException;

    /**
     * Sets the enricher for proteins. If null, proteins will not be enriched.
     * @param proteinEnricher   The enricher to use for proteins. Can be null.
     */
    public void setProteinEnricher(ProteinEnricher proteinEnricher);

    /**
     * The current enricher used for proteins. If null, proteins are not being enriched.
     * @return  The new enricher for proteins.
     */
    public ProteinEnricher getProteinEnricher();

    /**
     * Sets the enricher for CvTerms. If null, cvTerms are not being enriched.
     * @param cvTermEnricher    The new enricher for CvTerms
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);

    /**
     * The current CvTerm enricher, If null, CvTerms will not be enriched.
     * @return  The new enricher for CvTerms. Can be null.
     */
    public CvTermEnricher getCvTermEnricher();


    /**
     * Sets the new enricher for Features.
     * @param featureEnricher   The enricher to use for features. Can be null.
     */
    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher);

    /**
     * The current enricher used for features. If null, features are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public FeatureEnricher getFeatureEnricher();


    public void setParticipantListener(ParticipantEnricherListener listener);
    public ParticipantEnricherListener getParticipantEnricherListener();

}
