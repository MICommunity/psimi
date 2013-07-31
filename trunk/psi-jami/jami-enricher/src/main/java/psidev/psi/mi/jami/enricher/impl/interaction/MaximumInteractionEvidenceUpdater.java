package psidev.psi.mi.jami.enricher.impl.interaction;


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
public class MaximumInteractionEvidenceUpdater
        extends MaximumInteractionUpdater<InteractionEvidence, ParticipantEvidence, FeatureEvidence>
        implements InteractionEvidenceEnricher {


    private PublicationEnricher publicationEnricher = null;

    @Override
    public void processInteraction(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);

        if( getPublicationEnricher() != null )
            if( interactionToEnrich.getExperiment() != null
                    && interactionToEnrich.getExperiment().getPublication() != null )
                getPublicationEnricher().enrichPublication(
                        interactionToEnrich.getExperiment().getPublication());
    }

    public PublicationEnricher getPublicationEnricher() {
        return publicationEnricher;
    }

    public void setPublicationEnricher(PublicationEnricher publicationEnricher) {
        this.publicationEnricher = publicationEnricher;
    }


    /*
    @Override
    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new MaximumParticipantEvidenceUpdater();
        return participantEnricher;
    }*/
}
