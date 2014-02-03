package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.FeatureEvidenceChangeListener;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * An extension of the FeatureChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the feature.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface FeatureEvidenceEnricherListener
        extends FeatureEnricherListener<FeatureEvidence>, FeatureEvidenceChangeListener {
}
