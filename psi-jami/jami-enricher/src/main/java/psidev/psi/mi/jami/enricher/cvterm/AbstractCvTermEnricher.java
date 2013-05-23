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
 *
 * When extended with an enricher, an EnrichmentEvent will be reported at the end.
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

    /**
     * @throws EnrichmentException      Thrown when the bridge can not be initiated
     */
    public AbstractCvTermEnricher()
            throws EnrichmentException {
        enricherEvent = new EnricherEvent("CvTerm");
    }

    public void setFetcher(CvTermFetcher fetcher){
        this.fetcher = fetcher;
    }
    public CvTermFetcher getFetcher(){
        return this.fetcher;
    }

    /**
     * Uses details in the CvTerm Master to generate a new, ideal CvTerm.
     * No changes are made to the master CvTerm.
     * The enriched form is generated using an identifying feature of the master.
     * This will be an identifier if it is provided, followed by the full and short names.
     * If the identifier can not be identified, an enrichment exception is thrown.
     * If the full or short names do not match a single entry, an enrichment exception is thrown.
     *
     * @param cvTermMaster  The CvTerm to be enriched
     * @return              A new, ideal CvTerm inferred from an identifying feature of the master.
     * @throws EnrichmentException  Thrown when a bridge has failed or in the case of bad identifiers.
     */
    protected CvTerm getEnrichedForm(CvTerm cvTermMaster)
            throws EnrichmentException {
        if(fetcher == null) throw new FetchingException("CvTermFetcher is null.");

        CvTerm enriched = null;
        enricherEvent.clear();


        Collection<Xref> identifiersList = new ArrayList<Xref>();
        identifiersList.addAll(cvTermMaster.getIdentifiers());
        if(identifiersList.size() > 0){
            String identifier = null;
            if(cvTermMaster.getMIIdentifier() != null){
                identifier = cvTermMaster.getMIIdentifier();
            }else if(cvTermMaster.getMODIdentifier() != null){
                identifier = cvTermMaster.getMODIdentifier();
            }else if(cvTermMaster.getPARIdentifier() != null){
                identifier = cvTermMaster.getPARIdentifier();
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
            if(cvTermMaster.getFullName() != null){
                enricherEvent.setQueryDetails(cvTermMaster.getFullName(),"FullName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on fullname "+cvTermMaster.getFullName());}
                    //TODO where do you get the database for term if there is no identifier?
                    enriched = fetcher.getCvTermByName(cvTermMaster.getFullName(), cvTermMaster.get);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            } else { if(log.isTraceEnabled()) log.trace("No full name to search on");}
        }

        if(enriched == null){
            if(cvTermMaster.getShortName() != null){
                enricherEvent.setQueryDetails(cvTermMaster.getShortName(),"ShortName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on short name "+cvTermMaster.getShortName());}
                    //Todo find out if ols gives a database

                    enriched = fetcher.getCvTermByName(cvTermMaster.getShortName(), null);
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
