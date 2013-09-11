package psidev.psi.mi.jami.enricher.impl;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Publication;

import java.util.Date;
import java.util.Iterator;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * At the minimum level, the publication enricher only enriches the pubmedId and authors.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimalPublicationUpdater extends MinimalPublicationEnricher {

    public MinimalPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processPublicationDate(Publication publicationToEnrich, Publication fetched) {
        // == PUBLICATION DATE =================================================================================
        if((fetched.getPublicationDate() != null && !fetched.getPublicationDate().equals( publicationToEnrich.getPublicationDate()) )
                || (fetched.getPublicationDate() == null && publicationToEnrich.getPublicationDate() != null)) {
            Date oldValue = publicationToEnrich.getPublicationDate();
            publicationToEnrich.setPublicationDate(fetched.getPublicationDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPublicationDateUpdated(publicationToEnrich , oldValue);
        }
    }

    @Override
    protected void processIdentifiers(Publication publicationToEnrich, Publication fetched) {
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    @Override
    protected void processAuthors(Publication publicationToEnrich, Publication fetched) {
        // == AUTHORS ===========================================================================================
        if(!CollectionUtils.isEqualCollection(publicationToEnrich.getAuthors(), fetched.getAuthors())){
            Iterator<String> authorIterator = publicationToEnrich.getAuthors().iterator();
            while(authorIterator.hasNext()){
                String auth = authorIterator.next();
                authorIterator.remove();
                if(getPublicationEnricherListener() != null)
                    getPublicationEnricherListener().onAuthorRemoved(publicationToEnrich, auth);
            }
            for(String author : fetched.getAuthors()){
                publicationToEnrich.getAuthors().add(author);
                if(getPublicationEnricherListener() != null)
                    getPublicationEnricherListener().onAuthorAdded(publicationToEnrich , author);
            }
        }
    }
}
