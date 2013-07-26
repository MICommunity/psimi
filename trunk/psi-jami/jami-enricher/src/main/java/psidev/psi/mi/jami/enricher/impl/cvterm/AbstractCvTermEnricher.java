package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.RetryStrategy;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;


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

    public static final int RETRY_COUNT = 5;
    protected CvTermFetcher fetcher = null;
    protected CvTermEnricherListener listener = null;

    protected CvTerm cvTermFetched = null;

    public AbstractCvTermEnricher(CvTermFetcher cvTermFetcher) {
        setCvTermFetcher(cvTermFetcher);
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) throws EnricherException {
        for(CvTerm cvTermToEnrich : cvTermsToEnrich){
            enrichCvTerm(cvTermToEnrich);
        }
    }

    /**
     * Uses the CvTermToEnrich to fetch a more complete term
     * and then processes the CvTerm to modify it to a more enriched or updated form.
     * @param cvTermToEnrich  a CvTerm to enrich
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich) throws EnricherException {

        cvTermFetched = fetchCvTerm(cvTermToEnrich);
        if(cvTermFetched == null){
            if(listener != null) listener.onCvTermEnriched(cvTermToEnrich, EnrichmentStatus.FAILED ,"No CvTerm could be found.");
            return;
        }

        processCvTerm(cvTermToEnrich);

        if(listener != null) listener.onCvTermEnriched(cvTermToEnrich, EnrichmentStatus.SUCCESS , "CvTerm minimum enriched.");
    }

    /**
     * Uses the fetched CvTerm to enrich or update the CvTermToEnrich
     * @param cvTermToEnrich the CvTerm to enrich
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
                cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                        cvTermToEnrich.getMIIdentifier(), CvTerm.PSI_MI);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                cvTermToEnrich.getMIIdentifier(), CvTerm.PSI_MI);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }

        if(cvTermToEnrich.getMODIdentifier() != null){
            try {
                cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                        cvTermToEnrich.getMODIdentifier(), CvTerm.PSI_MOD);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                cvTermToEnrich.getMODIdentifier(), CvTerm.PSI_MOD);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }

        if(cvTermToEnrich.getPARIdentifier() != null){
            try {
                cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                        cvTermToEnrich.getPARIdentifier(), CvTerm.PSI_PAR);
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                cvTermToEnrich.getPARIdentifier(), CvTerm.PSI_PAR);
                        if(cvTermFetched != null) return cvTermFetched;
                    } catch (BridgeFailedException ee) {
                        ee.printStackTrace();
                    }
                    index++;
                }
                throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
            }
        }


        for(Xref identifierXref : cvTermToEnrich.getIdentifiers()){
            if( cvTermFetched != null ) break;


            try {
                cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                        identifierXref.getId(), identifierXref.getDatabase());
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
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
                cvTermFetched = getCvTermFetcher().getCvTermByExactName(
                        cvTermToEnrich.getFullName());
                if(cvTermFetched != null) return cvTermFetched;
            } catch (BridgeFailedException e) {
                int index = 0;
                while(index < RETRY_COUNT){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByExactName(
                                cvTermToEnrich.getFullName());
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



    public void setCvTermFetcher(CvTermFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public CvTermFetcher getCvTermFetcher() {
        return fetcher;
    }

    public void setCvTermEnricherListener(CvTermEnricherListener cvTermEnricherListener) {
        listener = cvTermEnricherListener;
    }

    public CvTermEnricherListener getCvTermEnricherListener() {
        return listener;
    }



}
