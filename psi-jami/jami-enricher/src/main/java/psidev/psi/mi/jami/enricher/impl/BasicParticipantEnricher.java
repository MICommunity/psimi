package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class BasicParticipantEnricher<P extends Participant , F extends Feature<P,F>>
        implements ParticipantEnricher<P , F>  {

    private ParticipantEnricherListener listener;
    private ProteinEnricher proteinEnricher;
    private CvTermEnricher cvTermEnricher;
    private GeneEnricher geneEnricher;
    private FeatureEnricher<F> featureEnricher;
    private BioactiveEntityEnricher bioactiveEntityEnricher;
    private InteractorEnricher<Interactor> interactorEnricher;

    public void enrich(Collection<P> participantsToEnrich) throws EnricherException {
        if(participantsToEnrich == null) throw new IllegalArgumentException("Cannot enrich a null collection of participants.");

        for(P participant : participantsToEnrich){
            enrich(participant);
        }
    }

    public void enrich(P participantToEnrich) throws EnricherException{

        if(participantToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null participant.");

        // == CvTerm BioRole =========================================================
        processBiologicalRole(participantToEnrich);

        // == Prepare Features =====================================
        if( getFeatureEnricher() != null )
            getFeatureEnricher().setFeaturesWithRangesToUpdate((Collection<F>)participantToEnrich.getFeatures());
        // == Enrich Features =========================================================
        processFeatures(participantToEnrich);

        // == Enrich Interactor ============================
        processInteractor(participantToEnrich);

        // enrich other properties
        processOtherProperties(participantToEnrich);

        if( getParticipantEnricherListener() != null )
            getParticipantEnricherListener().onEnrichmentComplete(participantToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    protected void processOtherProperties(P participantToEnrich) throws EnricherException {
        // do nothing
    }

    protected void processFeatures(P participantToEnrich) throws EnricherException {
        if( getFeatureEnricher() != null )
                getFeatureEnricher().enrich(participantToEnrich.getFeatures());
    }

    protected void processInteractor(P participantToEnrich) throws EnricherException {
        boolean enrichProteins = getProteinEnricher() != null;
        boolean enrichSmallMolecules = getBioactiveEntityEnricher() != null;
        boolean enrichGenes = getGeneEnricher() != null;
        boolean enrichBasicInteractors = getBasicInteractorEnricher() != null;

        Interactor interactor = participantToEnrich.getInteractor();
        // we can enrich interactors
        if (enrichProteins || enrichSmallMolecules || enrichGenes || enrichBasicInteractors){
            boolean isProtein = interactor instanceof Protein;

            if (isProtein){
                if (enrichProteins){
                    getProteinEnricher().enrich((Protein)interactor);
                }
                else if (enrichBasicInteractors){
                    getBasicInteractorEnricher().enrich(interactor);
                }
            }
            else {
                boolean isSmallMolecule = interactor instanceof BioactiveEntity;

                if (isSmallMolecule){
                    if (enrichSmallMolecules){
                        getBioactiveEntityEnricher().enrich((BioactiveEntity)interactor);
                    }
                    else if (enrichBasicInteractors){
                        getBasicInteractorEnricher().enrich(interactor);
                    }
                }
                else {
                    boolean isGene = interactor instanceof Gene;

                    if (isGene){
                        if (enrichGenes){
                            getGeneEnricher().enrich((Gene)interactor);
                        }
                        else if (enrichBasicInteractors){
                            getBasicInteractorEnricher().enrich(interactor);
                        }
                    }
                    else if (enrichBasicInteractors){
                        getBasicInteractorEnricher().enrich(interactor);
                    }
                }
            }
        }
    }

    protected void processBiologicalRole(P participantToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null && participantToEnrich.getBiologicalRole() != null)
            getCvTermEnricher().enrich(participantToEnrich.getBiologicalRole());
    }

    /**
     * Sets the listener for Participant events. If null, events will not be reported.
     * @param listener  The listener to use. Can be null.
     */
    public void setParticipantListener(ParticipantEnricherListener listener) {
        this.listener = listener;
    }

    /**
     * The current listener that participant changes are reported to.
     * If null, events are not being reported.
     * @return  TThe current listener. Can be null.
     */
    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    /**
     * Sets the enricher for proteins. If null, proteins will not be enriched.
     * @param proteinEnricher   The enricher to use for proteins. Can be null.
     */
    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
        EnricherUtils.linkFeatureEnricherToProteinEnricher(getFeatureEnricher(), proteinEnricher);
    }

    /**
     * The current enricher used for proteins. If null, proteins are not being enriched.
     * @return  The new enricher for proteins.
     */
    public ProteinEnricher getProteinEnricher(){
        return proteinEnricher;
    }

    /**
     * Sets the enricher for CvTerms. If null, cvTerms are not being enriched.
     * @param cvTermEnricher    The new enricher for CvTerms
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    /**
     * The current CvTerm enricher, If null, CvTerms will not be enriched.
     * @return  The new enricher for CvTerms. Can be null.
     */
    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    /**
     * Will attempt to add the featureEnricher as a proteinListener if this is valid.
     * If the proteinEnricher already has a listener, this will be preserved using a listener manager.
     * @param featureEnricher   The enricher to use for features. Can be null.
     */
    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher){
        this.featureEnricher = featureEnricher;
        EnricherUtils.linkFeatureEnricherToProteinEnricher(featureEnricher, getProteinEnricher());
    }

    /**
     * The current enricher used for features. If null, features are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public FeatureEnricher<F> getFeatureEnricher(){
        return featureEnricher;
    }

    /**
     * Sets the new enricher for BioactiveEntities
     * @param bioactiveEntityEnricher   The enricher to use for BioactiveEntities. Can be null.
     */
    public void setBioactiveEntityEnricher(BioactiveEntityEnricher bioactiveEntityEnricher){
        this.bioactiveEntityEnricher = bioactiveEntityEnricher;
    }

    /**
     * The current enricher used for BioactiveEntities.
     * If null, BioactiveEntities are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public BioactiveEntityEnricher getBioactiveEntityEnricher(){
        return this.bioactiveEntityEnricher;
    }


    /**
     * Sets the new enricher for genes
     * @param geneEnricher   The enricher to use for genes. Can be null.
     */
    public void setGeneEnricher(GeneEnricher geneEnricher){
        this.geneEnricher = geneEnricher;
    }

    /**
     * The current enricher used for genes.
     * If null, genes are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public GeneEnricher getGeneEnricher(){
        return this.geneEnricher;
    }

    public void setBasicInteractorEnricher(InteractorEnricher<Interactor> interactorEnricher) {
        this.interactorEnricher = interactorEnricher;
    }

    public InteractorEnricher<Interactor> getBasicInteractorEnricher() {
        return this.interactorEnricher;
    }
}
