package psidev.psi.mi.jami.listener.impl;

import psidev.psi.mi.jami.listener.PublicationChangeListener;
import psidev.psi.mi.jami.model.*;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener will listen to publication change events
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class PublicationChangeLogger implements PublicationChangeListener {

    private static final Logger publicationChangeLogger = Logger.getLogger("PublicationChangeLogger");

    public void onPubmedIdUpdate(Publication publication, String oldId) {
        if (oldId == null){
            publicationChangeLogger.log(Level.INFO, "The pubmed id has been initialised for the publication " + publication.toString());
        }
        else if (publication.getPubmedId() == null){
            publicationChangeLogger.log(Level.INFO, "The pubmed id has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The pubmed id " + oldId + " has been updated with " + publication.getPubmedId() + " in the publication " + publication.toString());
        }
    }

    public void onDoiUpdate(Publication publication, String oldId) {
        if (oldId == null){
            publicationChangeLogger.log(Level.INFO, "The DOI has been initialised for the publication " + publication.toString());
        }
        else if (publication.getDoi() == null){
            publicationChangeLogger.log(Level.INFO, "The DOI has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The DOI " + oldId + " has been updated with " + publication.getDoi() + " in the publication " + publication.toString());
        }
    }

    public void onImexIdentifierUpdate(Publication publication, Xref oldId) {
        if (oldId == null){
            publicationChangeLogger.log(Level.INFO, "The IMEx identifier has been initialised for the publication " + publication.toString());
        }
        else if (publication.getImexId() == null){
            publicationChangeLogger.log(Level.INFO, "The IMEx identifier has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The IMEx identifier " + oldId + " has been updated with " + publication.getImexId() + " in the publication " + publication.toString());
        }
    }

    public void onTitleUpdated(Publication publication, String oldTitle) {
        if (oldTitle == null){
            publicationChangeLogger.log(Level.INFO, "The title has been initialised for the publication " + publication.toString());
        }
        else if (publication.getTitle() == null){
            publicationChangeLogger.log(Level.INFO, "The title has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The title " + oldTitle + " has been updated with " + publication.getTitle() + " in the publication " + publication.toString());
        }
    }

    public void onJournalUpdated(Publication publication, String oldJournal) {
        if (oldJournal == null){
            publicationChangeLogger.log(Level.INFO, "The journal has been initialised for the publication " + publication.toString());
        }
        else if (publication.getJournal() == null){
            publicationChangeLogger.log(Level.INFO, "The journal has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The journal " + oldJournal + " has been updated with " + publication.getJournal() + " in the publication " + publication.toString());
        }
    }

    public void onPublicationDateUpdated(Publication publication, Date oldDate) {
        if (oldDate == null){
            publicationChangeLogger.log(Level.INFO, "The publication date has been initialised for the publication " + publication.toString());
        }
        else if (publication.getPublicationDate() == null){
            publicationChangeLogger.log(Level.INFO, "The publication date has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The publication date " + oldDate + " has been updated with " + publication.getPublicationDate() + " in the publication " + publication.toString());
        }
    }

    public void onAuthorAdded(Publication publication, String addedAuthor) {
        publicationChangeLogger.log(Level.INFO, "The author " + addedAuthor + " has been added to the publication " + publication.toString());

    }

    public void onAuthorRemoved(Publication publication, String removedAuthor) {
        publicationChangeLogger.log(Level.INFO, "The author " + removedAuthor + " has been removed from the publication " + publication.toString());

    }

    public void onReleaseDateUpdated(Publication publication, Date oldDate) {
        if (oldDate == null){
            publicationChangeLogger.log(Level.INFO, "The released date has been initialised for the publication " + publication.toString());
        }
        else if (publication.getReleasedDate() == null){
            publicationChangeLogger.log(Level.INFO, "The released date has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The released date " + oldDate + " has been updated with " + publication.getReleasedDate() + " in the publication " + publication.toString());
        }
    }

    public void onSourceUpdated(Publication publication, Source oldSource) {
        if (oldSource == null){
            publicationChangeLogger.log(Level.INFO, "The source has been initialised for the publication " + publication.toString());
        }
        else if (publication.getSource() == null){
            publicationChangeLogger.log(Level.INFO, "The source has been reset for the publication " + publication.toString());
        }
        else {
            publicationChangeLogger.log(Level.INFO, "The source " + oldSource + " has been updated with " + publication.getSource() + " in the publication " + publication.toString());
        }
    }

    public void onAddedIdentifier(Publication publication, Xref added) {
        publicationChangeLogger.log(Level.INFO, "The identifier " + added.toString() + " has been added to the publication " + publication.toString());
    }

    public void onRemovedIdentifier(Publication publication, Xref removed) {
        publicationChangeLogger.log(Level.INFO, "The identifier " + removed.toString() + " has been removed from the publication " + publication.toString());
    }

    public void onAddedXref(Publication publication, Xref added) {
        publicationChangeLogger.log(Level.INFO, "The xref " + added.toString() + " has been added to the publication " + publication.toString());
    }

    public void onRemovedXref(Publication publication, Xref removed) {
        publicationChangeLogger.log(Level.INFO, "The xref " + removed.toString() + " has been removed from the publication " + publication.toString());
    }

    public void onAddedAnnotation(Publication publication, Annotation added) {
        publicationChangeLogger.log(Level.INFO, "The annotation " + added.toString() + " has been added to the publication " + publication.toString());
    }

    public void onRemovedAnnotation(Publication publication, Annotation removed) {
        publicationChangeLogger.log(Level.INFO, "The annotation " + removed.toString() + " has been removed from the publication " + publication.toString());
    }
}
