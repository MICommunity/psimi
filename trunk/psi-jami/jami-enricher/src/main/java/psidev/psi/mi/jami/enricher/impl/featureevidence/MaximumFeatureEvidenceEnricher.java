package psidev.psi.mi.jami.enricher.impl.featureevidence;

import psidev.psi.mi.jami.enricher.FeatureEvidenceEnricher;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 * Time: 13:24
 */
public class MaximumFeatureEvidenceEnricher
        extends AbstractFeatureEvidenceEnricher
        implements FeatureEvidenceEnricher {


    @Override
    protected boolean processFeatureEvidence(FeatureEvidence featureEvidenceToEnrich) {
        return false;
    }


}
