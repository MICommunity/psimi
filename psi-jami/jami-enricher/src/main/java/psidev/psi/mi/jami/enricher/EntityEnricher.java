package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

/**
 * Sub enrichers: Protein, CvTerm, Feature, Bioactive3Entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface EntityEnricher<P extends Entity, F extends Feature> extends MIEnricher<P>{

    /**
     * The current enricher used for general interactors. If null, interactors are not being enriched.
     * @return  The new enricher for general interactors.
     */
    public CompositeInteractorEnricher getInteractorEnricher();

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
    public EntityEnricherListener getParticipantEnricherListener();

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher);

    public void setFeatureEnricher(FeatureEnricher<F> enricher);

    public void setParticipantEnricherListener(EntityEnricherListener listener);

}
