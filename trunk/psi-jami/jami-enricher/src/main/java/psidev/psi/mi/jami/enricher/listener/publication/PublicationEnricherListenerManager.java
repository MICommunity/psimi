package psidev.psi.mi.jami.enricher.listener.publication;


import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherListenerManager
        extends EnricherListenerManager<Publication, PublicationEnricherListener>
        implements PublicationEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public PublicationEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public PublicationEnricherListenerManager(PublicationEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onPubmedIdUpdate(publication, oldPubmedId);
        }
    }

    public void onDoiUpdate(Publication publication, String oldDoi) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onDoiUpdate(publication, oldDoi);
        }
    }

    public void onIdentifierAdded(Publication publication, Xref addedXref) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onIdentifierAdded(publication, addedXref);
        }
    }

    public void onIdentifierRemoved(Publication publication, Xref removedXref) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onIdentifierRemoved(publication, removedXref);
        }
    }

    public void onImexIdentifierAdded(Publication publication , Xref addedXref) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onImexIdentifierAdded(publication, addedXref);
        }
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onTitleUpdated(publication, oldTitle);
        }
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onJournalUpdated(publication, oldJournal);
        }
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onPublicationDateUpdated(publication, oldDate);
        }
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onAuthorAdded(publication, addedAuthor);
        }
    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onAuthorRemoved(publication, removedAuthor);
        }
    }

    public void onXrefAdded(Publication publication, Xref addedXref) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onXrefAdded(publication, addedXref);
        }
    }

    public void onXrefRemoved(Publication publication, Xref removedXref) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onXrefRemoved(publication, removedXref);
        }
    }

    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onAnnotationAdded(publication, annotationAdded);
        }
    }

    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onAnnotationRemoved(publication, annotationRemoved);
        }
    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        for(PublicationEnricherListener listener : getListenersList()){
            listener.onReleaseDateUpdated(publication, oldDate);
        }
    }
}
