package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.InteractionEvidenceChangeListener;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 *Listener for interaction evidence enrichment
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEvidenceEnricherListener extends InteractionEnricherListener<InteractionEvidence>, InteractionEvidenceChangeListener{

}
