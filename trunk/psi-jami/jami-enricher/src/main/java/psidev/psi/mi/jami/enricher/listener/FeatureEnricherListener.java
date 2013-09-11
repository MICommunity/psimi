package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.listener.FeatureChangeListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * An extension of the FeatureChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the feature.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface FeatureEnricherListener
        extends FeatureChangeListener, EnricherListener<Feature> {
}
