package psidev.psi.mi.jami.enricher.impl.minimal;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Publication;

import java.util.Date;
import java.util.Iterator;

/**
 * Provides minimal update of Publication.
 *
 * - enrich source of a publication if the sourceEnricher is not null. If the source of the publication to enrich
 * is different from the source of the fetched publication (see DefaultCvTermComparator), it will override the source
 * with the source of the fetched publication
 * - enrich identifiers (pubmed, doi, etc.) of a publication. It will use DefaultXrefComparator to compare identifiers and add missing identifiers.
 * It will also remove identifiers that are not in the fetched publication
 * - enrich authors of a publication. It will add all missing authors.
 * It will also remove authors that are not in the fetched publication
 * - enrich publication date. If the publication date of the publication to enrich
 * is different from the publication date of the fetched publication, it will override the publication date
 * with the publication date of the fetched publication
 *
 * It will ignore all other properties of a publication
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimalPublicationUpdater extends MinimalPublicationEnricher {

    public MinimalPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processPublicationDate(Publication publicationToEnrich, Publication fetched) throws EnricherException {
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
    protected void processIdentifiers(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    @Override
    protected void processAuthors(Publication publicationToEnrich, Publication fetched) throws EnricherException{
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
