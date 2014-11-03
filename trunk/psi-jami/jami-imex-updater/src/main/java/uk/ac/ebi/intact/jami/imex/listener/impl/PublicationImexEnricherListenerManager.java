package uk.ac.ebi.intact.jami.imex.listener.impl;


import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.PublicationEnricherListenerManager;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.jami.bridges.imex.PublicationStatus;
import uk.ac.ebi.intact.jami.imex.listener.PublicationImexEnricherListener;

import java.util.Collection;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 */
public class PublicationImexEnricherListenerManager
        extends PublicationEnricherListenerManager
        implements PublicationImexEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public PublicationImexEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public PublicationImexEnricherListenerManager(PublicationImexEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onImexIdConflicts(Publication originalPublication, Collection<Xref> conflictingXrefs) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onImexIdConflicts(originalPublication, conflictingXrefs);
            }
        }
    }

    public void onMissingImexId(Publication publication) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onMissingImexId(publication);
            }
        }
    }

    public void onCurationDepthUpdated(Publication publication, CurationDepth oldDepth) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onCurationDepthUpdated(publication, oldDepth);
            }
        }
    }

    public void onImexAdminGroupUpdated(Publication publication, Source oldSource) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onImexAdminGroupUpdated(publication, oldSource);
            }
        }
    }

    public void onImexStatusUpdated(Publication publication, PublicationStatus oldStatus) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onImexStatusUpdated(publication, oldStatus);
            }
        }
    }

    public void onImexPublicationIdentifierSynchronized(Publication publication) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onImexPublicationIdentifierSynchronized(publication);
            }
        }
    }

    public void onPublicationAlreadyRegisteredInImexCentral(Publication publication, String imex) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onPublicationAlreadyRegisteredInImexCentral(publication, imex);
            }
        }
    }

    public void onPublicationRegisteredInImexCentral(Publication publication) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onPublicationRegisteredInImexCentral(publication);
            }
        }
    }

    public void onPublicationWhichCannotBeRegistered(Publication publication) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onPublicationWhichCannotBeRegistered(publication);
            }
        }
    }

    public void onPublicationNotEligibleForImex(Publication publication) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onPublicationNotEligibleForImex(publication);
            }
        }
    }

    public void onImexIdAssigned(Publication publication, String imex) {
        for(PublicationEnricherListener listener : getListenersList()){
            if (listener instanceof PublicationImexEnricherListener){
                ((PublicationImexEnricherListener) listener).onImexIdAssigned(publication, imex);
            }
        }
    }
}
