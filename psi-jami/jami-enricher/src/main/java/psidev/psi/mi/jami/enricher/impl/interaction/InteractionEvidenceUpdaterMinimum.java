package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.model.*;

/**
 * An extension of the interaction enricher which only accepts InteractionEvidence
 * Overrides the default ParticipantEnricher to the evidence only form.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEvidenceUpdaterMinimum
        extends InteractionUpdaterMinimum<InteractionEvidence, ParticipantEvidence, FeatureEvidence>
        implements InteractionEvidenceEnricher {

    /*
    @Override
    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantEvidenceUpdaterMinimum();
        return participantEnricher;
    } */
}
