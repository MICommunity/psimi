package psidev.psi.mi.jami.enricher.impl.feature.listener;


import psidev.psi.mi.jami.enricher.listener.AbstractEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class FeatureEnricherListenerManager
        extends AbstractEnricherListenerManager<FeatureEnricherListener>
        implements FeatureEnricherListener{


    public void onFeatureEnriched(Feature feature, EnrichmentStatus status, String message) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onFeatureEnriched(feature, status, message);
        }
    }
}
