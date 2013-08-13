package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MaximumFeatureEvidenceEnricher
        extends AbstractFeatureEnricher<FeatureEvidence>{

    @Override
    public void processFeature(FeatureEvidence featureToEnrich)
            throws EnricherException {
        super.processFeature(featureToEnrich);
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrichCvTerms(featureToEnrich.getDetectionMethods());
    }

}
