package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.ExperimentalParticipantPoolEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeEntityEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantPoolEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;

/**
 * The participant pool enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class MinimalExperimentalParticipantPoolUpdater
        extends MinimalParticipantEvidenceUpdater<ExperimentalParticipantPool> implements ExperimentalParticipantPoolEnricher {

    private CompositeEntityEnricher entityEnricher;

    @Override
    public void processOtherProperties(ExperimentalParticipantPool objectToEnrich, ExperimentalParticipantPool objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);

        // merge participant candidates
        processParticipantCandidates(objectToEnrich, objectSource);
    }

    protected void processParticipantCandidates(ExperimentalParticipantPool objectToEnrich, ExperimentalParticipantPool objectSource) throws EnricherException {
        EnricherUtils.mergeParticipantCandidates(objectToEnrich, objectToEnrich, objectSource, true,
                getParticipantEnricherListener() instanceof ParticipantPoolEnricherListener ? (ParticipantPoolEnricherListener) getParticipantEnricherListener() : null,
                getParticipantCandidateEnricher());
    }

    public CompositeEntityEnricher getParticipantCandidateEnricher() {
        return entityEnricher;
    }

    public void setParticipantCandidateEnricher(CompositeEntityEnricher entityEnricher) {
        this.entityEnricher = entityEnricher;
    }
}
