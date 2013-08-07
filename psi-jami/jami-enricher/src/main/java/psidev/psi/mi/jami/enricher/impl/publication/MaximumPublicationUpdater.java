package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Date;

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
public class MaximumPublicationUpdater
        extends MinimumPublicationUpdater{

    public MaximumPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    @Override
    public void processPublication(Publication publicationToEnrich){

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


        /**
         * XREFS
         */
        if(!publicationFetched.getXrefs().isEmpty()){
            XrefMerger xrefMerger = new XrefMerger();
            xrefMerger.merge(publicationFetched.getXrefs() , publicationToEnrich.getXrefs() , false);
            for(Xref newXref : xrefMerger.getToAdd()) {
                publicationToEnrich.getXrefs().add(newXref);
                if(getPublicationEnricherListener() != null)
                    getPublicationEnricherListener().onXrefAdded(publicationToEnrich , newXref);
            }
        }

        // ANNOTATIONS

        // EXPERIMENTS

        // CURATION DEPTH

        // SOURCE

        // RELEASE DATE
        if(publicationToEnrich.getReleasedDate().equals(publicationFetched.getReleasedDate())
                && publicationFetched.getReleasedDate() != null) {
            Date oldValue = publicationToEnrich.getReleasedDate() ;
            publicationToEnrich.setReleasedDate(publicationFetched.getReleasedDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich , oldValue);
        }
    }
}
