package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.impl.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Participant;

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
public class ParticipantEnricherListenerManager
        extends EnricherListenerManager<Participant, ParticipantEnricherListener>
        implements ParticipantEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ParticipantEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ParticipantEnricherListenerManager(ParticipantEnricherListener... listeners){
        super(listeners);
    }



    //============================================================================================
}
