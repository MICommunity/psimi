package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEvidenceEnricherListenerManager
        extends InteractionEnricherListenerManager<InteractionEvidence>
        implements InteractionEvidenceEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public InteractionEvidenceEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public InteractionEvidenceEnricherListenerManager(InteractionEvidenceEnricherListener... listeners){
        super(listeners);
    }

    public void onExperimentUpdate(InteractionEvidence interaction, Experiment oldExperiment) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onExperimentUpdate(interaction, oldExperiment);
            }
        }
    }

    public void onAddedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onAddedVariableParameterValues(interaction, added);
            }
        }
    }

    public void onRemovedVariableParameterValues(InteractionEvidence interaction, VariableParameterValueSet removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onRemovedVariableParameterValues(interaction, removed);
            }
        }
    }

    public void onInferredPropertyUpdate(InteractionEvidence interaction, boolean oldInferred) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onInferredPropertyUpdate(interaction, oldInferred);
            }
        }
    }

    public void onNegativePropertyUpdate(InteractionEvidence interaction, boolean negative) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onNegativePropertyUpdate(interaction, negative);
            }
        }
    }

    public void onAddedConfidence(InteractionEvidence o, Confidence added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onAddedConfidence(o, added);
            }
        }
    }

    public void onRemovedConfidence(InteractionEvidence o, Confidence removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onRemovedConfidence(o, removed);
            }
        }
    }

    public void onAddedParameter(InteractionEvidence o, Parameter added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onAddedParameter(o, added);
            }
        }
    }

    public void onRemovedParameter(InteractionEvidence o, Parameter removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)listener).onRemovedParameter(o, removed);
            }
        }
    }

    //============================================================================================
}
