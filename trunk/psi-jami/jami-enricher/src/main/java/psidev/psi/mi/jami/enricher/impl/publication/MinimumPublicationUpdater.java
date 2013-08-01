package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
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

        // DOI
        if(publicationToEnrich.getDoi().equals(publicationFetched.getDoi())
                && publicationFetched.getDoi() != null) {
            String oldValue = publicationToEnrich.getDoi();
            publicationToEnrich.setPubmedId(publicationFetched.getDoi());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onDoiUpdate(publicationToEnrich , oldValue);
        }

        //IDS
        //IMEX

        // TITLE
        if(publicationToEnrich.getTitle().equals(publicationFetched.getTitle())
                && publicationFetched.getTitle() != null) {
            String oldValue = publicationToEnrich.getTitle();
            publicationToEnrich.setTitle(publicationFetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldValue);
        }

        // JOURNAL
        if(publicationToEnrich.getJournal().equals( publicationFetched.getJournal() )
                && publicationFetched.getJournal() != null) {
            String oldValue = publicationToEnrich.getJournal();
            publicationToEnrich.setJournal(publicationFetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldValue);
        }

        // PUBLICATION DATE
        if(publicationToEnrich.getPublicationDate().equals( publicationFetched.getPublicationDate() )
                && publicationFetched.getPublicationDate() != null) {
            Date oldValue = publicationToEnrich.getPublicationDate();
            publicationToEnrich.setPublicationDate(publicationFetched.getPublicationDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPublicationDateUpdated(publicationToEnrich , oldValue);
        }

        // AUTHORS  // TODO - cases sensitive and alternative ways to display represent a problem
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

        // XREFS

        // ANNOTATIONS

        // EXPERIMENTS

        // CURATION DEPTH

        // RELEASE DATE
        if(publicationToEnrich.getReleasedDate().equals(publicationFetched.getReleasedDate())
                && publicationFetched.getReleasedDate() != null) {
            Date oldValue = publicationToEnrich.getReleasedDate() ;
            publicationToEnrich.setReleasedDate(publicationFetched.getReleasedDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich , oldValue);
        }

        // SOURCE
    }
}
