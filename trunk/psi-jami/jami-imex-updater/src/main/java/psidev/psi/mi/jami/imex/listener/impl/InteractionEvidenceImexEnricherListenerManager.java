package psidev.psi.mi.jami.imex.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.InteractionEvidenceEnricherListenerManager;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.imex.listener.InteractionImexEnricherListener;

import java.util.Collection;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 */
public class InteractionEvidenceImexEnricherListenerManager
        extends InteractionEvidenceEnricherListenerManager
        implements InteractionImexEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public InteractionEvidenceImexEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public InteractionEvidenceImexEnricherListenerManager(InteractionImexEnricherListener... listeners){
        super(listeners);
    }

    public void onImexIdConflicts(InteractionEvidence originalInteraction, Collection<Xref> conflictingXrefs) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionImexEnricherListener){
                ((InteractionImexEnricherListener) listener).onImexIdConflicts(originalInteraction, conflictingXrefs);
            }
        }
    }

    public void onImexIdAssigned(InteractionEvidence interaction, String imex) {
        for (InteractionEnricherListener listener : getListenersList()){
            if (listener instanceof InteractionImexEnricherListener){
                ((InteractionImexEnricherListener) listener).onImexIdAssigned(interaction, imex);
            }
        }
    }

    //============================================================================================
}
