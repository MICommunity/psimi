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
     * The current enricher used for general interactors. If null, interactors are not being enriched.
     * @return  The new enricher for general interactors.
     */
    public CompositeInteractorEnricher getInteractorEnricher();

    /**
     * The current CvTerm enricher, If null, CvTerms will not be enriched.
     * @return  The new enricher for CvTerms. Can be null.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher();

    /**
     * The current enricher used for features. If null, features are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public FeatureEnricher<F> getFeatureEnricher();

    /**
     * The current listener that participant changes are reported to.
     * If null, events are not being reported.
     * @return  TThe current listener. Can be null.
     */
    public ParticipantEnricherListener getParticipantEnricherListener();

}
