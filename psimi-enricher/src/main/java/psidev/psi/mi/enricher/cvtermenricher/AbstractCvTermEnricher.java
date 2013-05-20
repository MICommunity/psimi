package psidev.psi.mi.enricher.cvtermenricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.enricher.exception.BadIdentifierException;
import psidev.psi.mi.enricher.exception.EnrichmentException;
import psidev.psi.mi.enricher.exception.MissingIdentifierException;
import psidev.psi.mi.enricherlistener.EnricherEventProcessorImp;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;
import java.util.Iterator;

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
        implements CvTermEnricher{

    private final Logger log = LoggerFactory.getLogger(AbstractCvTermEnricher.class.getName());
    private CvTermFetcherManager fetcher;

    /**
     * Constructor to set up resources.
     * Initiates the fetchers.
     * Will throw a bridge failed exception in an enrichment exception.
     * @throws EnrichmentException      Thrown when the bridge can not be initiated
     */
    public AbstractCvTermEnricher() throws EnrichmentException {
        try {
            fetcher = new CvTermFetcherManager();
            fetcher.addDefaultFetcher();
        } catch (BridgeFailedException e) {
            log.warn("CvTerm fetcher failed on initialise.");
            throw new EnrichmentException(e);
        }
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
    protected CvTerm getEnrichedForm(CvTerm cvTermMaster) throws EnrichmentException {
        log.debug("-- NEW ENRICHMENT BEGINS --") ;
        CvTerm enriched = null;
        enricherEvent = null;

        String identifier = identifierScraper(cvTermMaster);

        if(identifier != null) {
            enricherEvent = new EnricherEvent(identifier,"Identifier");
            //Todo Extract the ontology
            String ontology = null;
                try {
                    if(log.isTraceEnabled()){log.trace("Searching on identifier "+identifier);}
                    enriched = fetcher.getCVTermByID(identifier, ontology);
                    if(enriched == null){
                        throw new BadIdentifierException("Identifier ["+identifier+"] returned no CvTerm.");
                    }
                } catch (BridgeFailedException e) {
                    log.warn("Bridge failed on identifier "+identifier);
                    throw new EnrichmentException(e);
                }

        }else { if(log.isTraceEnabled()) log.trace("No identifier found by the scraper");}

        if(enriched == null){
            if(cvTermMaster.getFullName() != null){
                enricherEvent = new EnricherEvent(cvTermMaster.getFullName(),"FullName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on fullname "+cvTermMaster.getFullName());}
                    enriched = fetcher.getCVTermByName(cvTermMaster.getFullName(), null);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            } else { if(log.isTraceEnabled()) log.trace("No full name to search on");}
        }

        if(enriched == null){
            if(cvTermMaster.getShortName() != null){
                enricherEvent = new EnricherEvent(cvTermMaster.getShortName(),"ShortName");

                try {
                    if(log.isTraceEnabled()){log.trace("Searching on short name "+cvTermMaster.getShortName());}

                    enriched = fetcher.getCVTermByName(cvTermMaster.getShortName(), null);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            }
            else{ if(log.isTraceEnabled()) log.trace("No short name to search on."); }
        }

        if(enriched == null){
            log.debug("No enrichment made, throwing enricher exception");
            throw new MissingIdentifierException("Missing identifier or resolvable name.");
        }

        return enriched;
    }

    /**
     * Used to find the best identifier from a CvTerm.
     * If possible, it will attempt to get the MI, MOD or PAR identifier.
     * If these are not available, it will use the first identifier from the identifier list.
     *
     * @param cvTerm    The master CvTerm to fin the identifier from.
     * @return  The best match identifier (or null if the CvTerm has no identifiers)
     */
    private String identifierScraper(CvTerm cvTerm){
        String identifier = null;

        if(cvTerm.getMIIdentifier() != null){
            identifier = cvTerm.getMIIdentifier();
            if(log.isTraceEnabled()) log.trace("Using MI");
        }else if(cvTerm.getMODIdentifier() != null){
            identifier = cvTerm.getMODIdentifier();
            if(log.isTraceEnabled()) log.trace("Using MOD");
        }else if(cvTerm.getPARIdentifier() != null){
            identifier = cvTerm.getPARIdentifier();
            if(log.isTraceEnabled()) log.trace("Using PAR");
        }else{
            Collection<Xref> identifiers = cvTerm.getIdentifiers();
            if(identifiers.size() > 0){
                if(log.isTraceEnabled()){log.trace("There are "+identifiers.size()+" identifiers.");}
                Iterator i = identifiers.iterator();
                Xref  idXref = (Xref)i.next();
                identifier = idXref.getId();
                log.trace("The first identifier was "+identifier);
                if(log.isTraceEnabled()){
                    while(i.hasNext()){
                        idXref = (Xref)i.next();
                        log.trace("Did not use identifier "+idXref.getId());
                    }
                }

            }
        }
        return identifier;
    }
}
