package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.ModelledInteractionEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Source;

/**
 * Full updater of a modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullModelledInteractionUpdater<I extends ModelledInteraction> extends FullModelledInteractionEnricher<I>{

    public FullModelledInteractionUpdater() {
        super(new FullInteractionUpdater<I, ModelledParticipant, ModelledFeature>());
    }

    @Override
    protected void processConfidences(I objectToEnrich, I objectSource) {
        EnricherUtils.mergeConfidences(objectToEnrich, objectToEnrich.getModelledConfidences(), objectSource.getModelledConfidences(), true,
                (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener ? (ModelledInteractionEnricherListener) getInteractionEnricherListener() : null));
    }

    @Override
    protected void processParameters(I objectToEnrich, I objectSource) {

        EnricherUtils.mergeParameters(objectToEnrich, objectToEnrich.getModelledParameters(), objectSource.getModelledParameters(), true,
                (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener ? (ModelledInteractionEnricherListener) getInteractionEnricherListener() : null));
    }

    @Override
    protected void processCooperativeEffects(I objectToEnrich, I objectSource) {
        mergeCooperativeEffects(objectToEnrich, objectSource, true);
    }

    @Override
    protected void processInteractionEvidences(I objectToEnrich, I objectSource) throws EnricherException {
        mergeInteractionEvidences(objectToEnrich, objectSource, true);

        processInteractionEvidences(objectToEnrich);
    }

    @Override
    protected void processSource(I objectToEnrich, I objectSource) throws EnricherException {
        if (objectSource.getSource() != objectToEnrich.getSource()){
            Source old = objectToEnrich.getSource();
            objectToEnrich.setSource(objectSource.getSource());
            if (getInteractionEnricherListener() instanceof ModelledInteractionEnricher){
                ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onSourceUpdate(objectToEnrich, old);
            }
        }

        processSource(objectToEnrich);
    }
}
