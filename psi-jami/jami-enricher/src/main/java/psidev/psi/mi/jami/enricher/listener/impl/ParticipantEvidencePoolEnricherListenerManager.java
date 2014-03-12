package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidencePoolEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantPoolEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidencePool;

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
public class ParticipantEvidencePoolEnricherListenerManager
        extends ParticipantEvidenceEnricherListenerManager<ParticipantEvidencePool>
        implements ParticipantEvidencePoolEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ParticipantEvidencePoolEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ParticipantEvidencePoolEnricherListenerManager(ParticipantEvidencePoolEnricherListener... listeners){
        super(listeners);
    }

    public void onTypeUpdate(ParticipantEvidencePool participant, CvTerm oldType) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onTypeUpdate(participant, oldType);
            }
        }
    }

    public void onAddedEntity(ParticipantEvidencePool participant, Participant added) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onAddedEntity(participant, added);
            }
        }
    }

    public void onRemovedEntity(ParticipantEvidencePool participant, Participant removed) {
        for(ParticipantEnricherListener listener : getListenersList()){
            if (listener instanceof ParticipantPoolEnricherListener){
                ((ParticipantPoolEnricherListener)listener).onRemovedEntity(participant, removed);
            }
        }
    }


    //============================================================================================
}
