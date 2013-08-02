package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * At the minimum level, the publication enricher only enriches the pubmedId and authors.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimumPublicationUpdater
        extends AbstractPublicationEnricher{


    public MinimumPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processPublication(Publication publicationToEnrich) {

        // PUBMED ID
        if(! publicationToEnrich.getPubmedId().equals(publicationFetched.getPubmedId())
                && publicationFetched.getPubmedId() != null) {
            String oldValue = publicationToEnrich.getPubmedId();
            publicationToEnrich.setPubmedId(publicationFetched.getPubmedId());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPubmedIdUpdate(publicationToEnrich , oldValue);
        }


        // AUTHORS
        if(!publicationFetched.getAuthors().isEmpty()){
            Collection<String> authorsToRemove = new ArrayList<String>();
            authorsToRemove.addAll(publicationToEnrich.getAuthors());
            authorsToRemove.removeAll(publicationFetched.getAuthors());
            for(String author : authorsToRemove){
                publicationToEnrich.getAuthors().remove(author);
                if(getPublicationEnricherListener() != null)
                    getPublicationEnricherListener().onAuthorRemoved(publicationToEnrich, author);
            }

            for(String author : publicationFetched.getAuthors()){
                if(publicationToEnrich.getAuthors().add(author))
                    if(getPublicationEnricherListener() != null)
                        getPublicationEnricherListener().onAuthorAdded(
                                publicationToEnrich , author);
            }
        }
    }
}
