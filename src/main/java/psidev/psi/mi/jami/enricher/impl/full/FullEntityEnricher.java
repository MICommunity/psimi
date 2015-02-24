package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalEntityEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class FullEntityEnricher<P extends Entity, F extends Feature>
        extends MinimalEntityEnricher<P,F> {

    public void processOtherProperties(P objectToEnrich, P objectSource) throws EnricherException {
        // causal relationships
        processCausalRelationships(objectToEnrich, objectSource);

        processOtherProperties(objectToEnrich);
    }

    protected void processCausalRelationships(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeCausalRelationships(objectToEnrich, objectToEnrich.getCausalRelationships(), objectSource.getCausalRelationships(),
                false, getParticipantEnricherListener());
    }
}
