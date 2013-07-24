package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/07/13
 */
public interface InteractionEvidenceEnricher
        extends InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> {
}
