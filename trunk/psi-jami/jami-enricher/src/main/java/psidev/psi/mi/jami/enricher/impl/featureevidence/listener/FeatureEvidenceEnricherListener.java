package psidev.psi.mi.jami.enricher.impl.featureevidence.listener;

import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:56
 */
public interface FeatureEvidenceEnricherListener {

    public void onFeatureEvidenceEnriched(FeatureEvidence featureEvidence, String status);
}
