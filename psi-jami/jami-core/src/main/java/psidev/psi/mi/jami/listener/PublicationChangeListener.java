package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;
import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public interface PublicationChangeListener extends EventListener {

    public void onPubmedIdUpdate(Publication publication , String oldPubmedId);

    public void onDoiUpdate(Publication publication , String oldDoi);

    public void onIdentifierAdded(Publication publication, Xref addedXref);
    public void onIdentifierRemoved(Publication publication, Xref removedXref);
    public void onImexIdentifierAdded(Publication publication, Xref addedXref);

    public void onTitleUpdated(Publication publication , String oldTitle);

    public void onJournalUpdated(Publication publication , String oldJournal);

    public void onPublicationDateUpdated(Publication publication , Date oldDate);

    public void onAuthorAdded(Publication publication , String addedAuthor);
    public void onAuthorRemoved(Publication publication , String removedAuthor);


    public void onXrefAdded(Publication publication, Xref addedXref);
    public void onXrefRemoved(Publication publication, Xref removedXref);

    public void onAnnotationAdded(Publication publication, Annotation annotationAdded);
    public void onAnnotationRemoved(Publication publication, Annotation annotationRemoved);

    //EXPERIMENTS
    //CURATION DEPTH

    public void onReleaseDateUpdated(Publication publication , Date oldDate);

    //SOURCE

}
