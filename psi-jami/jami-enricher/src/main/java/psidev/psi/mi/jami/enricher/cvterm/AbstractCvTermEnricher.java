package psidev.psi.mi.jami.enricher.cvterm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The basic layer for all cvTerm enrichment.
 * Responsible for fetching an enriched form for comparison.
 * To get a report of all changes, use an enrichment listener.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:21
 */
public abstract class AbstractCvTermEnricher
        extends EnricherEventProcessorImp
        implements CvTermEnricher {

    private final Logger log = LoggerFactory.getLogger(AbstractCvTermEnricher.class.getName());
    private CvTermFetcher fetcher = null;
    private static final String TYPE =  "CvTerm";

    public AbstractCvTermEnricher(){
        enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractCvTermEnricher(CvTermFetcher fetcher){
        this();
        setFetcher(fetcher);
    }


    public void setFetcher(CvTermFetcher fetcher){
        this.fetcher = fetcher;
    }
    public CvTermFetcher getFetcher(){
        return this.fetcher;
    }

    /**
     * Uses details in the CvTermToEnrich to generate a new, ideal CvTerm.
     * No changes are made to the CvTermToEnrich.
     * This will be an identifier if it is provided, followed by the full and short names.
     * If the identifier can not be identified, an enrichment exception is thrown.
     * If the full or short names do not match a single entry, an enrichment exception is thrown.
     *
     * @param cvTermToEnrich  The CvTerm to be enriched
     * @return              A new, ideal CvTerm inferred from an identifying feature of the CvTermToEnrich.
     * @throws EnrichmentException  Thrown when a bridge has failed or in the case of bad identifiers.
     */
    protected CvTerm getEnrichedForm(CvTerm cvTermToEnrich)
            throws EnrichmentException {
        if(fetcher == null) throw new FetchingException("CvTermFetcher is null.");

        CvTerm enriched = null;
        enricherEvent.clear();


        Collection<Xref> identifiersList = new ArrayList<Xref>();
        identifiersList.addAll(cvTermToEnrich.getIdentifiers());
        if(identifiersList.size() > 0){
            String identifier = null;
            if(cvTermToEnrich.getMIIdentifier() != null){
                identifier = cvTermToEnrich.getMIIdentifier();
            }else if(cvTermToEnrich.getMODIdentifier() != null){
                identifier = cvTermToEnrich.getMODIdentifier();
            }else if(cvTermToEnrich.getPARIdentifier() != null){
                identifier = cvTermToEnrich.getPARIdentifier();
            }
            if(identifier != null){
                Xref identifierXref;
                for(Xref id : identifiersList){
                    if(id.getId() == identifier){
                        identifierXref = id;
                        enricherEvent.setQueryDetails(identifierXref.getId(), "Identifier");
                        enriched = enrichByIdentifier(identifierXref);
                        break;
                    }
                }

            }
            //Todo use other identifiers if none of these are present
        }

        /*
        if(enriched == null){
            if(cvTermToEnrich.getFullName() != null){
                enricherEvent.setQueryDetails(cvTermToEnrich.getFullName(),"FullName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on fullname "+cvTermToEnrich.getFullName());}
                    //TODO where do you get the database for term if there is no identifier?
                    enriched = fetcher.getCvTermByName(cvTermToEnrich.getFullName(), cvTermToEnrich.get);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            } else { if(log.isTraceEnabled()) log.trace("No full name to search on");}
        }

        if(enriched == null){
            if(cvTermToEnrich.getShortName() != null){
                enricherEvent.setQueryDetails(cvTermToEnrich.getShortName(),"ShortName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on short name "+cvTermToEnrich.getShortName());}
                    //Todo find out if ols gives a database

                    enriched = fetcher.getCvTermByName(cvTermToEnrich.getShortName(), null);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            }
            else{ if(log.isTraceEnabled()) log.trace("No short name to search on."); }
        }*/

        if(enriched == null)throw new FetchingException("Null CvTerm");

        return enriched;
    }



    private CvTerm enrichByIdentifier(Xref identifier)
            throws EnrichmentException{

        if(log.isTraceEnabled()) log.trace("Searching on identifier "+identifier.getId());

        try{
            return fetcher.getCvTermByID(identifier.getId(), identifier.getDatabase());
        }catch(FetcherException e){
            throw new FetchingException("Identifier ["+identifier+"] could not return a CvTerm.",e);
        }
    }

}
