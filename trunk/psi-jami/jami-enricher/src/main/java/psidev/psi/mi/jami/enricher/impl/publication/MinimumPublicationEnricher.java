package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MinimumPublicationEnricher extends AbstractPublicationEnricher {

    public MinimumPublicationEnricher(PublicationFetcher fetcher) {
        super(fetcher);
    }

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

    }


}
