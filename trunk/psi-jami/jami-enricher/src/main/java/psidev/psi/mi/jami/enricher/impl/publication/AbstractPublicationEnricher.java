package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.publication.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * The general architecture for a Publication enricher
 * with methods to fetch a publication and coordinate the enriching.
 * Has an abstract method 'processPublication' which can be overridden
 * to determine which parts should be enriched and how.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public abstract class AbstractPublicationEnricher
        implements PublicationEnricher{

    public static final int RETRY_COUNT = 5;

    private PublicationEnricherListener listener = null;
    private PublicationFetcher fetcher = null;

    private CvTermEnricher cvTermEnricher;

    protected Publication publicationFetched = null;

    /**
     * The only constructor. It requires a publication fetcher.
     * If the publication fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param fetcher  The PublicationFetcher to use.
     */
    public AbstractPublicationEnricher(PublicationFetcher fetcher){
        setPublicationFetcher(fetcher);
    }

    /**
     * Enriches a collection of publications.
     * @param publicationsToEnrich      The publications to be enriched
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichPublications(Collection<Publication> publicationsToEnrich)
            throws EnricherException{
        for(Publication publicationToEnrich : publicationsToEnrich){
            enrichPublication(publicationToEnrich);
        }
    }

    /**
     * Enriches the publicationToEnrich using a record found using the fetcher.
     * @param publicationToEnrich   The publication to be enriched. Can not be null.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichPublication(Publication publicationToEnrich)
            throws EnricherException{

        if( publicationToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich a null publication.");

        // == FETCH ============================================================
        publicationFetched = fetchPublication(publicationToEnrich);
        if(publicationFetched == null){
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onEnrichmentComplete(
                        publicationToEnrich, EnrichmentStatus.FAILED, "No publication could be found.");
            return;
        }

        // == SOURCE ==========================================================
        getCvTermEnricher().enrichCvTerm(publicationToEnrich.getSource());

        // == ENRICH ==========================================================
        processPublication(publicationToEnrich);

        if( getPublicationEnricherListener() != null)
            getPublicationEnricherListener().onEnrichmentComplete(publicationToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    /**
     * The strategy for the enrichment of the publication.
     * This methods can be overwritten to change the behaviour of the enrichment.
     * @param publicationToEnrich   The publication which is being enriched.
     */
    protected abstract void processPublication(Publication publicationToEnrich);

    /**
     * Fetches a publication record which matches the publicationToEnrich.
     * @param publicationToEnrich   The publication to match.
     * @return                      The fetched publication. Null if no publication is found.
     * @throws EnricherException    Thrown if the fetcher encounters a problem.
     */
    private Publication fetchPublication(Publication publicationToEnrich) throws EnricherException{
        if(getPublicationFetcher() == null) throw new IllegalStateException("The PublicationEnricher was null.");
        if(publicationToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null publication.");

        Publication publicationFetched = null;

        if(publicationToEnrich.getPubmedId() != null && publicationToEnrich.getPubmedId().length() > 0){
            try {
                publicationFetched = getPublicationFetcher().fetchPublicationByIdentifier(publicationToEnrich.getPubmedId() , Xref.ENSEMBL);
                if(publicationFetched != null) return publicationFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        publicationFetched = getPublicationFetcher().fetchPublicationByIdentifier(publicationToEnrich.getPubmedId() , Xref.ENSEMBL);
                        if(publicationFetched != null) return publicationFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Re-tried "+RETRY_COUNT+" times", e);
            }
        }

        return publicationFetched;
    }

    /**
     * Sets the publication fetcher.
     * If null, an illegal state exception will be thrown at the next enrichment
     * @param fetcher   the fetcher to be used to retrieve publication entries
     */
    public void setPublicationFetcher(PublicationFetcher fetcher){
        this.fetcher = fetcher;
    }
    /**
     * Gets the publication fetcher which is currently being used to retrieve entries
     * @return  the current publication fetcher.
     */
    public PublicationFetcher getPublicationFetcher(){
        return fetcher;
    }


    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
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
}
