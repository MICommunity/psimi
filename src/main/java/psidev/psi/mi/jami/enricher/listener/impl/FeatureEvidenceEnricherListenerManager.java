package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.FeatureEvidenceEnricherListener;
import psidev.psi.mi.jami.listener.FeatureEvidenceChangeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Parameter;

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
public class FeatureEvidenceEnricherListenerManager
        extends FeatureEnricherListenerManager<FeatureEvidence> implements FeatureEvidenceChangeListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public FeatureEvidenceEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public FeatureEvidenceEnricherListenerManager(FeatureEvidenceEnricherListener... listeners){
        super(listeners);
    }

    public void onAddedDetectionMethod(FeatureEvidence feature, CvTerm added) {
        for(FeatureEnricherListener listener : getListenersList()){
            if (listener instanceof FeatureEvidenceEnricherListener){
                ((FeatureEvidenceEnricherListener)listener).onAddedDetectionMethod(feature, added);
            }
        }
    }

    public void onRemovedDetectionMethod(FeatureEvidence feature, CvTerm removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            if (listener instanceof FeatureEvidenceEnricherListener){
                ((FeatureEvidenceEnricherListener)listener).onRemovedDetectionMethod(feature, removed);
            }
        }
    }

    public void onAddedParameter(FeatureEvidence o, Parameter added) {
        for(FeatureEnricherListener listener : getListenersList()){
            if (listener instanceof FeatureEvidenceEnricherListener){
                ((FeatureEvidenceEnricherListener)listener).onAddedParameter(o, added);
            }
        }
    }

    public void onRemovedParameter(FeatureEvidence o, Parameter removed) {
        for(FeatureEnricherListener listener : getListenersList()){
            if (listener instanceof FeatureEvidenceEnricherListener){
                ((FeatureEvidenceEnricherListener)listener).onRemovedParameter(o, removed);
            }
        }
    }
}
