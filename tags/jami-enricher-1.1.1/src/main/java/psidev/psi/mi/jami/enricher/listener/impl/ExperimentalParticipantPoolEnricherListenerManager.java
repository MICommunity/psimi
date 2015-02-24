package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.*;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.model.ParticipantCandidate;

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
public class ExperimentalParticipantPoolEnricherListenerManager
        extends ParticipantEvidenceEnricherListenerManager<ExperimentalParticipantPool>
        implements ExperimentalParticipantPoolEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ExperimentalParticipantPoolEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ExperimentalParticipantPoolEnricherListenerManager(ParticipantEvidenceEnricherListener<ExperimentalParticipantPool>... listeners){
        super(listeners);
    }

    public void onTypeUpdate(ExperimentalParticipantPool participant, CvTerm oldType) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onTypeUpdate(participant, oldType);
            }
        }
    }

    public void onAddedCandidate(ExperimentalParticipantPool participant, ParticipantCandidate added) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onAddedCandidate(participant, added);
            }
        }
    }

    public void onRemovedCandidate(ExperimentalParticipantPool participant, ParticipantCandidate removed) {
        for(EntityEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onRemovedCandidate(participant, removed);
            }
        }
    }


    //============================================================================================
}
