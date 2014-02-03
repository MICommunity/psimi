package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEvidenceUpdater extends FullFeatureUpdater<FeatureEvidence> {

    private MinimalFeatureEvidenceUpdater minimalEnricher;

    public FullFeatureEvidenceUpdater(){
        super();
        this.minimalEnricher = new MinimalFeatureEvidenceUpdater();
    }

    @Override
    protected void processMinimalUpdates(FeatureEvidence objectToEnrich, FeatureEvidence objectSource) throws EnricherException {
        this.minimalEnricher.processMinimalUpdates(objectToEnrich, objectSource);
    }

}
