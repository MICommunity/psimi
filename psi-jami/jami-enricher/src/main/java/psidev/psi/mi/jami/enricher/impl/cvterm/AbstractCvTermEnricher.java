package psidev.psi.mi.jami.enricher.impl.cvterm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.cvterm.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;


import java.util.ArrayList;
import java.util.Collection;

/**
 * The general architecture for a CvTerm enricher with methods to fetch a CvTerm and coordinate the enriching.
 * Has an abstract method 'processCvTerm' which can be overridden to determine which parts should be enriched and how.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   08/05/13
 */
public abstract class AbstractCvTermEnricher
        implements CvTermEnricher {


    protected static final Logger log = LoggerFactory.getLogger(AbstractCvTermEnricher.class.getName());

    public static final int RETRY_COUNT = 5;

    private CvTermFetcher fetcher = null;
    private CvTermEnricherListener listener = null;

    protected CvTerm cvTermFetched = null;

    /**
     * The only constructor, fulfilling the requirement of a cvTerm fetcher.
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param cvTermFetcher  The CvTermFetcher to use.
     */
    public AbstractCvTermEnricher(CvTermFetcher cvTermFetcher) {
        setCvTermFetcher(cvTermFetcher);
    }

    /**
     * Enriches a collection of CvTerms.
     * @param cvTermsToEnrich       The cvTerms to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) throws EnricherException {
        for(CvTerm cvTermToEnrich : cvTermsToEnrich){
            enrichCvTerm(cvTermToEnrich);
        }
    }

    /**
     * Uses the cvTermToEnrich to fetch a more complete term
     * and then processes the CvTerm to modify it to a more enriched or updated form.
     * @param cvTermToEnrich  a CvTerm to enrich
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich) throws EnricherException {
        if ( cvTermToEnrich == null )
            throw new IllegalArgumentException("Attempted to enrich null interactor.") ;

        cvTermFetched = fetchCvTerm(cvTermToEnrich);
        if(cvTermFetched == null){
            if(getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.FAILED, "No CvTerm could be found.");
            return;
        }

        processCvTerm(cvTermToEnrich);

        if(listener != null) listener.onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "CvTerm minimum enriched.");
    }

    /**
     * Strategy for the cvTerm enrichment.
     * This method can be overwritten to change how the cvTerm is enriched.
     * @param cvTermToEnrich   The cvTerm to be enriched.
     */
    protected abstract void processCvTerm(CvTerm cvTermToEnrich);


    /**
     * Uses the CvTermToEnrich to fetch a more complete CvTerm
     * @param cvTermToEnrich the CvTerm that is being enriched and will be used to fetch.
     * @return  a CvTerm fetched from the fetching service.
     */
    private CvTerm fetchCvTerm(CvTerm cvTermToEnrich) throws EnricherException {
        if(getCvTermFetcher() == null) throw new IllegalStateException("The CvTermFetcher was null.");
        if(cvTermToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null CvTerm.");

        CvTerm cvTermFetched = null;


        if(cvTermToEnrich.getMIIdentifier() != null){
            try {
                cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                        cvTermToEnrich.getMIIdentifier(), CvTerm.PSI_MI);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                                cvTermToEnrich.getMIIdentifier(), CvTerm.PSI_MI);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Re-tried "+RETRY_COUNT+" times", e);
            }
        }

        if(cvTermToEnrich.getMODIdentifier() != null){
            try {
                cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                        cvTermToEnrich.getMODIdentifier(), CvTerm.PSI_MOD);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                                cvTermToEnrich.getMODIdentifier(), CvTerm.PSI_MOD);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Re-tried "+RETRY_COUNT+" times", e);
            }
        }

        if(cvTermToEnrich.getPARIdentifier() != null){
            try {
                cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                        cvTermToEnrich.getPARIdentifier(), CvTerm.PSI_PAR);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                                cvTermToEnrich.getPARIdentifier(), CvTerm.PSI_PAR);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Re-tried "+RETRY_COUNT+" times", e);
            }
        }


        for(Xref identifierXref : cvTermToEnrich.getIdentifiers()){
            //if( cvTermFetched != null ) break;
            try {
                cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                        identifierXref.getId(), identifierXref.getDatabase());
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().fetchCvTermByIdentifier(
                                identifierXref.getId(), identifierXref.getDatabase());
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }


        if(cvTermFetched == null && cvTermToEnrich.getFullName() != null){
            try {
                Collection<CvTerm> results;
                results = getCvTermFetcher().fetchCvTermByName(
                        cvTermToEnrich.getFullName());
                if (! results.isEmpty())cvTermFetched = results.iterator().next();
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        Collection<CvTerm> results;
                        results = getCvTermFetcher().fetchCvTermByName(
                                cvTermToEnrich.getFullName());
                        if (! results.isEmpty())cvTermFetched = results.iterator().next();
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }
        return cvTermFetched;
    }


    /**
     * Sets the cvTerm fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setCvTermFetcher(CvTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * The fetcher to be used for used for fetcher.
     * @return  The fetcher which is being used for fetching.
     */
    public CvTermFetcher getCvTermFetcher() {
        return fetcher;
    }


    /**
     * The cvTermEnricherListener to be used.
     * It will be fired at all points where a change is made to the cvTerm
     * @param listener  The listener to use. Can be null.
     */
    public void setCvTermEnricherListener(CvTermEnricherListener listener) {
        this.listener = listener;
    }
    /**
     * The current CvTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public CvTermEnricherListener getCvTermEnricherListener() {
        return listener;
    }
}