package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalParticipantEnricher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.listener.AnnotationsChangeListener;
import psidev.psi.mi.jami.listener.XrefsChangeListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class FullParticipantEnricher<P extends Participant, F extends Feature>
        extends MinimalParticipantEnricher<P,F> {

    public void processOtherProperties(P objectToEnrich, P objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);
        // causal relationships
        processCausalRelationships(objectToEnrich, objectSource);
        // xrefs
        processXrefs(objectToEnrich, objectSource);
        // annotations
        processAnnotations(objectToEnrich, objectSource);

        processOtherProperties(objectToEnrich);
    }

    protected void processCausalRelationships(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeCausalRelationships(objectToEnrich, objectToEnrich.getCausalRelationships(), objectSource.getCausalRelationships(),
                false, getParticipantEnricherListener());
    }

    protected void processXrefs(P objectToEnrich, P objectSource) throws EnricherException{
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), objectSource.getXrefs(), false,
                false,
                getParticipantEnricherListener() instanceof XrefsChangeListener ? (XrefsChangeListener)getParticipantEnricherListener():null,
                null);
    }

    protected void processAnnotations(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), objectSource.getAnnotations(), false,
                getParticipantEnricherListener() instanceof AnnotationsChangeListener ? (AnnotationsChangeListener)getParticipantEnricherListener():null);
    }
}
