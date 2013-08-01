package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * An extension to the feature enricher, the protein listening form also updates ranges.
 *
 * Updating ranges requires knowledge of the protein's sequence.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface ProteinListeningFeatureEnricher<F extends Feature>
    extends FeatureEnricher<F>, ProteinEnricherListener{


}
