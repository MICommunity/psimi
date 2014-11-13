package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractMIEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

/**
 * Provides minimal enrichment of Publication.
 *
 * - enrich identifiers (pubmed, doi, etc.) of a publication. It will use DefaultXrefComparator to compare identifiers and add missing identifiers without
 * removing any existing identifiers.
 * - enrich authors of a publication. It will add all missing authors but will not remove any existing authors
 * - enrich publication date. It will only enrich the publication date if it is not already set in the publication to enrich. It will not
 * override any existing publication date
 *
 * It will ignore all other properties of a publication
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class MinimalPublicationEnricher extends AbstractMIEnricher<Publication> implements PublicationEnricher {

    private int retryCount = 5;

    private PublicationEnricherListener listener = null;
    private PublicationFetcher fetcher = null;

    /**
     * The only constructor. It requires a publication fetcher.
     * If the publication fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param fetcher  The PublicationFetcher to use.
     */
    public MinimalPublicationEnricher(PublicationFetcher fetcher){
        if (fetcher == null){
            throw new IllegalArgumentException("The fetcher is required and cannot be null");
        }
        this.fetcher = fetcher;
    }

    /**
     * Gets the publication fetcher which is currently being used to retrieve entries
     * @return  the current publication fetcher.
     */
    public PublicationFetcher getPublicationFetcher(){
        return fetcher;
    }

    /**
     * Sets the listener to report publication changes to.
     * Can be null.
     * @param listener the new publication listener
     */
    public void setPublicationEnricherListener(PublicationEnricherListener listener){
        this.listener = listener;
    }
    /**
     * Gets the current publication listener
     * @return  the current publication listener
     */
    public PublicationEnricherListener getPublicationEnricherListener(){
        return listener;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    public void processPublication(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException{

        // == PUBMED ID and other identifiers ======================================================================
        processIdentifiers(publicationToEnrich, fetchedPublication);

        // == AUTHORS =======================================================================
        processAuthors(publicationToEnrich, fetchedPublication);

        // == PUBLICATION DATE ============================================================
        processPublicationDate(publicationToEnrich, fetchedPublication);

        // other properties
        processOtherProperties(publicationToEnrich, fetchedPublication);
    }

    protected void processOtherProperties(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException{
        // do nothing
    }

    protected void processPublicationDate(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        if(publicationToEnrich.getPublicationDate() == null
                && fetched.getPublicationDate() != null) {
            publicationToEnrich.setPublicationDate(fetched.getPublicationDate());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onPublicationDateUpdated(publicationToEnrich , null);
        }
    }

    protected void processAuthors(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        // only add authors if empty collection. Authors are an ordered list and it does not make sens to complete an author list. Either it is there or it is not.
        if(!fetched.getAuthors().isEmpty() && publicationToEnrich.getAuthors().isEmpty()){
            for(String author : fetched.getAuthors()){
                publicationToEnrich.getAuthors().add(author);
                    if(getPublicationEnricherListener() != null)
                        getPublicationEnricherListener().onAuthorAdded(publicationToEnrich , author);
            }
        }
    }

    protected void processIdentifiers(Publication publicationToEnrich, Publication fetched) throws EnricherException{
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getIdentifiers(), fetched.getIdentifiers(), false, true,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    @Override
    public Publication find(Publication publicationToEnrich) throws EnricherException {
        Publication publicationFetched = null;

        if(publicationToEnrich.getPubmedId() != null){
            publicationFetched = fetchPublication(publicationToEnrich.getPubmedId(), Xref.PUBMED);
            if(publicationFetched != null) return publicationFetched;
        }

        if(publicationToEnrich.getDoi() != null){
            publicationFetched = fetchPublication(publicationToEnrich.getDoi(), Xref.DOI);
            if(publicationFetched != null) return publicationFetched;
        }

        return publicationFetched;
    }

    @Override
    protected void onEnrichedVersionNotFound(Publication publicationToEnrich) throws EnricherException {
        if(getPublicationEnricherListener() != null)
            getPublicationEnricherListener().onEnrichmentComplete(
                    publicationToEnrich, EnrichmentStatus.FAILED, "No matching publication could be found.");
    }

    @Override
    public void enrich(Publication publicationToEnrich, Publication publicationFetched) throws EnricherException {
        processPublication(publicationToEnrich, publicationFetched);

        if( getPublicationEnricherListener() != null)
            getPublicationEnricherListener().onEnrichmentComplete(publicationToEnrich , EnrichmentStatus.SUCCESS , "The publication has been successfully enriched");
    }

    private Publication fetchPublication(String id, String db) throws EnricherException {
        try {
            return getPublicationFetcher().fetchByIdentifier(id, db);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < retryCount){
                try {
                    return getPublicationFetcher().fetchByIdentifier(id, db);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ retryCount +" times to fetch the Publication but cannot connect to the fetcher.", e);
        }
    }
}
