package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.EntityPoolEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.EntityPool;

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
public class EntityPoolEnricherListenerManager<P extends EntityPool>
        extends ParticipantEnricherListenerManager<P>
        implements EntityPoolEnricherListener<P> {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public EntityPoolEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public EntityPoolEnricherListenerManager(EntityPoolEnricherListener<P>... listeners){
        super(listeners);
    }

    public void onTypeUpdate(P participant, CvTerm oldType) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof EntityPoolEnricherListener){
                ((EntityPoolEnricherListener)listener).onTypeUpdate(participant, oldType);
            }
        }
    }

    public void onAddedEntity(P participant, Entity added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof EntityPoolEnricherListener){
                ((EntityPoolEnricherListener)listener).onAddedEntity(participant, added);
            }
        }
    }

    public void onRemovedEntity(P participant, Entity removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof EntityPoolEnricherListener){
                ((EntityPoolEnricherListener)listener).onRemovedEntity(participant, removed);
            }
        }
    }


    //============================================================================================
}
