package psidev.psi.mi.jami.enricher.enricherimplementation.cvterm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.enricher.util.CollectionUtilsExtra;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

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

    public static final String TYPE =  "CvTerm";

    public final static String FIELD_FULLNAME = "FullName";
    public final static String FIELD_SHORTNAME = "ShortName";
    public final static String FIELD_IDENTIFIER = "Identifier";
    public final static String FIELD_SYNONYM = "Synonym";

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
    protected CvTerm getFullyEnrichedForm(CvTerm cvTermToEnrich)
            throws EnrichmentException {
        if(fetcher == null) throw new FetchingException("CvTermFetcher is null.");
        if(cvTermToEnrich == null)throw new EnrichmentException("Attempted to enrich null CvTerm");

        CvTerm enriched = null;
        enricherEvent.clear();

        Collection<Xref> identifiersList = new ArrayList<Xref>();
        identifiersList.addAll(cvTermToEnrich.getIdentifiers());
        if(identifiersList.size() > 0){
            //Try with MI
            for(Xref id : identifiersList){
                if( enriched != null ) break;
                else if( id.getDatabase().getShortName().equals(CvTerm.PSI_MI_MI)){
                    try{
                        enriched = getFullyEnrichedFormByIdentifier(id);
                        enricherEvent.clear();
                        enricherEvent.setQueryDetails(
                                id.getId(), "MI Identifier", fetcher.getService());
                    }catch(FetchingException e){enriched = null;} //Ignore this exception, try the next identifier
                }
            }
            //Try with MOD
            if( enriched == null){
                for(Xref id : identifiersList){
                    if( enriched != null ) break;
                    else if( id.getDatabase().getShortName().equals(CvTerm.PSI_MOD_MI)){
                        try{
                            enriched = getFullyEnrichedFormByIdentifier(id);
                            enricherEvent.clear();
                            enricherEvent.setQueryDetails(
                                    id.getId(), "MOD Identifier", fetcher.getService());
                        }catch(FetchingException e){enriched = null;} //Ignore this exception, try the next identifier
                    }
                }
            }
            //Try with any other
            if( enriched == null){
                for(Xref id : identifiersList){
                    if( enriched != null ) break;
                    else if( id.getId() != null){
                        try{
                            enriched = getFullyEnrichedFormByIdentifier(id);
                            enricherEvent.clear();
                            enricherEvent.setQueryDetails(
                                    id.getId(), id.getDatabase().getShortName()+" Identifier", fetcher.getService());
                        }catch(FetchingException e){enriched = null;} //Ignore this exception, try the next identifier
                    }
                }
            }
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

    private CvTerm getFullyEnrichedFormByIdentifier(Xref identifier)
            throws EnrichmentException{

        if(log.isTraceEnabled()) log.trace("Searching on identifier "+identifier.getId());

        try{
            return fetcher.getCvTermByID(identifier.getId(), identifier.getDatabase());
        }catch(FetcherException e){
            throw new FetchingException("Identifier ["+identifier+"] could not return a CvTerm.",e);
        }
    }

    /**
     * Compares two CvTerms and updates the ToEnrich with any fields that it is missing.
     * Only full name and synonyms are considered.
     * @param cvTermToEnrich      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the ToEnrich with.
     * @throws EnrichmentException
     */
    protected void runCvTermAdditionEnrichment(CvTerm cvTermToEnrich, CvTerm cvTermEnriched)
            throws EnrichmentException{

        //Todo report obsolete

        //ShortName not checked - never null



        //FullName
        if(cvTermToEnrich.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermToEnrich.setFullName(cvTermEnriched.getFullName());
            addAdditionReport(new AdditionReport(FIELD_FULLNAME, cvTermToEnrich.getFullName()));
        }

        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getIdentifiers(),
                cvTermToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref x: subtractedIdentifiers){
            cvTermToEnrich.getIdentifiers().add(x);
            addAdditionReport(new AdditionReport(FIELD_IDENTIFIER, x.getId()));
        }

        //Add synonyms
        Collection<Alias> subtractedSynonyms = CollectionUtilsExtra.comparatorSubtract(
                cvTermEnriched.getSynonyms(),
                cvTermToEnrich.getSynonyms(),
                new DefaultAliasComparator());

        for(Alias x: subtractedSynonyms){
            cvTermToEnrich.getSynonyms().add(x);
            addAdditionReport(new AdditionReport(FIELD_SYNONYM, "Name: " + x.getName() + ", Type: " + x.getType()));
        }
    }

    /**
     * Compares the ToEnrich and enriched form for mismatches.
     * The full name and the short name are compared between the ToEnrich and enriched forms.
     * @param cvTermToEnrich
     * @param cvTermEnriched
     */
    public void runCvTermMismatchComparison(CvTerm cvTermToEnrich, CvTerm cvTermEnriched){
        //ShortName - can never be null
        if(!cvTermToEnrich.getShortName().equals(cvTermEnriched.getShortName())){
            addMismatchReport(new MismatchReport(
                    FIELD_SHORTNAME, cvTermToEnrich.getShortName(), cvTermEnriched.getShortName()));
        }

        //FullName
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermToEnrich.getFullName().equals(cvTermEnriched.getFullName())){
                addMismatchReport(new MismatchReport(
                        FIELD_FULLNAME, cvTermToEnrich.getFullName(), cvTermEnriched.getFullName()));
            }
        }
    }

    /**
     * Compares two CvTerms and updates the ToEnrich with any fields that it is missing or mismatched.
     * The minimum enricher is run first to add any missing fields,
     * then, the full name and short name are overwritten.
     * @param cvTermToEnrich      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the ToEnrich with.
     * @throws EnrichmentException
     */
    public void runCvTermOverwriteUpdate(CvTerm cvTermToEnrich, CvTerm cvTermEnriched)
            throws EnrichmentException{

        //Overwrite shortname - is never null
        if(!cvTermToEnrich.getShortName().equals(cvTermEnriched.getShortName())){
            String oldname =  cvTermToEnrich.getShortName();
            cvTermToEnrich.setShortName(cvTermEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    FIELD_SHORTNAME, cvTermToEnrich.getShortName(), oldname));
        }

        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermToEnrich.getFullName().equals(cvTermEnriched.getFullName())){
                String oldname =  cvTermToEnrich.getFullName();
                cvTermToEnrich.setFullName(cvTermEnriched.getFullName());
                addOverwriteReport(new OverwriteReport(
                        FIELD_FULLNAME, cvTermToEnrich.getFullName(), oldname));
            }
        }
    }
}
