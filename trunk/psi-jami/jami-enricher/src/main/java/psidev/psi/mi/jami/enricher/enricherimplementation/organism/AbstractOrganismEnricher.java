package psidev.psi.mi.jami.enricher.enricherimplementation.organism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.ConflictException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:05
 */
public abstract class AbstractOrganismEnricher
        extends EnricherEventProcessorImp
        implements OrganismEnricher {


    protected final Logger log = LoggerFactory.getLogger(AbstractOrganismEnricher.class.getName());
    private OrganismFetcher fetcher=null;
    private static final String TYPE = "Organism";

    public AbstractOrganismEnricher(){
        enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractOrganismEnricher(OrganismFetcher fetcher){
        this();
        setFetcher(fetcher);
    }

    public void setFetcher(OrganismFetcher fetcher){
        this.fetcher = fetcher;
    }
    public OrganismFetcher getFetcher(){
        return this.fetcher;
    }

    protected Organism getFullyEnrichedForm(Organism organismToEnrich)
            throws EnrichmentException {

        if(fetcher == null) throw new FetchingException("OrganismFetcher is null.");
        if(organismToEnrich == null) throw new FetchingException("Attempted to enrich a null organism.");

        Organism enriched = null;
        try{
            enriched = fetcher.getOrganismByTaxID(organismToEnrich.getTaxId());
            enricherEvent.clear();
            enricherEvent.setQueryDetails(""+organismToEnrich.getTaxId(),"TaxID",fetcher.getService());
        }catch(FetcherException e){
            throw new FetchingException(e);
        }

        if(enriched==null) throw new FetchingException("Null organism");

        return enriched;
    }

    protected void runOrganismAdditionEnrichment(Organism organismToEnrich, Organism organismEnriched)
            throws EnrichmentException {

        //if(organismToEnrich == null && organismEnriched == null) return; //Todo
        //if(organismToEnrich == null && organismEnriched != null) organismToEnrich = new DefaultOrganism(-3);

        if(organismEnriched.getTaxId() < -4){//TODO check this  is a valid assertion
            throw new FetchingException(
                    "The organism had an invalid taxID of "+organismEnriched.getTaxId());
        }else{
            //TaxID
            if(organismToEnrich.getTaxId() > 0){
                if(organismToEnrich.getTaxId() != organismEnriched.getTaxId()) {
                    throw new ConflictException("Organism Conflict on TaxId. " +
                            "ToEnrich had ["+organismToEnrich.getTaxId()+"] " +
                            "but fetcher found ["+organismEnriched.getTaxId()+"]");
                }
            }else if(organismToEnrich.getTaxId() == -3){
                organismToEnrich.setTaxId(organismEnriched.getTaxId());
                addAdditionReport(new AdditionReport("TaxId",""+organismToEnrich.getTaxId()));
            }
            //negative taxids will continue through at this point

            //Scientific name
            if(organismToEnrich.getScientificName() == null
                    && organismEnriched.getScientificName() != null){
                organismToEnrich.setScientificName(organismEnriched.getScientificName());
                addAdditionReport(new AdditionReport(
                        "Scientific name",organismEnriched.getScientificName()));
            }

            //Commonname
            if(organismToEnrich.getCommonName() == null
                    &&organismEnriched.getCommonName() != null){
                organismToEnrich.setCommonName(organismEnriched.getCommonName());
                addAdditionReport(new AdditionReport(
                        "Common name", organismToEnrich.getCommonName()));
            }
        }
    }

    protected void runOrganismMismatchComparison(Organism organismToEnrich, Organism organismEnriched){

        // Should not be reachable for positive taxids, a conflict should have been thrown by this point
        // Essentially only compares -2,or -1
        if(organismEnriched.getTaxId() != organismToEnrich.getTaxId()){
            addMismatchReport(new MismatchReport(
                    "TaxID",
                    ""+organismToEnrich.getTaxId(),
                    ""+organismEnriched.getTaxId()));
        }

        //Scientific Name
        if (organismEnriched.getScientificName() != null){
            if (!organismEnriched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){
                addMismatchReport(new MismatchReport(
                        "Scientific name",
                        organismToEnrich.getScientificName(),
                        organismEnriched.getScientificName()));
            }
        }

        //Common Name
        if (organismEnriched.getCommonName() != null){
            if (!organismEnriched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){
                addMismatchReport(new MismatchReport(
                        "Common name",
                        organismToEnrich.getCommonName(),
                        organismEnriched.getCommonName()));
            }
        }
    }

    protected void runOrganismOverwriteUpdate(Organism organismToEnrich, Organism organismEnriched)
            throws EnrichmentException{

        // Should not be reachable for positive taxids, a conflict should have been thrown by this point
        // Essentially only compares -2,or -1
        if(organismEnriched.getTaxId() != organismToEnrich.getTaxId()){

            String oldValue = ""+organismToEnrich.getTaxId();
            organismToEnrich.setTaxId(organismEnriched.getTaxId());
            addOverwriteReport(new OverwriteReport(
                    "TaxID",
                    oldValue,
                    ""+organismToEnrich.getTaxId()));
        }

        //Scientific Name
        if (organismEnriched.getScientificName() != null){
            if (!organismEnriched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){
                String oldValue = organismToEnrich.getScientificName();
                organismToEnrich.setScientificName(organismEnriched.getScientificName());
                addOverwriteReport(new OverwriteReport(
                        "Scientific name",
                        oldValue,
                        organismToEnrich.getScientificName()));
            }
        }

        //Common Name
        if (organismEnriched.getCommonName() != null){
            if (!organismEnriched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){
                String oldValue = organismToEnrich.getCommonName();
                organismToEnrich.setCommonName(organismEnriched.getCommonName());
                addOverwriteReport(new OverwriteReport(
                        "Common name",
                        oldValue,
                        organismToEnrich.getCommonName()));
            }
        }
    }
}
