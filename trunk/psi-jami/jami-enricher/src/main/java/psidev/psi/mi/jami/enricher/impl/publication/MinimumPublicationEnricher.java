package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

/**
 * Provides minimum enrichment of the Publication.
 * Will enrich the pubmedID and the authors.
 * As an enricher, no values from the provided publication to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MinimumPublicationEnricher extends AbstractPublicationEnricher {

    public MinimumPublicationEnricher(PublicationFetcher fetcher) {
        super(fetcher);
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    @Override
    protected void processPublication(Publication publicationToEnrich) {

        // PUBMED ID
        if(publicationToEnrich.getPubmedId() == null
                && publicationFetched.getPubmedId() != null) {
            publicationToEnrich.setPubmedId(publicationFetched.getPubmedId());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPubmedIdUpdate(publicationToEnrich , null);
        }


        // AUTHORS
        if(!publicationFetched.getAuthors().isEmpty()){
            for(String author : publicationFetched.getAuthors()){
                if(publicationToEnrich.getAuthors().add(author))
                    if(getPublicationEnricherListener() != null)
                        getPublicationEnricherListener().onAuthorAdded(
                                publicationToEnrich , author);
            }
        }


        // RELEASE DATE
        if(publicationToEnrich.getReleasedDate() == null
                && publicationFetched.getReleasedDate() != null) {
            publicationToEnrich.setReleasedDate(publicationFetched.getReleasedDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich , null);
        }

    }


}
