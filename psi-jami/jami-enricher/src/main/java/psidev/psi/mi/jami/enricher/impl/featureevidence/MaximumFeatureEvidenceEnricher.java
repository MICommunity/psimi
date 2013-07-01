package psidev.psi.mi.jami.enricher.impl.featureevidence;


import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 * Time: 13:24
 */
@Deprecated
public class MaximumFeatureEvidenceEnricher
        extends MinimumFeatureEvidenceEnricher
{


    @Override
    protected boolean processFeatureEvidence(FeatureEvidence featureEvidenceToEnrich) {
        return false;
    }


}
