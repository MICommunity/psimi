package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.AbstractFeatureEnricher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class FeatureEvidenceEnricher extends AbstractFeatureEnricher<FeatureEvidence> {

    @Override
    public void processFeature(FeatureEvidence featureToEnrich) throws EnricherException {
        super.processFeature(featureToEnrich);

        for(CvTerm cvTerm : featureToEnrich.getDetectionMethods()){
            if(getCvTermEnricher() != null) getCvTermEnricher().enrichCvTerm(cvTerm);
        }

    }
}
