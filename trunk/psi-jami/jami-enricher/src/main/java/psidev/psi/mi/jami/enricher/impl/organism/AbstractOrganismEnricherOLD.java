package psidev.psi.mi.jami.enricher.impl.organism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:05
 */
public abstract class AbstractOrganismEnricherOLD
        implements OrganismEnricher {


    protected final Logger log = LoggerFactory.getLogger(AbstractOrganismEnricherOLD.class.getName());
    private OrganismFetcher fetcher=null;
    private static final String TYPE = "Organism";
    private static final String FIELD_TAXID = "TaxID";
    private static final String FIELD_COMMONNAME = "CommonName";
    private static final String FIELD_SCIENTIFICNAME = "ScientificName";

    public AbstractOrganismEnricherOLD(){
        //enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractOrganismEnricherOLD(OrganismFetcher fetcher){
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
            throws BridgeFailedException, MissingServiceException, BadToEnrichFormException {

        if(fetcher == null) throw new MissingServiceException("OrganismFetcher is null.");
        if(organismToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null organism.");

        Organism enriched = null;

            enriched = fetcher.getOrganismByTaxID(organismToEnrich.getTaxId());
            //enricherEvent.clear();
            //enricherEvent.setQueryDetails(""+organismToEnrich.getTaxId(),FIELD_TAXID,fetcher.getService());


        return enriched;
    }

    protected void runOrganismAdditionEnrichment(Organism organismToEnrich, Organism OrganismFetched)
            throws BadEnrichedFormException {

        //if(organismToEnrich == null && OrganismFetched != null) organismToEnrich = new DefaultOrganism(-3);

        if(OrganismFetched.getTaxId() < -4){//TODO check this  is a valid assertion
            throw new BadEnrichedFormException( "The organism had an invalid taxID of "+OrganismFetched.getTaxId());
        }else{
            //TaxID
            if(organismToEnrich.getTaxId() > 0){
                /*if(organismToEnrich.getTaxId() != OrganismFetched.getTaxId()) {
                    throw new ConflictException("Organism Conflict on TaxId. " +
                            "ToEnrich had ["+organismToEnrich.getTaxId()+"] " +
                            "but fetcher found ["+OrganismFetched.getTaxId()+"]");
                }*/
            }else if(organismToEnrich.getTaxId() == -3){
                organismToEnrich.setTaxId(OrganismFetched.getTaxId());
                //addAdditionReport(new AdditionReport(FIELD_TAXID,""+organismToEnrich.getTaxId()));
            }
            //negative taxids will continue through at this point

            //Scientific name
            if(organismToEnrich.getScientificName() == null
                    && OrganismFetched.getScientificName() != null){
                organismToEnrich.setScientificName(OrganismFetched.getScientificName());
                // addAdditionReport(new AdditionReport(
                // FIELD_SCIENTIFICNAME,OrganismFetched.getScientificName()));
            }

            //Commonname
            if(organismToEnrich.getCommonName() == null
                    &&OrganismFetched.getCommonName() != null){
                organismToEnrich.setCommonName(OrganismFetched.getCommonName());
                //addAdditionReport(new AdditionReport(
                // FIELD_COMMONNAME, organismToEnrich.getCommonName()));
            }
        }

    }

    protected void runOrganismMismatchComparison(Organism organismToEnrich, Organism OrganismFetched){

        // Should not be reachable for positive taxids, a conflict should have been thrown by this point
        // Essentially only compares -2,or -1
        if(OrganismFetched.getTaxId() != organismToEnrich.getTaxId()){
            //addMismatchReport(new MismatchReport(
                   // FIELD_TAXID,
                   // ""+organismToEnrich.getTaxId(),
                   // ""+OrganismFetched.getTaxId()));
        }

        //Scientific Name
        if (OrganismFetched.getScientificName() != null){
            if (!OrganismFetched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){
                //addMismatchReport(new MismatchReport(
                     //   FIELD_SCIENTIFICNAME,
                     //   organismToEnrich.getScientificName(),
                      //  OrganismFetched.getScientificName()));
            }
        }

        //Common Name
        if (OrganismFetched.getCommonName() != null){
            if (!OrganismFetched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){
               // addMismatchReport(new MismatchReport(
               //         FIELD_COMMONNAME,
               //         organismToEnrich.getCommonName(),
               //         OrganismFetched.getCommonName()));
            }
        }
    }

    protected void runOrganismOverwriteUpdate(Organism organismToEnrich, Organism OrganismFetched){

        // Should not be reachable for positive taxids, a conflict should have been thrown by this point
        // Essentially only compares -2,or -1
        if(OrganismFetched.getTaxId() != organismToEnrich.getTaxId()){

            String oldValue = ""+organismToEnrich.getTaxId();
            organismToEnrich.setTaxId(OrganismFetched.getTaxId());
           // addOverwriteReport(new OverwriteReport(
           //         FIELD_TAXID,
           //         oldValue,
           //         ""+organismToEnrich.getTaxId()));
        }

        //Scientific Name
        if (OrganismFetched.getScientificName() != null){
            if (!OrganismFetched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){
                String oldValue = organismToEnrich.getScientificName();
                organismToEnrich.setScientificName(OrganismFetched.getScientificName());
               // addOverwriteReport(new OverwriteReport(
               //         FIELD_SCIENTIFICNAME,
               //         oldValue,
               //         organismToEnrich.getScientificName()));
            }
        }

        //Common Name
        if (OrganismFetched.getCommonName() != null){
            if (!OrganismFetched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){
                String oldValue = organismToEnrich.getCommonName();
                organismToEnrich.setCommonName(OrganismFetched.getCommonName());
                //addOverwriteReport(new OverwriteReport(
                //        FIELD_COMMONNAME,
                //        oldValue,
                 //       organismToEnrich.getCommonName()));
            }
        }
    }
}
