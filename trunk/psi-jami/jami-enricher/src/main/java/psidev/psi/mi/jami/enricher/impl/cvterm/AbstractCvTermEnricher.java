package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnrichmentStatus;
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

    protected CvTermFetcher fetcher = null;
    protected CvTermEnricherListener listener = null;

    protected CvTerm cvTermFetched = null;

    public AbstractCvTermEnricher() {
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
        //Todo
        if(getCvTermFetcher() == null) throw new IllegalStateException("The CvTermFetcher was null.");
        if(cvTermToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null CvTerm.");

        CvTerm cvTermFetched = null;

        Collection<Xref> identifiersList = new ArrayList<Xref>();
        identifiersList.addAll(cvTermToEnrich.getIdentifiers());
        if(identifiersList.size() > 0){
            //Try with MI
            for(Xref identifierXref : identifiersList){
                if( cvTermFetched != null ) break;
                else if( identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MI)){
                    try {
                        cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                identifierXref.getId(), identifierXref.getDatabase());
                    } catch (BridgeFailedException e) {
                        throw new EnricherException("Problem encountered while enriching CvTerm", e);
                    }
                }
            }
            //Try with MOD
            if( cvTermFetched == null){
                for(Xref identifierXref : identifiersList){
                    if( cvTermFetched != null ) break;
                    else if( identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MOD_MI)){
                        try {
                            cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                    identifierXref.getId(), identifierXref.getDatabase());
                        } catch (BridgeFailedException e) {
                            throw new EnricherException("Problem encountered while enriching CvTerm", e);
                        }
                    }
                }
            }
            //Try with all other identifiers (ignoring if the database is one already checked.
            if( cvTermFetched == null){
                for(Xref identifierXref : identifiersList){
                    if( cvTermFetched != null ) break;
                    else if(! identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MOD_MI)
                            && ! identifierXref.getDatabase().getShortName().equals(CvTerm.PSI_MI_MI)){
                        try {
                            cvTermFetched = getCvTermFetcher().getCvTermByIdentifier(
                                    identifierXref.getId(), identifierXref.getDatabase());
                        } catch (BridgeFailedException e) {
                            throw new EnricherException("Problem encountered while enriching CvTerm", e);
                        }
                    }
                }
            }
        }

        if(cvTermFetched == null){
            try{
                getCvTermFetcher().getCvTermByExactName(cvTermToEnrich.getShortName());
            } catch (BridgeFailedException e) {
                throw new EnricherException("Problem encountered while enriching CvTerm", e);
            }
        }

        return cvTermFetched;
    }


}
