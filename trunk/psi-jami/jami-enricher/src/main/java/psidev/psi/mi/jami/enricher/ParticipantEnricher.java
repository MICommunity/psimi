package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;

/**
 * Sub enrichers: Protein, CvTerm, Feature, Bioactive3Entity
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface ParticipantEnricher <P extends Participant, F extends Feature<P,F>> extends MIEnricher<P>{

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

    /**
     * Sets the new enricher for BioactiveEntities
     * @param bioactiveEntityEnricher   The enricher to use for BioactiveEntities. Can be null.
     */
    public void setBioactiveEntityEnricher(BioactiveEntityEnricher bioactiveEntityEnricher);

    /**
     * The current enricher used for BioactiveEntities.
     * If null, BioactiveEntities are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public BioactiveEntityEnricher getBioactiveEntityEnricher();


    /**
     * Sets the new enricher for genes
     * @param geneEnricher   The enricher to use for genes. Can be null.
     */
    public void setGeneEnricher(GeneEnricher geneEnricher);

    /**
     * The current enricher used for genes.
     * If null, genes are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public GeneEnricher getGeneEnricher();

    /**
     * Sets the new enricher for interactors
     * @param geneEnricher   The enricher to use for basic interactors that are not proteins, bioactive entities or genes. Can be null.
     */
    public void setBasicInteractorEnricher(InteractorEnricher<Interactor> geneEnricher);

    /**
     * The current enricher used for basic interactors.
     * If null, basic interactors are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public InteractorEnricher<Interactor> getBasicInteractorEnricher();

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
