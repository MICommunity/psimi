package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeEntityEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;

import java.util.Collection;

/**
 * The enricher for Interactions which can enrich a single interaction or a collection.
 * The interaction enricher has subEnrichers for participants and cvTerms.
 * It has no fetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalInteractionEnricher<I extends Interaction>
        implements InteractionEnricher<I> {

    private InteractionEnricherListener<I> listener;
    private CompositeEntityEnricher participantEnricher;
    private CvTermEnricher<CvTerm> cvTermEnricher;

    /**
     * Enrichment of a single interaction.
     * @param interactionToEnrich   The interaction which is to be enriched
     * @throws EnricherException    Thrown if a fetcher encounters problems.
     */
    public void enrich(I interactionToEnrich) throws EnricherException {
        if ( interactionToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich null interaction.") ;

        // Enrich interaction type
        processInteractionType(interactionToEnrich);

        // Enrich all participants
        processParticipants(interactionToEnrich);

        // Enrich remaining properties
        processOtherProperties(interactionToEnrich);

        if(listener != null)
            listener.onEnrichmentComplete(interactionToEnrich , EnrichmentStatus.SUCCESS , "Interaction successfully enriched.");
    }

    public void enrich(Collection<I> objects) throws EnricherException {
        if( objects == null )
            throw new IllegalArgumentException("Cannot enrich a null collection of interactions.");
        for(I i : objects){
            enrich(i);
        }
    }

    public void enrich(I objectToEnrich, I objectSource) throws EnricherException {
        if (objectSource == null){
            enrich(objectToEnrich);
        }
        else{
            processMinimalUpdates(objectToEnrich, objectSource);

            // Enrich remaining properties
            processOtherProperties(objectToEnrich, objectSource);

            if(listener != null)
                listener.onEnrichmentComplete(objectToEnrich , EnrichmentStatus.SUCCESS , "Interaction successfully enriched.");
        }
    }

    public void processMinimalUpdates(I objectToEnrich, I objectSource) throws EnricherException {
        // check shortname
        processShortName(objectToEnrich, objectSource);

        // check update date
        processUpdateDate(objectToEnrich, objectSource);

        // check created date
        processCreatedDate(objectToEnrich, objectSource);

        // Enrich interaction type
        processInteractionType(objectToEnrich, objectSource);

        // identifiers
        processIdentifiers(objectToEnrich, objectSource);

        // Enrich all participants
        processParticipants(objectToEnrich, objectSource);
    }

    protected void processCreatedDate(I objectToEnrich, I objectSource) {
        if (objectToEnrich.getCreatedDate() == null && objectSource.getCreatedDate() != null){
             objectToEnrich.setCreatedDate(objectSource.getCreatedDate());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onCreatedDateUpdate(objectToEnrich, null);
            }
        }
    }

    protected void processUpdateDate(I objectToEnrich, I objectSource) {
        if (objectToEnrich.getUpdatedDate() == null && objectSource.getUpdatedDate() != null){
            objectToEnrich.setUpdatedDate(objectSource.getUpdatedDate());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onUpdatedDateUpdate(objectToEnrich, null);
            }
        }
    }

    protected void processShortName(I objectToEnrich, I objectSource) {
        if (objectToEnrich.getShortName() == null && objectSource.getShortName() != null){
            objectToEnrich.setShortName(objectSource.getShortName());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onShortNameUpdate(objectToEnrich, null);
            }
        }
    }

    protected void processOtherProperties(I objectToEnrich, I objectSource) throws EnricherException{
        // do nothing
    }

    protected void processIdentifiers(I objectToEnrich, I objectSource) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getIdentifiers(), objectSource.getIdentifiers(),false, true,
                getInteractionEnricherListener(), getInteractionEnricherListener());
    }

    protected void processParticipants(I objectToEnrich, I objectSource) throws EnricherException{
        EnricherUtils.mergeParticipants(objectToEnrich, objectToEnrich.getParticipants(), objectSource.getParticipants(),
                false, getInteractionEnricherListener(), getParticipantEnricher());

        processParticipants(objectToEnrich);
    }

    protected void processInteractionType(I objectToEnrich, I objectSource) throws EnricherException {

        if (objectToEnrich.getInteractionType() == null && objectSource.getInteractionType() != null){
            objectToEnrich.setInteractionType(objectSource.getInteractionType());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onInteractionTypeUpdate(objectToEnrich, null);
            }
        }

        processInteractionType(objectToEnrich);
    }

    /**
     * The current sub enricher for CvTerms.
     * @param cvTermEnricher The enricher for cvTerms. Can be null.
     */
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    /**
     * Sets the sub enricher for CvTerms.
     * @return  The enricher for CvTerms. Can be null.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher(){
        return cvTermEnricher;
    }

    /**
     * Sets the sub enricher for participants.
     * @param participantEnricher   The enricher for participants. Can be null.
     */
    public void setParticipantEnricher(CompositeEntityEnricher participantEnricher){
        this.participantEnricher = participantEnricher;
    }
    /**
     * The current sub enricher for participants.
     * @return  The enricher for participants. Can be null.
     */
    public CompositeEntityEnricher getParticipantEnricher(){
        return this.participantEnricher;
    }

    /**
     * The listener for changes made to interactions.
     * @return  The listener for interaction changes. Can be null.
     */
    public InteractionEnricherListener<I> getInteractionEnricherListener() {
        return listener;
    }

    /**
     * Sets the listener to be used when interactions are changed.
     * @param listener  The listener for interaction changes. Can be null.
     */
    public void setInteractionEnricherListener(InteractionEnricherListener<I> listener) {
        this.listener = listener;
    }

    protected void processInteractionType(I interactionToEnrich) throws EnricherException {
        if( getCvTermEnricher() != null &&
                interactionToEnrich.getInteractionType() != null)
            getCvTermEnricher().enrich(interactionToEnrich.getInteractionType());
    }

    protected void processParticipants(I interactionToEnrich) throws EnricherException {
        if( getParticipantEnricher() != null )
            getParticipantEnricher().enrich(interactionToEnrich.getParticipants());
    }

    /**
     * The strategy used for enriching the interaction.
     * Can be overwritten to change the behaviour.
     * @param interactionToEnrich   The interaction being enriched.
     * @throws EnricherException    Thrown if a fetcher encounters a problem.
     */
    protected void processOtherProperties(I interactionToEnrich) throws EnricherException {
        // do nothing by default
    }
}
