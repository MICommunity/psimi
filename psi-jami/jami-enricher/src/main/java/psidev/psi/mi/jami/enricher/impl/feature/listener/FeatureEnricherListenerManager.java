package psidev.psi.mi.jami.enricher.impl.feature.listener;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class FeatureEnricherListenerManager
        extends EnricherListenerManager<FeatureEnricherListener>
        implements FeatureEnricherListener{



    public FeatureEnricherListenerManager(FeatureEnricherListener... listeners){
        super(listeners);
    }


    //===================

    public void onFeatureEnriched(Feature feature, EnrichmentStatus status, String message) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onFeatureEnriched(feature, status, message);
        }
    }

    public void onUpdatedRange(Feature feature, Range updated, String message) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onUpdatedRange(feature, updated, message);
        }
    }

    public void onInvalidRange(Feature feature, Range invalid, String message) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onInvalidRange(feature, invalid, message);
        }
    }

    public void onShortNameUpdate(Feature feature, String oldShortName) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onShortNameUpdate(feature, oldShortName);
        }
    }

    public void onFullNameUpdate(Feature feature, String oldFullName) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onFullNameUpdate(feature, oldFullName);
        }
    }

    public void onInterproUpdate(Feature feature, String oldInterpro) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onInterproUpdate(feature, oldInterpro);
        }
    }

    public void onTypeAdded(Feature feature, CvTerm oldType) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onTypeAdded(feature, oldType);
        }
    }


    public void onAddedIdentifier(Feature feature, Xref added) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onAddedIdentifier(feature, added);
        }
    }

    public void onRemovedIdentifier(Feature feature, Xref removed) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onRemovedIdentifier(feature, removed);
        }
    }

    public void onAddedXref(Feature feature, Xref added) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onAddedXref(feature, added);
        }
    }

    public void onRemovedXref(Feature feature, Xref removed) {
        for(FeatureEnricherListener listener : listenersList){
            listener. onRemovedXref(feature, removed);
        }
    }

    public void onAddedAnnotation(Feature feature, Annotation added) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onAddedAnnotation(feature, added);
        }
    }

    public void onRemovedAnnotation(Feature feature, Annotation removed) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onRemovedAnnotation(feature, removed);
        }
    }

    public void onAddedRange(Feature feature, Range added) {
        for(FeatureEnricherListener listener : listenersList){
            listener. onAddedRange(feature, added);
        }
    }

    public void onRemovedRange(Feature feature, Range removed) {
        for(FeatureEnricherListener listener : listenersList){
            listener.onRemovedRange(feature, removed);
        }
    }
}
