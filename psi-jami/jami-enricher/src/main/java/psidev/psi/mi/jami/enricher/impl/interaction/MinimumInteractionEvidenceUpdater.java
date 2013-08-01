package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;

/**
 * An extension of the interaction enricher which only accepts InteractionEvidence
 * Overrides the default ParticipantEnricher to the evidence only form.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MinimumInteractionEvidenceUpdater
        extends MinimumInteractionUpdater<InteractionEvidence, ParticipantEvidence, FeatureEvidence>
        implements InteractionEvidenceEnricher {

    private ExperimentEnricher experimentEnricher = null;

    @Override
    public void processInteraction(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);

        if( getExperimentEnricher() != null
                && interactionToEnrich.getExperiment() != null )
            getExperimentEnricher().enrichExperiment( interactionToEnrich.getExperiment() );
    }

    public ExperimentEnricher getExperimentEnricher() {
        return experimentEnricher ;
    }

    public void setExperimentEnricher(ExperimentEnricher experimentEnricher) {
        this.experimentEnricher = experimentEnricher;
    }

    /*
    @Override
    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new MinimumParticipantEvidenceUpdater();
        return participantEnricher;
    } */
}
