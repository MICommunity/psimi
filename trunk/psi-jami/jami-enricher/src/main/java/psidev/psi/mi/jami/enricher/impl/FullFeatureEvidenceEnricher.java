package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Full enricher for feature evidences
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEvidenceEnricher extends MinimalFeatureEvidenceEnricher {

    /**
     * Processes the specific details of the feature which are not delegated to a subEnricher.
     * @param featureToEnrich       The feature being enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    @Override
    public void enrich(FeatureEvidence featureToEnrich)
            throws EnricherException {
        super.enrich(featureToEnrich);
        // DETECTION METHODS
        processDetectionMethods(featureToEnrich);
    }

    protected void processDetectionMethods(FeatureEvidence featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrich(featureToEnrich.getDetectionMethods());
    }

}
