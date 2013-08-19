package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MaximumFeatureEvidenceUpdater
        extends BasicFeatureUpdater<FeatureEvidence>{

    /**
     * Processes the specific details of the feature which are not delegated to a subEnricher.
     * @param featureToEnrich       The feature being enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    @Override
    public void processFeature(FeatureEvidence featureToEnrich)
            throws EnricherException {
        super.processFeature(featureToEnrich);
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrichCvTerms(featureToEnrich.getDetectionMethods());
    }
}
