package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Sub enrichers: Protein, CvTerm, Feature, Bioactive3Entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface ParticipantEnricher <P extends Entity, F extends Feature> extends MIEnricher<P>{

    /**
     * Sets the enricher for general interactors. If null, interactors will not be enriched.
     * @param proteinEnricher   The enricher to use for general interactors. Can be null.
     */
    public void setInteractorEnricher(CompositeInteractorEnricher proteinEnricher);

    /**
     * The current enricher used for general interactors. If null, interactors are not being enriched.
     * @return  The new enricher for general interactors.
     */
    public CompositeInteractorEnricher getInteractorEnricher();

    /**
     * Sets the enricher for CvTerms. If null, cvTerms are not being enriched.
     * @param cvTermEnricher    The new enricher for CvTerms
     */
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher);

    /**
     * The current CvTerm enricher, If null, CvTerms will not be enriched.
     * @return  The new enricher for CvTerms. Can be null.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher();

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

    /**
     * Sets the listener for Participant events. If null, events will not be reported.
     * @param listener  The listener to use. Can be null.
     */
    public void setParticipantListener(ParticipantEnricherListener listener);

    /**
     * The current listener that participant changes are reported to.
     * If null, events are not being reported.
     * @return  TThe current listener. Can be null.
     */
    public ParticipantEnricherListener getParticipantEnricherListener();

}
