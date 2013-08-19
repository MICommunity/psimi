package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * At the maximum level, the publication enricher enriches the minimum level fields pubmedId and authors.
 * It also enriches the fields for DOI, Title, Journal, Publication Date, Xrefs and release date.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MaximumPublicationEnricher extends MinimumPublicationEnricher {

    public MaximumPublicationEnricher(PublicationFetcher fetcher) {
        super(fetcher);
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    @Override
    protected void processPublication(Publication publicationToEnrich) {
        super.processPublication(publicationToEnrich);

        // == DOI ===================================================================
        if(publicationToEnrich.getDoi() == null
                && publicationFetched.getDoi() != null) {
            publicationToEnrich.setDoi(publicationFetched.getDoi());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onDoiUpdate(publicationToEnrich , null);
        }

        // == TITLE ==================================================================
        if(publicationToEnrich.getTitle() == null
                && publicationFetched.getTitle() != null) {
            publicationToEnrich.setTitle(publicationFetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }

        // == JOURNAL ===================================================================
        if(publicationToEnrich.getJournal() == null
                && publicationFetched.getJournal() != null) {
            publicationToEnrich.setJournal(publicationFetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , null);
        }



        // == XREFS ===========================================================================
        if(!publicationFetched.getXrefs().isEmpty()){
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
