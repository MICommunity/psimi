package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.AnnotationUtils;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * As an updater, any fields which contradict the fetched entry will be overwritten.
 *
 * At the maximum level, the publication updater overwrites the minimum level fields pubmedId and authors.
 * It also updates the fields for DOI, Title, Journal, Publication Date, Xrefs and release date.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class FullPublicationUpdater extends FullPublicationEnricher{

    private MinimalPublicationUpdater minimalPublicationUpdater;

    public FullPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
        this.minimalPublicationUpdater = new MinimalPublicationUpdater(fetcher);
    }

    @Override
    protected void processMinimalEnrichment(Publication publicationToEnrich, Publication fetched) throws EnricherException {
        this.minimalPublicationUpdater.processPublication(publicationToEnrich, fetched);
    }

    @Override
    protected void processContactEmail(Publication publicationToEnrich, Publication fetched) {
        Annotation contactEmail1 = AnnotationUtils.collectFirstAnnotationWithTopic(fetched.getAnnotations(), Annotation.CONTACT_EMAIL_MI, Annotation.CONTACT_EMAIL);
        Annotation contactEmail2 = AnnotationUtils.collectFirstAnnotationWithTopic(publicationToEnrich.getAnnotations(), Annotation.CONTACT_EMAIL_MI, Annotation.CONTACT_EMAIL);

        if ((contactEmail1 != null && !contactEmail1.equals(contactEmail2)) || (contactEmail1 == null && contactEmail2 != null)){
            publicationToEnrich.getAnnotations().remove(contactEmail2);
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onRemovedAnnotation(publicationToEnrich, contactEmail2);
            publicationToEnrich.getAnnotations().add(contactEmail1);
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onAddedAnnotation(publicationToEnrich, contactEmail1);
        }
    }

    @Override
    protected void processJournal(Publication publicationToEnrich, Publication fetched) {
        if((fetched.getJournal() != null && !fetched.getJournal().equals(publicationToEnrich.getJournal()))
                || (fetched.getJournal() == null && publicationToEnrich.getJournal() != null)) {
            String oldJournal = publicationToEnrich.getJournal();
            publicationToEnrich.setTitle(fetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onJournalUpdated(publicationToEnrich, oldJournal);
        }
    }

    @Override
    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) {
        if((fetched.getTitle() != null && !fetched.getTitle().equals(publicationToEnrich.getTitle()))
                || (fetched.getTitle() == null && publicationToEnrich.getTitle() != null)) {
            String oldTitle = publicationToEnrich.getTitle();
            publicationToEnrich.setTitle(fetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldTitle);
        }
    }
}
