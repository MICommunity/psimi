package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.ComplexChangeListener;
import psidev.psi.mi.jami.model.Complex;

/**
 *Listener for complex enrichment
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface ComplexEnricherListener extends ModelledInteractionEnricherListener<Complex>, ComplexChangeListener, InteractorEnricherListener<Complex> {

}
