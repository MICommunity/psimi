package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Full enricher for feature evidences
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEvidenceEnricher extends FullFeatureEnricher<FeatureEvidence> {

    private MinimalFeatureEvidenceEnricher minimalEnricher;

    public FullFeatureEvidenceEnricher(){
        super();
        this.minimalEnricher = new MinimalFeatureEvidenceEnricher();
    }

    @Override
    protected void processMinimalUpdates(FeatureEvidence objectToEnrich, FeatureEvidence objectSource) throws EnricherException {
        this.minimalEnricher.processMinimalUpdates(objectToEnrich, objectSource);
    }
}
