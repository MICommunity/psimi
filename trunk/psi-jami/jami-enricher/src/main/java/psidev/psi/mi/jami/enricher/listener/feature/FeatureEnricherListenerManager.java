package psidev.psi.mi.jami.enricher.listener.feature;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.model.*;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class FeatureEnricherListenerManager
        extends EnricherListenerManager<Feature, FeatureEnricherListener>
        implements FeatureEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public FeatureEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public FeatureEnricherListenerManager(FeatureEnricherListener... listeners){
        super(listeners);
    }


    //============================================================================================

    public void onShortNameUpdate(Feature feature, String oldShortName) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(feature, oldShortName);
        }
    }

    public void onFullNameUpdate(Feature feature, String oldFullName) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onFullNameUpdate(feature, oldFullName);
        }
    }

    public void onInterproUpdate(Feature feature, String oldInterpro) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onInterproUpdate(feature, oldInterpro);
        }
    }

    public void onTypeAdded(Feature feature, CvTerm oldType) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onTypeAdded(feature, oldType);
        }
    }


    public void onAddedIdentifier(Feature feature, Xref added) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier(feature, added);
        }
    }

    public void onRemovedIdentifier(Feature feature, Xref removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(feature, removed);
        }
    }

    public void onAddedXref(Feature feature, Xref added) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onAddedXref(feature, added);
        }
    }

    public void onRemovedXref(Feature feature, Xref removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener. onRemovedXref(feature, removed);
        }
    }

    public void onAddedAnnotation(Feature feature, Annotation added) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(feature, added);
        }
    }

    public void onRemovedAnnotation(Feature feature, Annotation removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(feature, removed);
        }
    }

    public void onAddedRange(Feature feature, Range added) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener. onAddedRange(feature, added);
        }
    }

    public void onRemovedRange(Feature feature, Range removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            listener.onRemovedRange(feature, removed);
        }
    }
}
