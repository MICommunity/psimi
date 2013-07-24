package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/07/13
 */
public interface ModelledInteractionEnricher
        extends InteractionEnricher<ModelledInteraction, ModelledParticipant, ModelledFeature>{
}
