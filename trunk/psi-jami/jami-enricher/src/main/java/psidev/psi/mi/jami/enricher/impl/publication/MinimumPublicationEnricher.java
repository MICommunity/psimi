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

        // DOI
        if(publicationToEnrich.getDoi() == null
                && publicationFetched.getDoi() != null) {
            publicationToEnrich.setPubmedId(publicationFetched.getDoi());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onDoiUpdate(publicationToEnrich , null);
        }

        //IDS
        //IMEX

        // TITLE
        if(publicationToEnrich.getTitle() == null
                && publicationFetched.getTitle() != null) {
            publicationToEnrich.setTitle(publicationFetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }

        // JOURNAL
        if(publicationToEnrich.getJournal() == null
                && publicationFetched.getJournal() != null) {
            publicationToEnrich.setJournal(publicationFetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }

        // PUBLICATION DATE
        if(publicationToEnrich.getPublicationDate() == null
                && publicationFetched.getPublicationDate() != null) {
            publicationToEnrich.setPublicationDate(publicationFetched.getPublicationDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPublicationDateUpdated(publicationToEnrich , null);
        }

        // AUTHORS  // TODO - cases sensitive and alternative ways to display represent a problem
        if(!publicationFetched.getAuthors().isEmpty()){
            for(String author : publicationFetched.getAuthors()){
                if(publicationToEnrich.getAuthors().add(author))
                    if(getPublicationEnricherListener() != null)
                        getPublicationEnricherListener().onAuthorAdded(
                                publicationToEnrich , author);
            }
        }

        // XREFS

        // ANNOTATIONS

        // EXPERIMENTS

        // CURATION DEPTH

        // RELEASE DATE
        if(publicationToEnrich.getReleasedDate() == null
                && publicationFetched.getReleasedDate() != null) {
            publicationToEnrich.setReleasedDate(publicationFetched.getReleasedDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich , null);
        }

        // SOURCE

    }


}
