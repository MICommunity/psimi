package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.FeatureChangeListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;

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

    /**
     *
     * @param feature   The feature that was enriched. Can not be null.
     * @param status    The status of the enrichment upon completion. Can not be null.
     * @param message   A message with additional information. Can be null.
     */
    public void onEnrichmentComplete(Feature feature , EnrichmentStatus status , String message);

    /**
     *
     * @param feature   The feature that was enriched. Can not be null.
     * @param updated   The range that was update. Can not be null.
     * @param message   A message with additional information. Can be null.
     */
    public void onUpdatedRange(Feature feature, Range updated , String message);

    /**
     *
     * @param feature   The feature that was enriched. Can not be null.
     * @param invalid   The range which was found to be invalid. Can be null.
     * @param message   A message with additional information. Can be null.
     */
    public void onInvalidRange(Feature feature, Range invalid, String message);


}
