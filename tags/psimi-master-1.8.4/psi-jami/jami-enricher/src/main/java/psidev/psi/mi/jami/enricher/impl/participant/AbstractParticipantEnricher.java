package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.participant.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.EnricherUtil;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public abstract class AbstractParticipantEnricher<P extends Participant , F extends Feature>
        implements ParticipantEnricher<P , F>  {


    private ParticipantEnricherListener listener;

    private ProteinEnricher proteinEnricher;
    private CvTermEnricher cvTermEnricher;
    private FeatureEnricher<F> featureEnricher;
    private BioactiveEntityEnricher bioactiveEntityEnricher;


    public void enrichParticipants(Collection<P> participantsToEnrich) throws EnricherException {
        for(Participant participant : participantsToEnrich){
            enrichParticipant((P) participant);
        }
    }


    public void enrichParticipant(P participantToEnrich) throws EnricherException {

        if(participantToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null participant.");

        // == CvTerm BioRole =========================================================
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrichCvTerm(participantToEnrich.getBiologicalRole());

        // Prepare Features
        if( getFeatureEnricher() != null )
            getFeatureEnricher().setFeaturesToEnrich(participantToEnrich);



        // Enrich Interactor
        CvTerm interactorType = participantToEnrich.getInteractor().getInteractorType();

        processParticipant(participantToEnrich);

        if(interactorType.getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)
                || interactorType.getShortName().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR)){

        }




        if(interactorType.getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)
                || interactorType.getShortName().equalsIgnoreCase(Protein.PROTEIN)){
            if(getProteinEnricher() != null){
                if(participantToEnrich.getInteractor() instanceof Protein){
                    getProteinEnricher().enrichProtein( (Protein) participantToEnrich.getInteractor() );
                } else {
                    if(listener != null) listener.onEnrichmentComplete(participantToEnrich ,
                            EnrichmentStatus.FAILED,
                            "Found interactor of type "+interactorType.getShortName()+
                                    " ("+interactorType.getMIIdentifier()+") "+
                                    "but was not an instance of 'Protein', " +
                                    "was "+participantToEnrich.getInteractor().getClass()+" instead.");
                    return;
                }
            }
        }
        if( interactorType.getMIIdentifier().equalsIgnoreCase(BioactiveEntity.BIOACTIVE_ENTITY_MI)
                || interactorType.getShortName().equalsIgnoreCase(BioactiveEntity.BIOACTIVE_ENTITY )){
            if(getBioactiveEntityEnricher() != null){
                if(participantToEnrich.getInteractor() instanceof BioactiveEntity){
                    getBioactiveEntityEnricher().enrichBioactiveEntity(
                            (BioactiveEntity) participantToEnrich.getInteractor() );
                } else {
                    if(listener != null) listener.onEnrichmentComplete(participantToEnrich ,
                            EnrichmentStatus.FAILED,
                            "Found interactor of type "+interactorType.getShortName()+
                                    " ("+interactorType.getMIIdentifier()+") "+
                                    "but was not an instance of 'BioactiveEntity', " +
                                    "was "+participantToEnrich.getInteractor().getClass()+" instead.");
                    return;
                }
            }
        }

        // Enrich Features
        if( getFeatureEnricher() != null )
                getFeatureEnricher().enrichFeatures(participantToEnrich.getFeatures());

        if( getParticipantEnricherListener() != null )
            getParticipantEnricherListener().onEnrichmentComplete(participantToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    protected abstract void processParticipant(P participantToEnrich) throws EnricherException;

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
        EnricherUtil.linkFeatureEnricherToProteinEnricher(getFeatureEnricher(), proteinEnricher);
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
        EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher, getProteinEnricher());
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
}
