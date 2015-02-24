package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.ModelledInteractionEnricher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * Minimal enricher for modelled interactions
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalModelledInteractionEnricher<I extends ModelledInteraction> extends MinimalInteractionEnricher<I>
        implements ModelledInteractionEnricher<I> {

    private SourceEnricher sourceEnricher = null;

    /**
     * Strategy for the Interaction enrichment.
     * This method can be overwritten to change how the interaction is enriched.
     * @param interactionToEnrich   The interaction to be enriched.
     */
    protected void processOtherProperties(I interactionToEnrich) throws EnricherException {

        //PROCESS source
        processSource(interactionToEnrich);
    }

    /**
     * The sourceEnricher which is currently being used for the enriching or updating of sources.
     * @return The source enricher. Can be null.
     */
    public SourceEnricher getSourceEnricher() {
        return sourceEnricher;
    }

    /**
     * Sets the sourceEnricher to be used.
     * @param sourceEnricher The source enricher to be used. Can be null.
     */
    public void setSourceEnricher(SourceEnricher sourceEnricher) {
        this.sourceEnricher = sourceEnricher;
    }

    protected void processSource(I interactionToEnrich) throws EnricherException {
        if( getSourceEnricher() != null
                && interactionToEnrich.getSource() != null )
            getSourceEnricher().enrich(interactionToEnrich.getSource());
    }

    @Override
    protected void processOtherProperties(I objectToEnrich, I objectSource) throws EnricherException {
        // source
        processSource(objectToEnrich, objectSource);
    }

    protected void processSource(I objectToEnrich, I objectSource) throws EnricherException {
        if (objectSource.getSource() != null && objectToEnrich.getSource() == null){
            objectToEnrich.setSource(objectSource.getSource());
            if (getInteractionEnricherListener() instanceof ModelledInteractionEnricher){
                ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onSourceUpdate(objectToEnrich, null);
            }
        }

        processSource(objectToEnrich);
    }
}
