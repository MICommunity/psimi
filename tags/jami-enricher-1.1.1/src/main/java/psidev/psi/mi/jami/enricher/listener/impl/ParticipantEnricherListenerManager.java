package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
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
public class ParticipantEnricherListenerManager<P extends Participant>
        extends EntityEnricherListenerManager<P>
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
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onBiologicalRoleUpdate(participant, oldType);
            }
        }
    }

    public void onAddedAlias(P o, Alias added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onAddedAlias(o, added);
            }
        }
    }

    public void onRemovedAlias(P o, Alias removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onRemovedAlias(o, removed);
            }
        }
    }

    public void onAddedAnnotation(P o, Annotation added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onAddedAnnotation(o, added);
            }
        }
    }

    public void onRemovedAnnotation(P o, Annotation removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onRemovedAnnotation(o, removed);
            }
        }
    }

    public void onAddedXref(P o, Xref added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onAddedXref(o, added);
            }
        }
    }

    public void onRemovedXref(P o, Xref removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantEnricherListener){
                ((ParticipantEnricherListener)listener).onRemovedXref(o, removed);
            }
        }
    }


    //============================================================================================
}
