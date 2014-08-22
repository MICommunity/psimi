package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

/**
 * Listener to publication changes
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public interface PublicationChangeListener extends IdentifiersChangeListener<Publication>, XrefsChangeListener<Publication>, AnnotationsChangeListener<Publication> {

    /**
     *
     * @param publication : updated publication
     * @param oldPubmedId : old pubmed
     */
    public void onPubmedIdUpdate(Publication publication , String oldPubmedId);

    /**
     *
     * @param publication : updated publication
     * @param oldDoi : old doi
     */
    public void onDoiUpdate(Publication publication , String oldDoi);

    /**
     *
     * @param publication : updated publication
     * @param addedXref : added xref
     */
    public void onImexIdentifierUpdate(Publication publication, Xref addedXref);

    /**
     *
     * @param publication : updated publication
     * @param oldTitle : old title
     */
    public void onTitleUpdated(Publication publication , String oldTitle);

    /**
     *
     * @param publication : updated publication
     * @param oldJournal : old journal
     */
    public void onJournalUpdated(Publication publication , String oldJournal);

    /**
     *
     * @param publication : updated publication
     * @param oldDepth : old depth
     */
    public void onCurationDepthUpdate(Publication publication , CurationDepth oldDepth);

    /**
     *
     * @param publication : updated publication
     * @param oldDate : old date
     */
    public void onPublicationDateUpdated(Publication publication , Date oldDate);

    /**
     *
     * @param publication : updated publication
     * @param addedAuthor  : added author
     */
    public void onAuthorAdded(Publication publication , String addedAuthor);

    /**
     *
     * @param publication  : updated publication
     * @param removedAuthor : removed author
     */
    public void onAuthorRemoved(Publication publication , String removedAuthor);

    /**
     *
     * @param publication  : updated publication
     * @param oldDate : old release date
     */
    public void onReleaseDateUpdated(Publication publication , Date oldDate);

    /**
     *
     * @param publication : updated publication
     * @param oldSource : old source
     */
    public void onSourceUpdated(Publication publication , Source oldSource);
}
