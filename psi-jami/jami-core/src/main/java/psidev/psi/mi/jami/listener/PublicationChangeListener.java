package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public interface PublicationChangeListener extends IdentifiersChangeListener<Publication>, XrefsChangeListener<Publication>, AnnotationsChangeListener<Publication> {

    public void onPubmedIdUpdate(Publication publication , String oldPubmedId);

    public void onDoiUpdate(Publication publication , String oldDoi);

    public void onImexIdentifierUpdate(Publication publication, Xref addedXref);

    public void onTitleUpdated(Publication publication , String oldTitle);

    public void onJournalUpdated(Publication publication , String oldJournal);
    public void onCurationDepthUpdate(Publication publication , CurationDepth oldDepth);
    public void onPublicationDateUpdated(Publication publication , Date oldDate);

    public void onAuthorAdded(Publication publication , String addedAuthor);
    public void onAuthorRemoved(Publication publication , String removedAuthor);

    public void onReleaseDateUpdated(Publication publication , Date oldDate);

    public void onSourceUpdated(Publication publication , Source oldSource);
}
