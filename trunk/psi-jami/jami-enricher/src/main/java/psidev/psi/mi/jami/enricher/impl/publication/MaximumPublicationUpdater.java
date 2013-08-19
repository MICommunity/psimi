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

        // == DOI ============================================================================================
        if(publicationFetched.getDoi() != null
                && ! publicationFetched.getDoi().equals( publicationToEnrich.getDoi() )){
            String oldValue = publicationToEnrich.getDoi();
            publicationToEnrich.setPubmedId(publicationFetched.getDoi());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onDoiUpdate(publicationToEnrich , oldValue);
        }


        // == TITLE ========================================================================================
        if(publicationFetched.getTitle() != null
                && ! publicationFetched.getTitle().equals( publicationToEnrich.getTitle() )) {
            String oldValue = publicationToEnrich.getTitle();
            publicationToEnrich.setTitle(publicationFetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldValue);
        }

        // == JOURNAL =======================================================================================
        if(publicationFetched.getJournal() != null
                && ! publicationFetched.getJournal().equals( publicationToEnrich.getJournal() )) {
            String oldValue = publicationToEnrich.getJournal();
            publicationToEnrich.setJournal(publicationFetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldValue);
        }

        // == XREFS ========================================================================================

        if( ! publicationFetched.getXrefs().isEmpty() ){
            XrefMerger xrefMerger = new XrefMerger();
            xrefMerger.merge(publicationFetched.getXrefs() , publicationToEnrich.getXrefs() , false);
            for(Xref newXref : xrefMerger.getToAdd()) {
                publicationToEnrich.getXrefs().add(newXref);
                if(getPublicationEnricherListener() != null)
                    getPublicationEnricherListener().onXrefAdded(publicationToEnrich , newXref);
            }
        }


        // == IDS ===========================================================================================

        // == IMEX ==========================================================================================

        // == ANNOTATIONS ===================================================================================

        // == EXPERIMENTS ===================================================================================

        // == CURATION DEPTH ================================================================================

    }
}
