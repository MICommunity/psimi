package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
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
public class ModelledInteractionEnricherListenerManager<I extends ModelledInteraction>
        extends InteractionEnricherListenerManager<I>
        implements ModelledInteractionEnricherListener<I> {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ModelledInteractionEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ModelledInteractionEnricherListenerManager(ModelledInteractionEnricherListener<I>... listeners){
        super(listeners);
    }

    public void onAddedCooperativeEffect(I interaction, CooperativeEffect added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onAddedCooperativeEffect(interaction, added);
            }
        }
    }

    public void onRemovedCooperativeEffect(I interaction, CooperativeEffect removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onRemovedCooperativeEffect(interaction, removed);
            }
        }
    }

    public void onAddedInteractionEvidence(I interaction, InteractionEvidence added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onAddedInteractionEvidence(interaction, added);
            }
        }
    }

    public void onRemovedInteractionEvidence(I interaction, InteractionEvidence removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onRemovedInteractionEvidence(interaction, removed);
            }
        }
    }

    public void onSourceUpdate(I interaction, Source oldSource) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onSourceUpdate(interaction, oldSource);
            }
        }
    }

    public void onEvidenceTypeUpdate(I interaction, CvTerm oldType) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onEvidenceTypeUpdate(interaction, oldType);
            }
        }
    }

    public void onAddedConfidence(I o, Confidence added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onAddedConfidence(o, added);
            }
        }
    }

    public void onRemovedConfidence(I o, Confidence removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onRemovedConfidence(o, removed);
            }
        }
    }

    public void onAddedParameter(I o, Parameter added) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onAddedParameter(o, added);
            }
        }
    }

    public void onRemovedParameter(I o, Parameter removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof ModelledInteractionEnricherListener){
                ((ModelledInteractionEnricherListener)listener).onRemovedParameter(o, removed);
            }
        }
    }

    //============================================================================================
}
