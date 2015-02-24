package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
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
public class EntityEnricherListenerManager<P extends Entity>
        extends EnricherListenerManager<P, EntityEnricherListener<P>>
        implements EntityEnricherListener<P>{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public EntityEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public EntityEnricherListenerManager(EntityEnricherListener<P>... listeners){
        super(listeners);
    }

    public void onStoichiometryUpdate(P participant, Stoichiometry oldStoichiometry) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onStoichiometryUpdate(participant, oldStoichiometry);
        }
    }

    public void onAddedCausalRelationship(P participant, CausalRelationship added) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onAddedCausalRelationship(participant, added);
        }
    }

    public void onRemovedCausalRelationship(P participant, CausalRelationship removed) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onRemovedCausalRelationship(participant, removed);
        }
    }

    public void onAddedFeature(P participant, Feature added) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onAddedFeature(participant, added);
        }
    }

    public void onRemovedFeature(P participant, Feature removed) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onRemovedFeature(participant, removed);
        }
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        for(EntityEnricherListener listener : getListenersList()){
            listener.onInteractorUpdate(entity, oldInteractor);
        }
    }

    //============================================================================================
}
