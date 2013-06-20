package psidev.psi.mi.jami.enricher.impl.featureevidence;

import psidev.psi.mi.jami.enricher.FeatureEvidenceEnricher;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/06/13
 */
public class MinimumFeatureEvidenceEnricher
        extends AbstractFeatureEvidenceEnricher
        implements FeatureEvidenceEnricher {


    @Override
    protected boolean processFeatureEvidence(FeatureEvidence featureEvidenceToEnrich) {
        return false;
    }
}
