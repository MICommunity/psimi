package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Provides minimum enrichment of the CvTerm.
 * Will enrich the full name if it is null and the identifiers.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimalCvTermEnricher extends AbstractMIEnricher<CvTerm> implements CvTermEnricher{

    private int retryCount = 5;

    private CvTermFetcher fetcher = null;
    private CvTermEnricherListener listener = null;

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalCvTermEnricher(CvTermFetcher cvTermFetcher) {
        if (cvTermFetcher == null){
            throw new IllegalArgumentException("The fetcher is required and cannot be null");
        }
        this.fetcher = cvTermFetcher;
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

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    protected void processCvTerm(CvTerm cvTermToEnrich, CvTerm cvTermFetched){

        //ShortName not checked - never null

        // == FullName ================================================================
        processFullName(cvTermToEnrich, cvTermFetched);

        // == Identifiers =============================================================
        processIdentifiers(cvTermToEnrich, cvTermFetched);
    }

    protected void processIdentifiers(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getIdentifiers(), cvTermFetched.getIdentifiers(), false, true,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }

    protected void processFullName(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        if(cvTermToEnrich.getFullName() == null
                && cvTermFetched.getFullName() != null){

            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onFullNameUpdate(cvTermToEnrich, null);
        }
    }

    @Override
    public CvTerm find(CvTerm cvTermToEnrich) throws EnricherException {
        CvTerm cvTermFetched = null;

        if(cvTermToEnrich.getMIIdentifier() != null){
            cvTermFetched = fetchCvTerm(cvTermToEnrich.getMIIdentifier(), CvTermUtils.getPsimi());
            if(cvTermFetched != null) return cvTermFetched;
        }

        if(cvTermToEnrich.getMODIdentifier() != null){
            cvTermFetched = fetchCvTerm(cvTermToEnrich.getMODIdentifier(), CvTermUtils.getPsimod());
            if(cvTermFetched != null) return cvTermFetched;
        }

        if(cvTermToEnrich.getPARIdentifier() != null){
            cvTermFetched = fetchCvTerm(cvTermToEnrich.getPARIdentifier(), CvTermUtils.getPsipar());
            if(cvTermFetched != null) return cvTermFetched;
        }

        for(Xref identifierXref : cvTermToEnrich.getIdentifiers()){
            cvTermFetched = fetchCvTerm(identifierXref.getId(), identifierXref.getDatabase());
            if(cvTermFetched != null) return cvTermFetched;
        }

        String name = cvTermToEnrich.getFullName() != null ? cvTermToEnrich.getFullName() : cvTermToEnrich.getShortName();
        if(cvTermFetched == null && name != null){
            // first try in PSI-MI ontology
            cvTermFetched = fetchCvTerm(name, CvTerm.PSI_MI);
            if (cvTermFetched != null){
                return cvTermFetched;
            }

            // then try in PSI-MOD ontology
            cvTermFetched = fetchCvTerm(name, CvTerm.PSI_MOD);
            if (cvTermFetched != null){
                return cvTermFetched;
            }

            // then try in PSI-PAR ontology
            cvTermFetched = fetchCvTerm(name, CvTerm.PSI_PAR);
            if (cvTermFetched != null){
                return cvTermFetched;
            }

            // then try in all ontologies
            cvTermFetched = fetchCvTerm(name, (String)null);
            if (cvTermFetched != null){
                return cvTermFetched;
            }
        }

        return cvTermFetched;
    }

    @Override
    protected void onEnrichedVersionNotFound(CvTerm cvTermToEnrich) {
        if(getCvTermEnricherListener() != null)
            getCvTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.FAILED, "The cvTerm does not exist.");
    }

    @Override
    public void enrich(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        processCvTerm(cvTermToEnrich, cvTermFetched);
        if(listener != null) listener.onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "CvTerm enriched successfully.");
    }

    private CvTerm fetchCvTerm(String id, CvTerm db) throws EnricherException {
        try {
            return getCvTermFetcher().fetchByIdentifier(id, db);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < retryCount){
                try {
                    return getCvTermFetcher().fetchByIdentifier(id, db);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ retryCount +" times to fetch the CvTerm but cannot connect to the fetcher.", e);
        }
    }

    private CvTerm fetchCvTerm(String name, String db) throws EnricherException {
        try {
            return getCvTermFetcher().fetchByName(name, db);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < retryCount){
                try {
                    return getCvTermFetcher().fetchByName(name, db);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ retryCount +" times to fetch the CvTerm but cannot connect to the fetcher.", e);
        }
    }
}
