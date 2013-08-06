package psidev.psi.mi.jami.enricher.impl.interaction.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricherListener extends EnricherListener<Interaction> {

    public void onEnrichmentComplete(
            Interaction interaction , EnrichmentStatus status, String message);

}
