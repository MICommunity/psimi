package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.*;

import java.util.Date;

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
public class InteractionEnricherListenerManager<I extends Interaction>
        extends EnricherListenerManager<I, InteractionEnricherListener<I>>
        implements InteractionEnricherListener<I>{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public InteractionEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public InteractionEnricherListenerManager(InteractionEnricherListener<I>... listeners){
        super(listeners);
    }

    public void onShortNameUpdate(I interaction, String oldName) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onShortNameUpdate(interaction, oldName);
        }
    }

    public void onUpdatedDateUpdate(I interaction, Date oldUpdate) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onUpdatedDateUpdate(interaction, oldUpdate);
        }
    }

    public void onCreatedDateUpdate(I interaction, Date oldCreated) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onCreatedDateUpdate(interaction, oldCreated);
        }
    }

    public void onInteractionTypeUpdate(I interaction, CvTerm oldType) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onInteractionTypeUpdate(interaction, oldType);
        }
    }

    public void onAddedParticipant(I interaction, Participant addedParticipant) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onAddedParticipant(interaction, addedParticipant);
        }
    }

    public void onRemovedParticipant(I interaction, Participant removedParticipant) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onRemovedParticipant(interaction, removedParticipant);
        }
    }

    public void onAddedAnnotation(I o, Annotation added) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(I o, Annotation removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }

    public void onAddedChecksum(I interactor, Checksum added) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onAddedChecksum(interactor, added);
        }
    }

    public void onRemovedChecksum(I interactor, Checksum removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onRemovedChecksum(interactor, removed);
        }
    }

    public void onAddedIdentifier(I o, Xref added) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onAddedIdentifier(o, added);
        }
    }

    public void onRemovedIdentifier(I o, Xref removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onRemovedIdentifier(o, removed);
        }
    }

    public void onAddedXref(I o, Xref added) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onAddedXref(o, added);
        }
    }

    public void onRemovedXref(I o, Xref removed) {
        for (InteractionEnricherListener listener : getListenersList()){
            listener.onRemovedXref(o, removed);
        }
    }

    //============================================================================================
}
