package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Minimal updater for feature evidence
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalFeatureEvidenceUpdater extends MinimalFeatureEvidenceEnricher {

    private MinimalFeatureUpdater<FeatureEvidence> minimalUpdater;

    public MinimalFeatureEvidenceUpdater(){
        super();
        this.minimalUpdater = new MinimalFeatureUpdater<FeatureEvidence>();
    }

    protected MinimalFeatureEvidenceUpdater(MinimalFeatureUpdater<FeatureEvidence> minimalUpdater){
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal feature updater is required");
        }
        this.minimalUpdater = minimalUpdater;
    }

    @Override
    protected void processMinimalUpdates(FeatureEvidence featureToEnrich) throws EnricherException {
        this.minimalUpdater.processMinimalUpdates(featureToEnrich);
    }

    @Override
    protected void processDetectionMethods(FeatureEvidence featureToEnrich, FeatureEvidence source) throws EnricherException {
        mergeDetectionMethods(featureToEnrich, featureToEnrich.getDetectionMethods(), source.getDetectionMethods(), true);
        processDetectionMethods(featureToEnrich);
    }
}
