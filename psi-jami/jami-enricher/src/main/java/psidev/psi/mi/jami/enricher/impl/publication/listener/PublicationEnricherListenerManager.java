package psidev.psi.mi.jami.enricher.impl.publication.listener;

import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherListenerManager
        extends EnricherListenerManager<PublicationEnricherListener>
        implements PublicationEnricherListener{


    public PublicationEnricherListenerManager(){}

    public PublicationEnricherListenerManager(PublicationEnricherListener... listeners){
        super(listeners);
    }


    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onPublicationEnriched(publication, status, message);
        }
    }

    public void onPubmedIdUpdate(Publication publication, String oldPubmedId) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onPubmedIdUpdate(publication, oldPubmedId);
        }
    }

    public void onDoiUpdate(Publication publication, String oldDoi) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onDoiUpdate(publication, oldDoi);
        }
    }

    public void onIdentifierAdded(Publication publication, Xref addedXref) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onIdentifierAdded(publication, addedXref);
        }
    }

    public void onIdentifierRemoved(Publication publication, Xref removedXref) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onIdentifierRemoved(publication, removedXref);
        }
    }

    public void onImexIdentifierAdded(Publication publication , Xref addedXref) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onImexIdentifierAdded(publication, addedXref);
        }
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onTitleUpdated(publication, oldTitle);
        }
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onJournalUpdated(publication, oldJournal);
        }
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onPublicationDateUpdated(publication, oldDate);
        }
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onAuthorAdded(publication, addedAuthor);
        }
    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onAuthorRemoved(publication, removedAuthor);
        }
    }

    public void onXrefAdded(Publication publication, Xref addedXref) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onXrefAdded(publication, addedXref);
        }
    }

    public void onXrefRemoved(Publication publication, Xref removedXref) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onXrefRemoved(publication, removedXref);
        }
    }

    public void onAnnotationAdded(Publication publication, Annotation annotationAdded) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onAnnotationAdded(publication, annotationAdded);
        }
    }

    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onAnnotationRemoved(publication, annotationRemoved);
        }
    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        for(PublicationEnricherListener listener : listenersList){
            listener.onReleaseDateUpdated(publication, oldDate);
        }
    }
}
