package psidev.psi.mi.enricher.cvtermenricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.cvtermenricher.exception.BadIdentifierException;
import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.enricher.cvtermenricher.exception.MissingIdentifierException;
import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.EnricherEventProcessorImp;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
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

    public AbstractCvTermEnricher() throws EnrichmentException {
        fetcher = new CvTermFetcherManager();
        try {
            fetcher.addDefaultFetcher();
        } catch (BridgeFailedException e) {
            log.warn("Bridge failed on initialise.");
            throw new EnrichmentException(e);
        }
    }

    protected CvTerm getEnrichedForm(CvTerm cvTermMaster) throws EnrichmentException {
        return getEnrichedForm(cvTermMaster, null);
    }

    protected CvTerm getEnrichedForm(CvTerm cvTermMaster, EnrichmentReport report) throws EnrichmentException {
        String identifier = identifierScraper(cvTermMaster);
        CvTerm enriched = null;

        if(identifier != null) {
            if(report != null) {
                report.setIdentity(identifier);
                report.setIdentityType("Identifier");
            }
            try {
                if(log.isTraceEnabled()){log.trace("Searching on identifier "+identifier);}
                enriched = fetcher.getCVTermByID(identifier, null);
                if(enriched == null){
                    throw new BadIdentifierException("Identifier ["+identifier+"] returned no CvTerm.");
                }
            } catch (BridgeFailedException e) {
                log.warn("Bridge failed on identifier "+identifier);
                throw new EnrichmentException(e);
            }
        }

        if(enriched == null){
            if(log.isTraceEnabled()){log.trace("No identifier found");}
            if(cvTermMaster.getFullName() != null){
                if(report != null) {
                    report.setIdentity(cvTermMaster.getFullName());
                    report.setIdentityType("FullName");
                }
                try {
                    if(log.isTraceEnabled()){log.trace("Searching on fullname "+cvTermMaster.getFullName());}
                    enriched = fetcher.getCVTermByName(cvTermMaster.getFullName(), null);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            }
        }

        if(enriched == null){
            if(log.isTraceEnabled()){log.trace("No identifier found");}
            if(cvTermMaster.getShortName() != null){
                if(report != null) {
                    report.setIdentity(cvTermMaster.getShortName());
                    report.setIdentityType("ShortName");
                }
                try {
                    if(log.isTraceEnabled()){log.trace("Searching on short name "+cvTermMaster.getShortName());}

                    enriched = fetcher.getCVTermByName(cvTermMaster.getShortName(), null);
                } catch (BridgeFailedException e) {
                    if(log.isTraceEnabled()){log.trace("Bridge failed");}
                    throw new EnrichmentException(e);
                }
            }
        }

        if(enriched == null){
            throw new MissingIdentifierException("Missing identifier or resolvable name.");
        }

        return enriched;
    }

    private String identifierScraper(CvTerm cvTerm){
        String identifier;

        identifier = cvTerm.getMIIdentifier();
        if(identifier == null){
            if(log.isTraceEnabled()){log.trace("No MI ID");}
            cvTerm.getMODIdentifier();
        }
        if(identifier == null){
            if(log.isTraceEnabled()){log.trace("No MOD ID");}
            cvTerm.getPARIdentifier();
        }
        if(identifier == null){
            if(log.isTraceEnabled()){log.trace("No PAR ID");}
            Collection<Xref> identifiers = cvTerm.getIdentifiers();
            if(identifiers != null){
                if(log.isTraceEnabled()){
                    log.trace("There are "+identifiers.size()+" identifiers.");
                }
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
