package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
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
public class ParticipantEnricherListenerManager<P extends Entity>
        extends EnricherListenerManager<P, ParticipantEnricherListener<P>>
        implements ParticipantEnricherListener<P>{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ParticipantEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ParticipantEnricherListenerManager(ParticipantEnricherListener<P>... listeners){
        super(listeners);
    }

    public void onBiologicalRoleUpdate(P participant, CvTerm oldType) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onBiologicalRoleUpdate(participant, oldType);
        }
    }

    public void onStoichiometryUpdate(P participant, Stoichiometry oldStoichiometry) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onStoichiometryUpdate(participant, oldStoichiometry);
        }
    }

    public void onAddedCausalRelationship(P participant, CausalRelationship added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onAddedCausalRelationship(participant, added);
        }
    }

    public void onRemovedCausalRelationship(P participant, CausalRelationship removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onRemovedCausalRelationship(participant, removed);
        }
    }

    public void onAddedFeature(P participant, Feature added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onAddedFeature(participant, added);
        }
    }

    public void onRemovedFeature(P participant, Feature removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onRemovedFeature(participant, removed);
        }
    }

    public void onAddedAlias(P o, Alias added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onAddedAlias(o, added);
        }
    }

    public void onRemovedAlias(P o, Alias removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onRemovedAlias(o, removed);
        }
    }

    public void onAddedAnnotation(P o, Annotation added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onAddedAnnotation(o, added);
        }
    }

    public void onRemovedAnnotation(P o, Annotation removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onRemovedAnnotation(o, removed);
        }
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onInteractorUpdate(entity, oldInteractor);
        }
    }

    public void onAddedXref(P o, Xref added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onAddedXref(o, added);
        }
    }

    public void onRemovedXref(P o, Xref removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            listener.onRemovedXref(o, removed);
        }
    }


    //============================================================================================
}
